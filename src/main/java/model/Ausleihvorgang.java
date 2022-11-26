package model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
public class Ausleihvorgang {
    @Basic
    @Column(name = "BEGINNZEIT")
    private Timestamp beginnzeit;
    @Basic
    @Column(name = "ENDEZEIT")
    private Timestamp endezeit;
    @Basic
    @Column(name = "BEGINNKM")
    private double beginnkm;
    @Basic
    @Column(name = "ENDEKM")
    private double endekm;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AUSLEIHVORGANGNR")
    private BigInteger ausleihvorgangnr;
    @ManyToOne
    @JoinColumn(name = "KENNZEICHEN", referencedColumnName = "KENNZEICHEN")
    private Autoexemplar autoexemplar;
    @OneToOne(mappedBy = "ausleihvorgang")
    private Rechnung rechnung;
    @OneToOne(mappedBy = "ausleihvorgang")
    private Reservierung reservierung;

    public Timestamp getBeginnzeit() {
        return beginnzeit;
    }

    public void setBeginnzeit(Timestamp beginnzeit) {
        this.beginnzeit = beginnzeit;
    }

    public Timestamp getEndezeit() {
        return endezeit;
    }

    public void setEndezeit(Timestamp endezeit) {
        this.endezeit = endezeit;
    }

    public double getBeginnkm() {
        return beginnkm;
    }

    public void setBeginnkm(double beginnkm) {
        this.beginnkm = beginnkm;
    }

    public double getEndekm() {
        return endekm;
    }

    public void setEndekm(double endekm) {
        this.endekm = endekm;
    }

    public BigInteger getAusleihvorgangnr() {
        return ausleihvorgangnr;
    }

    public void setAusleihvorgangnr(BigInteger ausleihvorgangnr) {
        this.ausleihvorgangnr = ausleihvorgangnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ausleihvorgang that = (Ausleihvorgang) o;

        if (Double.compare(that.beginnkm, beginnkm) != 0) return false;
        if (Double.compare(that.endekm, endekm) != 0) return false;
        if (beginnzeit != null ? !beginnzeit.equals(that.beginnzeit) : that.beginnzeit != null) return false;
        if (endezeit != null ? !endezeit.equals(that.endezeit) : that.endezeit != null) return false;
        if (ausleihvorgangnr != null ? !ausleihvorgangnr.equals(that.ausleihvorgangnr) : that.ausleihvorgangnr != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = beginnzeit != null ? beginnzeit.hashCode() : 0;
        result = 31 * result + (endezeit != null ? endezeit.hashCode() : 0);
        temp = Double.doubleToLongBits(beginnkm);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(endekm);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ausleihvorgangnr != null ? ausleihvorgangnr.hashCode() : 0);
        return result;
    }

    public Autoexemplar getAutoexemplar() {
        return autoexemplar;
    }

    public void setAutoexemplar(Autoexemplar autoexemplar) {
        this.autoexemplar = autoexemplar;
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    public void setReservierung(Reservierung reservierung) {
        this.reservierung = reservierung;
    }
}
