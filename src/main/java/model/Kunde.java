package model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
public class Kunde {
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "NACHNAME")
    private String nachname;
    @Basic
    @Column(name = "VORNAME")
    private String vorname;
    @Basic
    @Column(name = "ZWEITERVORNAME")
    private String zweitervorname;
    @Basic
    @Column(name = "ORT")
    private String ort;
    @Basic
    @Column(name = "PLZ")
    private String plz;
    @Basic
    @Column(name = "LAND")
    private String land;
    @Basic
    @Column(name = "STRASSE")
    private String strasse;
    @Basic
    @Column(name = "HAUSNR")
    private short hausnr;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "KUNDENNR")
    private BigInteger kundennr;
    @OneToMany(mappedBy = "kunde")
    private Collection<Reservierung> reservierungen;
    @ManyToMany(mappedBy = "kunden")
    private Collection<Fuehrerscheinklassen> fueherscheine;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getZweitervorname() {
        return zweitervorname;
    }

    public void setZweitervorname(String zweitervorname) {
        this.zweitervorname = zweitervorname;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public short getHausnr() {
        return hausnr;
    }

    public void setHausnr(short hausnr) {
        this.hausnr = hausnr;
    }

    public BigInteger getKundennr() {
        return kundennr;
    }

    public void setKundennr(BigInteger kundennr) {
        this.kundennr = kundennr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kunde kunde = (Kunde) o;

        if (hausnr != kunde.hausnr) return false;
        if (email != null ? !email.equals(kunde.email) : kunde.email != null) return false;
        if (nachname != null ? !nachname.equals(kunde.nachname) : kunde.nachname != null) return false;
        if (vorname != null ? !vorname.equals(kunde.vorname) : kunde.vorname != null) return false;
        if (zweitervorname != null ? !zweitervorname.equals(kunde.zweitervorname) : kunde.zweitervorname != null)
            return false;
        if (ort != null ? !ort.equals(kunde.ort) : kunde.ort != null) return false;
        if (plz != null ? !plz.equals(kunde.plz) : kunde.plz != null) return false;
        if (land != null ? !land.equals(kunde.land) : kunde.land != null) return false;
        if (strasse != null ? !strasse.equals(kunde.strasse) : kunde.strasse != null) return false;
        if (kundennr != null ? !kundennr.equals(kunde.kundennr) : kunde.kundennr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (nachname != null ? nachname.hashCode() : 0);
        result = 31 * result + (vorname != null ? vorname.hashCode() : 0);
        result = 31 * result + (zweitervorname != null ? zweitervorname.hashCode() : 0);
        result = 31 * result + (ort != null ? ort.hashCode() : 0);
        result = 31 * result + (plz != null ? plz.hashCode() : 0);
        result = 31 * result + (land != null ? land.hashCode() : 0);
        result = 31 * result + (strasse != null ? strasse.hashCode() : 0);
        result = 31 * result + (int) hausnr;
        result = 31 * result + (kundennr != null ? kundennr.hashCode() : 0);
        return result;
    }

    public Collection<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public void setReservierungen(Collection<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    public Collection<Fuehrerscheinklassen> getFueherscheine() {
        return fueherscheine;
    }

    public void setFueherscheine(Collection<Fuehrerscheinklassen> fueherscheine) {
        this.fueherscheine = fueherscheine;
    }
}
