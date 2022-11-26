package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Fuehrerscheinklassen {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "KLASSENBEZEICHNUNG")
    private String klassenbezeichnung;
    @OneToMany(mappedBy = "fuehrerscheinklasse")
    private Collection<Automodell> automodelle;
    @ManyToMany
    @JoinTable(name = "BESITZT", catalog = "", schema = "DABS_12", joinColumns = @JoinColumn(name = "KLASSENBEZEICHNUNG", referencedColumnName = "KLASSENBEZEICHNUNG"), inverseJoinColumns = @JoinColumn(name = "KUNDENNR", referencedColumnName = "KUNDENNR"))
    private Collection<Kunde> kunden;

    public String getKlassenbezeichnung() {
        return klassenbezeichnung;
    }

    public void setKlassenbezeichnung(String klassenbezeichnung) {
        this.klassenbezeichnung = klassenbezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fuehrerscheinklassen that = (Fuehrerscheinklassen) o;

        if (klassenbezeichnung != null ? !klassenbezeichnung.equals(that.klassenbezeichnung) : that.klassenbezeichnung != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return klassenbezeichnung != null ? klassenbezeichnung.hashCode() : 0;
    }

    public Collection<Automodell> getAutomodelle() {
        return automodelle;
    }

    public void setAutomodelle(Collection<Automodell> automodelle) {
        this.automodelle = automodelle;
    }

    public Collection<Kunde> getKunden() {
        return kunden;
    }

    public void setKunden(Collection<Kunde> kunden) {
        this.kunden = kunden;
    }
}
