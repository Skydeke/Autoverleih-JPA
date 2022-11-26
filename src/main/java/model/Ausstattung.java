package model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
public class Ausstattung {
    @Basic
    @Column(name = "BEZEICHNUNG")
    private String bezeichnung;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AUSSTATTUNGNR")
    private BigInteger ausstattungnr;
    @ManyToMany
    @JoinTable(name = "IST_NACHGERUESTET_BEI", catalog = "", schema = "DABS_12", joinColumns = @JoinColumn(name = "AUSSTATTUNGNR", referencedColumnName = "AUSSTATTUNGNR"), inverseJoinColumns = @JoinColumn(name = "KENNZEICHEN", referencedColumnName = "KENNZEICHEN"))
    private Collection<Autoexemplar> autoexemplare;
    @ManyToMany
    @JoinTable(name = "IST_GRUNDAUSSTATTUNG_BEI", catalog = "", schema = "DABS_12", joinColumns = @JoinColumn(name = "AUSSTATTUNGNR", referencedColumnName = "AUSSTATTUNGNR"), inverseJoinColumns = @JoinColumn(name = "AUTOMODELLNR", referencedColumnName = "AUTOMODELLNR"))
    private Collection<Automodell> nachGerModelle;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public BigInteger getAusstattungnr() {
        return ausstattungnr;
    }

    public void setAusstattungnr(BigInteger ausstattungnr) {
        this.ausstattungnr = ausstattungnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ausstattung that = (Ausstattung) o;

        if (bezeichnung != null ? !bezeichnung.equals(that.bezeichnung) : that.bezeichnung != null) return false;
        if (ausstattungnr != null ? !ausstattungnr.equals(that.ausstattungnr) : that.ausstattungnr != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bezeichnung != null ? bezeichnung.hashCode() : 0;
        result = 31 * result + (ausstattungnr != null ? ausstattungnr.hashCode() : 0);
        return result;
    }

    public Collection<Autoexemplar> getAutoexemplare() {
        return autoexemplare;
    }

    public void setAutoexemplare(Collection<Autoexemplar> autoexemplare) {
        this.autoexemplare = autoexemplare;
    }

    public Collection<Automodell> getNachGerModelle() {
        return nachGerModelle;
    }

    public void setNachGerModelle(Collection<Automodell> nachGerModelle) {
        this.nachGerModelle = nachGerModelle;
    }
}
