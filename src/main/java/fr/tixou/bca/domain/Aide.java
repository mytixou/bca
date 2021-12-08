package fr.tixou.bca.domain;

import fr.tixou.bca.domain.enumeration.TypeAide;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Aide.
 */
@Entity
@Table(name = "aide")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nom", nullable = false, unique = true)
    private TypeAide nom;

    @NotNull
    @Column(name = "priorite", nullable = false, unique = true)
    private Integer priorite;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @NotNull
    @Column(name = "date_lancement", nullable = false)
    private LocalDate dateLancement;

    @NotNull
    @Column(name = "anne_lancement", nullable = false)
    private Integer anneLancement;

    @NotNull
    @Column(name = "mois_lancement", nullable = false)
    private Integer moisLancement;

    @Column(name = "date_arret")
    private LocalDate dateArret;

    @Column(name = "derniere_annee")
    private Integer derniereAnnee;

    @Column(name = "dernier_mois")
    private Integer dernierMois;

    @ManyToOne
    private TiersFinanceur aide;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aide id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeAide getNom() {
        return this.nom;
    }

    public Aide nom(TypeAide nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(TypeAide nom) {
        this.nom = nom;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public Aide priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Aide isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateLancement() {
        return this.dateLancement;
    }

    public Aide dateLancement(LocalDate dateLancement) {
        this.setDateLancement(dateLancement);
        return this;
    }

    public void setDateLancement(LocalDate dateLancement) {
        this.dateLancement = dateLancement;
    }

    public Integer getAnneLancement() {
        return this.anneLancement;
    }

    public Aide anneLancement(Integer anneLancement) {
        this.setAnneLancement(anneLancement);
        return this;
    }

    public void setAnneLancement(Integer anneLancement) {
        this.anneLancement = anneLancement;
    }

    public Integer getMoisLancement() {
        return this.moisLancement;
    }

    public Aide moisLancement(Integer moisLancement) {
        this.setMoisLancement(moisLancement);
        return this;
    }

    public void setMoisLancement(Integer moisLancement) {
        this.moisLancement = moisLancement;
    }

    public LocalDate getDateArret() {
        return this.dateArret;
    }

    public Aide dateArret(LocalDate dateArret) {
        this.setDateArret(dateArret);
        return this;
    }

    public void setDateArret(LocalDate dateArret) {
        this.dateArret = dateArret;
    }

    public Integer getDerniereAnnee() {
        return this.derniereAnnee;
    }

    public Aide derniereAnnee(Integer derniereAnnee) {
        this.setDerniereAnnee(derniereAnnee);
        return this;
    }

    public void setDerniereAnnee(Integer derniereAnnee) {
        this.derniereAnnee = derniereAnnee;
    }

    public Integer getDernierMois() {
        return this.dernierMois;
    }

    public Aide dernierMois(Integer dernierMois) {
        this.setDernierMois(dernierMois);
        return this;
    }

    public void setDernierMois(Integer dernierMois) {
        this.dernierMois = dernierMois;
    }

    public TiersFinanceur getAide() {
        return this.aide;
    }

    public void setAide(TiersFinanceur tiersFinanceur) {
        this.aide = tiersFinanceur;
    }

    public Aide aide(TiersFinanceur tiersFinanceur) {
        this.setAide(tiersFinanceur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aide)) {
            return false;
        }
        return id != null && id.equals(((Aide) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aide{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", priorite=" + getPriorite() +
            ", isActif='" + getIsActif() + "'" +
            ", dateLancement='" + getDateLancement() + "'" +
            ", anneLancement=" + getAnneLancement() +
            ", moisLancement=" + getMoisLancement() +
            ", dateArret='" + getDateArret() + "'" +
            ", derniereAnnee=" + getDerniereAnnee() +
            ", dernierMois=" + getDernierMois() +
            "}";
    }
}
