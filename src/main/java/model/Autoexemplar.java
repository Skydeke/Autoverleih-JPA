package model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Autoexemplar {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "KENNZEICHEN")
    private String kennzeichen;
    @Basic
    @Column(name = "KILOMETERSTAND")
    private double kilometerstand;
    @Basic
    @Column(name = "LETZTERTUEV")
    private Date letztertuev;
    @Basic
    @Column(name = "KAUFDATUM")
    private Date kaufdatum;
    @OneToMany(mappedBy = "autoexemplar")
    private Collection<Ausleihvorgang> ausleihvorgaenge;
    @ManyToOne
    @JoinColumn(name = "AUTOMODELLNR", referencedColumnName = "AUTOMODELLNR")
    private Automodell automodell;
    @ManyToMany(mappedBy = "autoexemplare")
    private Collection<Ausstattung> NachGerAusst;

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public double getKilometerstand() {
        return kilometerstand;
    }

    public void setKilometerstand(double kilometerstand) {
        this.kilometerstand = kilometerstand;
    }

    public Date getLetztertuev() {
        return letztertuev;
    }

    public void setLetztertuev(Date letztertuev) {
        this.letztertuev = letztertuev;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(Date kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Autoexemplar that = (Autoexemplar) o;

        if (Double.compare(that.kilometerstand, kilometerstand) != 0) return false;
        if (kennzeichen != null ? !kennzeichen.equals(that.kennzeichen) : that.kennzeichen != null) return false;
        if (letztertuev != null ? !letztertuev.equals(that.letztertuev) : that.letztertuev != null) return false;
        if (kaufdatum != null ? !kaufdatum.equals(that.kaufdatum) : that.kaufdatum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp;
        temp = Double.doubleToLongBits(kilometerstand);
        int result = (int) (temp ^ (temp >>> 32));
        result = kennzeichen != null ? kennzeichen.hashCode() : 0;

        result = 31 * result + (letztertuev != null ? letztertuev.hashCode() : 0);
        result = 31 * result + (kaufdatum != null ? kaufdatum.hashCode() : 0);
        return result;
    }

    public Collection<Ausleihvorgang> getAusleihvorgaenge() {
        return ausleihvorgaenge;
    }

    public void setAusleihvorgaenge(Collection<Ausleihvorgang> ausleihvorgaenge) {
        this.ausleihvorgaenge = ausleihvorgaenge;
    }

    public Automodell getAutomodell() {
        return automodell;
    }

    public void setAutomodell(Automodell automodell) {
        this.automodell = automodell;
    }

    public Collection<Ausstattung> getNachGerAusst() {
        return NachGerAusst;
    }

    public void setNachGerAusst(Collection<Ausstattung> nachGerAusst) {
        NachGerAusst = nachGerAusst;
    }
}
