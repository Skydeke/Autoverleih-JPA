package model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
public class Reservierung {
    @Basic
    @Column(name = "GEPLANTERSTART")
    private Timestamp geplanterstart;
    @Basic
    @Column(name = "GEPLANTESENDE")
    private Timestamp geplantesende;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RESNR")
    private BigInteger resnr;
//    @Basic
//    @Column(name = "AUTOMODELLNR")
//    private BigInteger automodellnr;
//    @Basic
//    @Column(name = "KUNDENNR")
//    private BigInteger kundennr;
//    @Basic
//    @Column(name = "AUSLEIHVORGANGNR")
//    private BigInteger ausleihvorgangnr;
    @ManyToOne
    @JoinColumn(name = "AUTOMODELLNR", referencedColumnName = "AUTOMODELLNR")
    private Automodell automodell;
    @ManyToOne
    @JoinColumn(name = "KUNDENNR", referencedColumnName = "KUNDENNR")
    private Kunde kunde;
    @OneToOne
    @JoinColumn(name = "AUSLEIHVORGANGNR", referencedColumnName = "AUSLEIHVORGANGNR")
    private Ausleihvorgang ausleihvorgang;

    public Timestamp getGeplanterstart() {
        return geplanterstart;
    }

    public void setGeplanterstart(Timestamp geplanterstart) {
        this.geplanterstart = geplanterstart;
    }

    public Timestamp getGeplantesende() {
        return geplantesende;
    }

    public void setGeplantesende(Timestamp geplantesende) {
        this.geplantesende = geplantesende;
    }

    public BigInteger getResnr() {
        return resnr;
    }

    public void setResnr(BigInteger resnr) {
        this.resnr = resnr;
    }

//    public BigInteger getAutomodellnr() {
//        return automodellnr;
//    }
//
//    public void setAutomodellnr(BigInteger automodellnr) {
//        this.automodellnr = automodellnr;
//    }
//
//    public BigInteger getKundennr() {
//        return kundennr;
//    }
//
//    public void setKundennr(BigInteger kundennr) {
//        this.kundennr = kundennr;
//    }
//
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

        Reservierung that = (Reservierung) o;

        if (geplanterstart != null ? !geplanterstart.equals(that.geplanterstart) : that.geplanterstart != null)
            return false;
        if (geplantesende != null ? !geplantesende.equals(that.geplantesende) : that.geplantesende != null)
            return false;
        if (resnr != null ? !resnr.equals(that.resnr) : that.resnr != null) return false;
//        if (automodellnr != null ? !automodellnr.equals(that.automodellnr) : that.automodellnr != null) return false;
//        if (kundennr != null ? !kundennr.equals(that.kundennr) : that.kundennr != null) return false;
//        if (ausleihvorgangnr != null ? !ausleihvorgangnr.equals(that.ausleihvorgangnr) : that.ausleihvorgangnr != null)
//            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = geplanterstart != null ? geplanterstart.hashCode() : 0;
        result = 31 * result + (geplantesende != null ? geplantesende.hashCode() : 0);
        result = 31 * result + (resnr != null ? resnr.hashCode() : 0);
//        result = 31 * result + (automodellnr != null ? automodellnr.hashCode() : 0);
//        result = 31 * result + (kundennr != null ? kundennr.hashCode() : 0);
//        result = 31 * result + (ausleihvorgangnr != null ? ausleihvorgangnr.hashCode() : 0);
        return result;
    }

    public Automodell getAutomodell() {
        return automodell;
    }

    public void setAutomodell(Automodell automodell) {
        this.automodell = automodell;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Ausleihvorgang getAusleihvorgang() {
        return ausleihvorgang;
    }

    public void setAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        this.ausleihvorgang = ausleihvorgang;
    }
}
