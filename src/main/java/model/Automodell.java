package model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
public class Automodell {
    @Basic
    @Column(name = "HERSTELLER")
    private String hersteller;
    @Basic
    @Column(name = "SITZPLAETZE")
    private short sitzplaetze;
    @Basic
    @Column(name = "PREISPROTAG")
    private double preisprotag;
    @Basic
    @Column(name = "TYP")
    private String typ;
    @Basic
    @Column(name = "PREISPROKM")
    private double preisprokm;
    @Basic
    @Column(name = "ART")
    private String art;
    @Basic
    @Column(name = "LADEVOLUMENKUBIKMETER")
    private Double ladevolumenkubikmeter;
    @Basic
    @Column(name = "ACHSENZAHL")
    private Byte achsenzahl;
    @Basic
    @Column(name = "LADEGEWICHTKG")
    private Double ladegewichtkg;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AUTOMODELLNR")
    private BigInteger automodellnr;
//    @Basic
//    @Column(name = "KLASSENBEZEICHNUNG")
//    private String klassenbezeichnung;
    @OneToMany(mappedBy = "automodell")
    private Collection<Autoexemplar> autoexemplare;
    @ManyToOne
    @JoinColumn(name = "KLASSENBEZEICHNUNG", referencedColumnName = "KLASSENBEZEICHNUNG")
    private Fuehrerscheinklassen fuehrerscheinklasse;
    @OneToMany(mappedBy = "automodell")
    private Collection<Reservierung> reservierungen;
    @ManyToMany(mappedBy = "nachGerModelle")
    private Collection<Ausstattung> NachGerAusst;

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public short getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(short sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    public double getPreisprotag() {
        return preisprotag;
    }

    public void setPreisprotag(double preisprotag) {
        this.preisprotag = preisprotag;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public double getPreisprokm() {
        return preisprokm;
    }

    public void setPreisprokm(double preisprokm) {
        this.preisprokm = preisprokm;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public Double getLadevolumenkubikmeter() {
        return ladevolumenkubikmeter;
    }

    public void setLadevolumenkubikmeter(Double ladevolumenkubikmeter) {
        this.ladevolumenkubikmeter = ladevolumenkubikmeter;
    }

    public Byte getAchsenzahl() {
        return achsenzahl;
    }

    public void setAchsenzahl(Byte achsenzahl) {
        this.achsenzahl = achsenzahl;
    }

    public Double getLadegewichtkg() {
        return ladegewichtkg;
    }

    public void setLadegewichtkg(Double ladegewichtkg) {
        this.ladegewichtkg = ladegewichtkg;
    }

    public BigInteger getAutomodellnr() {
        return automodellnr;
    }

    public void setAutomodellnr(BigInteger automodellnr) {
        this.automodellnr = automodellnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Automodell that = (Automodell) o;

        if (sitzplaetze != that.sitzplaetze) return false;
        if (Double.compare(that.preisprotag, preisprotag) != 0) return false;
        if (Double.compare(that.preisprokm, preisprokm) != 0) return false;
        if (hersteller != null ? !hersteller.equals(that.hersteller) : that.hersteller != null) return false;
        if (typ != null ? !typ.equals(that.typ) : that.typ != null) return false;
        if (art != null ? !art.equals(that.art) : that.art != null) return false;
        if (ladevolumenkubikmeter != null ? !ladevolumenkubikmeter.equals(that.ladevolumenkubikmeter) : that.ladevolumenkubikmeter != null)
            return false;
        if (achsenzahl != null ? !achsenzahl.equals(that.achsenzahl) : that.achsenzahl != null) return false;
        if (ladegewichtkg != null ? !ladegewichtkg.equals(that.ladegewichtkg) : that.ladegewichtkg != null)
            return false;
        if (automodellnr != null ? !automodellnr.equals(that.automodellnr) : that.automodellnr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = hersteller != null ? hersteller.hashCode() : 0;
        result = 31 * result + (int) sitzplaetze;
        temp = Double.doubleToLongBits(preisprotag);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (typ != null ? typ.hashCode() : 0);
        temp = Double.doubleToLongBits(preisprokm);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (art != null ? art.hashCode() : 0);
        result = 31 * result + (ladevolumenkubikmeter != null ? ladevolumenkubikmeter.hashCode() : 0);
        result = 31 * result + (achsenzahl != null ? achsenzahl.hashCode() : 0);
        result = 31 * result + (ladegewichtkg != null ? ladegewichtkg.hashCode() : 0);
        result = 31 * result + (automodellnr != null ? automodellnr.hashCode() : 0);
        return result;
    }

    public Collection<Autoexemplar> getAutoexemplare() {
        return autoexemplare;
    }

    public void setAutoexemplare(Collection<Autoexemplar> autoexemplare) {
        this.autoexemplare = autoexemplare;
    }

    public Fuehrerscheinklassen getFuehrerscheinklasse() {
        return fuehrerscheinklasse;
    }

    public void setFuehrerscheinklasse(Fuehrerscheinklassen fuehrerscheinklasse) {
        this.fuehrerscheinklasse = fuehrerscheinklasse;
    }

    public Collection<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public void setReservierungen(Collection<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    public Collection<Ausstattung> getNachGerAusst() {
        return NachGerAusst;
    }

    public void setNachGerAusst(Collection<Ausstattung> nachGerAusst) {
        NachGerAusst = nachGerAusst;
    }
}
