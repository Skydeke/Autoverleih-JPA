package model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Rechnung {
    @Basic
    @Column(name = "SUMME")
    private double summe;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RECHNR")
    private BigInteger rechnr;
//    @Basic
//    @Column(name = "AUSLEIHVORGANGNR")
//    private BigInteger ausleihvorgangnr;
    @OneToOne
    @JoinColumn(name = "AUSLEIHVORGANGNR", referencedColumnName = "AUSLEIHVORGANGNR")
    private Ausleihvorgang ausleihvorgang;

    public double getSumme() {
        return summe;
    }

    public void setSumme(double summe) {
        this.summe = summe;
    }

    public BigInteger getRechnr() {
        return rechnr;
    }

    public void setRechnr(BigInteger rechnr) {
        this.rechnr = rechnr;
    }

//    public BigInteger getAusleihvorgangnr() {
//        return ausleihvorgangnr;
//    }
//
//    public void setAusleihvorgangnr(BigInteger ausleihvorgangnr) {
//        this.ausleihvorgangnr = ausleihvorgangnr;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rechnung rechnung = (Rechnung) o;

        if (Double.compare(rechnung.summe, summe) != 0) return false;
        if (rechnr != null ? !rechnr.equals(rechnung.rechnr) : rechnung.rechnr != null) return false;
//        if (ausleihvorgangnr != null ? !ausleihvorgangnr.equals(rechnung.ausleihvorgangnr) : rechnung.ausleihvorgangnr != null)
//            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(summe);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rechnr != null ? rechnr.hashCode() : 0);
//        result = 31 * result + (ausleihvorgangnr != null ? ausleihvorgangnr.hashCode() : 0);
        return result;
    }

    public Ausleihvorgang getAusleihvorgang() {
        return ausleihvorgang;
    }

    public void setAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        this.ausleihvorgang = ausleihvorgang;
    }
}
