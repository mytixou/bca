package fr.tixou.bca.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TiersFinanceur.
 */
@Entity
@Table(name = "tiers_financeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TiersFinanceur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @Column(name = "localisation")
    private String localisation;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @NotNull
    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription;

    @NotNull
    @Column(name = "anne_lancement", nullable = false)
    private Integer anneLancement;

    @NotNull
    @Column(name = "mois_lancement", nullable = false)
    private Integer moisLancement;

    @NotNull
    @Column(name = "recup_heure_actif", nullable = false)
    private Boolean recupHeureActif;

    @Column(name = "date_resiliation")
    private LocalDate dateResiliation;

    @Column(name = "derniere_annee")
    private Integer derniereAnnee;

    @Column(name = "dernier_mois")
    private Integer dernierMois;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TiersFinanceur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public TiersFinanceur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return this.localisation;
    }

    public TiersFinanceur localisation(String localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public TiersFinanceur isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateInscription() {
        return this.dateInscription;
    }

    public TiersFinanceur dateInscription(LocalDate dateInscription) {
        this.setDateInscription(dateInscription);
        return this;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Integer getAnneLancement() {
        return this.anneLancement;
    }

    public TiersFinanceur anneLancement(Integer anneLancement) {
        this.setAnneLancement(anneLancement);
        return this;
    }

    public void setAnneLancement(Integer anneLancement) {
        this.anneLancement = anneLancement;
    }

    public Integer getMoisLancement() {
        return this.moisLancement;
    }

    public TiersFinanceur moisLancement(Integer moisLancement) {
        this.setMoisLancement(moisLancement);
        return this;
    }

    public void setMoisLancement(Integer moisLancement) {
        this.moisLancement = moisLancement;
    }

    public Boolean getRecupHeureActif() {
        return this.recupHeureActif;
    }

    public TiersFinanceur recupHeureActif(Boolean recupHeureActif) {
        this.setRecupHeureActif(recupHeureActif);
        return this;
    }

    public void setRecupHeureActif(Boolean recupHeureActif) {
        this.recupHeureActif = recupHeureActif;
    }

    public LocalDate getDateResiliation() {
        return this.dateResiliation;
    }

    public TiersFinanceur dateResiliation(LocalDate dateResiliation) {
        this.setDateResiliation(dateResiliation);
        return this;
    }

    public void setDateResiliation(LocalDate dateResiliation) {
        this.dateResiliation = dateResiliation;
    }

    public Integer getDerniereAnnee() {
        return this.derniereAnnee;
    }

    public TiersFinanceur derniereAnnee(Integer derniereAnnee) {
        this.setDerniereAnnee(derniereAnnee);
        return this;
    }

    public void setDerniereAnnee(Integer derniereAnnee) {
        this.derniereAnnee = derniereAnnee;
    }

    public Integer getDernierMois() {
        return this.dernierMois;
    }

    public TiersFinanceur dernierMois(Integer dernierMois) {
        this.setDernierMois(dernierMois);
        return this;
    }

    public void setDernierMois(Integer dernierMois) {
        this.dernierMois = dernierMois;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TiersFinanceur)) {
            return false;
        }
        return id != null && id.equals(((TiersFinanceur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TiersFinanceur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", dateInscription='" + getDateInscription() + "'" +
            ", anneLancement=" + getAnneLancement() +
            ", moisLancement=" + getMoisLancement() +
            ", recupHeureActif='" + getRecupHeureActif() + "'" +
            ", dateResiliation='" + getDateResiliation() + "'" +
            ", derniereAnnee=" + getDerniereAnnee() +
            ", dernierMois=" + getDernierMois() +
            "}";
    }
}
