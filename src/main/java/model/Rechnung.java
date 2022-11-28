package model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Rechnung {
    @Basic
    @Column(name = "SUMME")
    private double summe;
    @SequenceGenerator(name = "RECH_ID_SEQ", sequenceName  = "SEQ_RECH_IDENTITY", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECH_ID_SEQ")
    @Id
    @Column(name = "RECHNR")
    private BigInteger rechnr;
    @OneToOne
    @JoinColumn(name = "AUSLEIHVORGANGNR", referencedColumnName = "AUSLEIHVORGANGNR")
    private Ausleihvorgang ausleihvorgang;
    @Basic
    @Column(name = "BEGLICHEN")
    private String beglichen;

    public boolean getBeglichen() {
        return beglichen.contentEquals("t");
    }

    public void setBeglichen(String beglichen) {
        this.beglichen = beglichen;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rechnung rechnung = (Rechnung) o;

        if (Double.compare(rechnung.summe, summe) != 0) return false;
        if (rechnr != null ? !rechnr.equals(rechnung.rechnr) : rechnung.rechnr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(summe);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rechnr != null ? rechnr.hashCode() : 0);
        return result;
    }

    public Ausleihvorgang getAusleihvorgang() {
        return ausleihvorgang;
    }

    public void setAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        this.ausleihvorgang = ausleihvorgang;
    }
}
