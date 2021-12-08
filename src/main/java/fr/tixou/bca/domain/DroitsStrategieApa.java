package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DroitsStrategieApa.
 */
@Entity
@Table(name = "droits_strategie_apa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DroitsStrategieApa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @NotNull
    @Column(name = "anne", nullable = false, unique = true)
    private Integer anne;

    @NotNull
    @Column(name = "mois", nullable = false, unique = true)
    private Integer mois;

    @NotNull
    @Column(name = "montant_plafond", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafond;

    @NotNull
    @Column(name = "montant_plafond_plus", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondPlus;

    @NotNull
    @Column(name = "nb_heure_plafond", precision = 21, scale = 2, nullable = false)
    private BigDecimal nbHeurePlafond;

    @NotNull
    @Column(name = "taux_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal tauxCotisations;

    @NotNull
    @Column(name = "date_ouverture", nullable = false)
    private LocalDate dateOuverture;

    @Column(name = "date_fermeture")
    private LocalDate dateFermeture;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enfants" }, allowSetters = true)
    private Beneficiaire beneficiaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DroitsStrategieApa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public DroitsStrategieApa isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Integer getAnne() {
        return this.anne;
    }

    public DroitsStrategieApa anne(Integer anne) {
        this.setAnne(anne);
        return this;
    }

    public void setAnne(Integer anne) {
        this.anne = anne;
    }

    public Integer getMois() {
        return this.mois;
    }

    public DroitsStrategieApa mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public BigDecimal getMontantPlafond() {
        return this.montantPlafond;
    }

    public DroitsStrategieApa montantPlafond(BigDecimal montantPlafond) {
        this.setMontantPlafond(montantPlafond);
        return this;
    }

    public void setMontantPlafond(BigDecimal montantPlafond) {
        this.montantPlafond = montantPlafond;
    }

    public BigDecimal getMontantPlafondPlus() {
        return this.montantPlafondPlus;
    }

    public DroitsStrategieApa montantPlafondPlus(BigDecimal montantPlafondPlus) {
        this.setMontantPlafondPlus(montantPlafondPlus);
        return this;
    }

    public void setMontantPlafondPlus(BigDecimal montantPlafondPlus) {
        this.montantPlafondPlus = montantPlafondPlus;
    }

    public BigDecimal getNbHeurePlafond() {
        return this.nbHeurePlafond;
    }

    public DroitsStrategieApa nbHeurePlafond(BigDecimal nbHeurePlafond) {
        this.setNbHeurePlafond(nbHeurePlafond);
        return this;
    }

    public void setNbHeurePlafond(BigDecimal nbHeurePlafond) {
        this.nbHeurePlafond = nbHeurePlafond;
    }

    public BigDecimal getTauxCotisations() {
        return this.tauxCotisations;
    }

    public DroitsStrategieApa tauxCotisations(BigDecimal tauxCotisations) {
        this.setTauxCotisations(tauxCotisations);
        return this;
    }

    public void setTauxCotisations(BigDecimal tauxCotisations) {
        this.tauxCotisations = tauxCotisations;
    }

    public LocalDate getDateOuverture() {
        return this.dateOuverture;
    }

    public DroitsStrategieApa dateOuverture(LocalDate dateOuverture) {
        this.setDateOuverture(dateOuverture);
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateFermeture() {
        return this.dateFermeture;
    }

    public DroitsStrategieApa dateFermeture(LocalDate dateFermeture) {
        this.setDateFermeture(dateFermeture);
        return this;
    }

    public void setDateFermeture(LocalDate dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public Beneficiaire getBeneficiaire() {
        return this.beneficiaire;
    }

    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public DroitsStrategieApa beneficiaire(Beneficiaire beneficiaire) {
        this.setBeneficiaire(beneficiaire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroitsStrategieApa)) {
            return false;
        }
        return id != null && id.equals(((DroitsStrategieApa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroitsStrategieApa{" +
            "id=" + getId() +
            ", isActif='" + getIsActif() + "'" +
            ", anne=" + getAnne() +
            ", mois=" + getMois() +
            ", montantPlafond=" + getMontantPlafond() +
            ", montantPlafondPlus=" + getMontantPlafondPlus() +
            ", nbHeurePlafond=" + getNbHeurePlafond() +
            ", tauxCotisations=" + getTauxCotisations() +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateFermeture='" + getDateFermeture() + "'" +
            "}";
    }
}
