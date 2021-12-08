package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StrategieApa.
 */
@Entity
@Table(name = "strategie_apa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrategieApa implements Serializable {

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
    @Column(name = "date_mensuelle_debut_validite", nullable = false)
    private LocalDate dateMensuelleDebutValidite;

    @NotNull
    @Column(name = "anne", nullable = false, unique = true)
    private Integer anne;

    @NotNull
    @Column(name = "mois", nullable = false, unique = true)
    private Integer mois;

    @NotNull
    @Column(name = "montant_plafond_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondSalaire;

    @NotNull
    @Column(name = "montant_plafond_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondCotisations;

    @NotNull
    @Column(name = "montant_plafond_salaire_plus", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondSalairePlus;

    @NotNull
    @Column(name = "montant_plafond_cotisations_plus", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondCotisationsPlus;

    @NotNull
    @Column(name = "nb_heure_salaire_plafond", precision = 21, scale = 2, nullable = false)
    private BigDecimal nbHeureSalairePlafond;

    @NotNull
    @Column(name = "taux_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal tauxSalaire;

    @NotNull
    @Column(name = "taux_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal tauxCotisations;

    @ManyToOne
    @JsonIgnoreProperties(value = { "aide" }, allowSetters = true)
    private Aide aide;

    @ManyToOne
    private TiersFinanceur tiersFinanceur;

    @ManyToMany
    @JoinTable(
        name = "rel_strategie_apa__nature_activite",
        joinColumns = @JoinColumn(name = "strategie_apa_id"),
        inverseJoinColumns = @JoinColumn(name = "nature_activite_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strategieCis", "strategieApas", "strategiePches", "strategiePchES" }, allowSetters = true)
    private Set<NatureActivite> natureActivites = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_strategie_apa__nature_montant",
        joinColumns = @JoinColumn(name = "strategie_apa_id"),
        inverseJoinColumns = @JoinColumn(name = "nature_montant_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strategieCis", "strategieApas", "strategiePches", "strategiePchES" }, allowSetters = true)
    private Set<NatureMontant> natureMontants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StrategieApa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public StrategieApa isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateMensuelleDebutValidite() {
        return this.dateMensuelleDebutValidite;
    }

    public StrategieApa dateMensuelleDebutValidite(LocalDate dateMensuelleDebutValidite) {
        this.setDateMensuelleDebutValidite(dateMensuelleDebutValidite);
        return this;
    }

    public void setDateMensuelleDebutValidite(LocalDate dateMensuelleDebutValidite) {
        this.dateMensuelleDebutValidite = dateMensuelleDebutValidite;
    }

    public Integer getAnne() {
        return this.anne;
    }

    public StrategieApa anne(Integer anne) {
        this.setAnne(anne);
        return this;
    }

    public void setAnne(Integer anne) {
        this.anne = anne;
    }

    public Integer getMois() {
        return this.mois;
    }

    public StrategieApa mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public BigDecimal getMontantPlafondSalaire() {
        return this.montantPlafondSalaire;
    }

    public StrategieApa montantPlafondSalaire(BigDecimal montantPlafondSalaire) {
        this.setMontantPlafondSalaire(montantPlafondSalaire);
        return this;
    }

    public void setMontantPlafondSalaire(BigDecimal montantPlafondSalaire) {
        this.montantPlafondSalaire = montantPlafondSalaire;
    }

    public BigDecimal getMontantPlafondCotisations() {
        return this.montantPlafondCotisations;
    }

    public StrategieApa montantPlafondCotisations(BigDecimal montantPlafondCotisations) {
        this.setMontantPlafondCotisations(montantPlafondCotisations);
        return this;
    }

    public void setMontantPlafondCotisations(BigDecimal montantPlafondCotisations) {
        this.montantPlafondCotisations = montantPlafondCotisations;
    }

    public BigDecimal getMontantPlafondSalairePlus() {
        return this.montantPlafondSalairePlus;
    }

    public StrategieApa montantPlafondSalairePlus(BigDecimal montantPlafondSalairePlus) {
        this.setMontantPlafondSalairePlus(montantPlafondSalairePlus);
        return this;
    }

    public void setMontantPlafondSalairePlus(BigDecimal montantPlafondSalairePlus) {
        this.montantPlafondSalairePlus = montantPlafondSalairePlus;
    }

    public BigDecimal getMontantPlafondCotisationsPlus() {
        return this.montantPlafondCotisationsPlus;
    }

    public StrategieApa montantPlafondCotisationsPlus(BigDecimal montantPlafondCotisationsPlus) {
        this.setMontantPlafondCotisationsPlus(montantPlafondCotisationsPlus);
        return this;
    }

    public void setMontantPlafondCotisationsPlus(BigDecimal montantPlafondCotisationsPlus) {
        this.montantPlafondCotisationsPlus = montantPlafondCotisationsPlus;
    }

    public BigDecimal getNbHeureSalairePlafond() {
        return this.nbHeureSalairePlafond;
    }

    public StrategieApa nbHeureSalairePlafond(BigDecimal nbHeureSalairePlafond) {
        this.setNbHeureSalairePlafond(nbHeureSalairePlafond);
        return this;
    }

    public void setNbHeureSalairePlafond(BigDecimal nbHeureSalairePlafond) {
        this.nbHeureSalairePlafond = nbHeureSalairePlafond;
    }

    public BigDecimal getTauxSalaire() {
        return this.tauxSalaire;
    }

    public StrategieApa tauxSalaire(BigDecimal tauxSalaire) {
        this.setTauxSalaire(tauxSalaire);
        return this;
    }

    public void setTauxSalaire(BigDecimal tauxSalaire) {
        this.tauxSalaire = tauxSalaire;
    }

    public BigDecimal getTauxCotisations() {
        return this.tauxCotisations;
    }

    public StrategieApa tauxCotisations(BigDecimal tauxCotisations) {
        this.setTauxCotisations(tauxCotisations);
        return this;
    }

    public void setTauxCotisations(BigDecimal tauxCotisations) {
        this.tauxCotisations = tauxCotisations;
    }

    public Aide getAide() {
        return this.aide;
    }

    public void setAide(Aide aide) {
        this.aide = aide;
    }

    public StrategieApa aide(Aide aide) {
        this.setAide(aide);
        return this;
    }

    public TiersFinanceur getTiersFinanceur() {
        return this.tiersFinanceur;
    }

    public void setTiersFinanceur(TiersFinanceur tiersFinanceur) {
        this.tiersFinanceur = tiersFinanceur;
    }

    public StrategieApa tiersFinanceur(TiersFinanceur tiersFinanceur) {
        this.setTiersFinanceur(tiersFinanceur);
        return this;
    }

    public Set<NatureActivite> getNatureActivites() {
        return this.natureActivites;
    }

    public void setNatureActivites(Set<NatureActivite> natureActivites) {
        this.natureActivites = natureActivites;
    }

    public StrategieApa natureActivites(Set<NatureActivite> natureActivites) {
        this.setNatureActivites(natureActivites);
        return this;
    }

    public StrategieApa addNatureActivite(NatureActivite natureActivite) {
        this.natureActivites.add(natureActivite);
        natureActivite.getStrategieApas().add(this);
        return this;
    }

    public StrategieApa removeNatureActivite(NatureActivite natureActivite) {
        this.natureActivites.remove(natureActivite);
        natureActivite.getStrategieApas().remove(this);
        return this;
    }

    public Set<NatureMontant> getNatureMontants() {
        return this.natureMontants;
    }

    public void setNatureMontants(Set<NatureMontant> natureMontants) {
        this.natureMontants = natureMontants;
    }

    public StrategieApa natureMontants(Set<NatureMontant> natureMontants) {
        this.setNatureMontants(natureMontants);
        return this;
    }

    public StrategieApa addNatureMontant(NatureMontant natureMontant) {
        this.natureMontants.add(natureMontant);
        natureMontant.getStrategieApas().add(this);
        return this;
    }

    public StrategieApa removeNatureMontant(NatureMontant natureMontant) {
        this.natureMontants.remove(natureMontant);
        natureMontant.getStrategieApas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrategieApa)) {
            return false;
        }
        return id != null && id.equals(((StrategieApa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrategieApa{" +
            "id=" + getId() +
            ", isActif='" + getIsActif() + "'" +
            ", dateMensuelleDebutValidite='" + getDateMensuelleDebutValidite() + "'" +
            ", anne=" + getAnne() +
            ", mois=" + getMois() +
            ", montantPlafondSalaire=" + getMontantPlafondSalaire() +
            ", montantPlafondCotisations=" + getMontantPlafondCotisations() +
            ", montantPlafondSalairePlus=" + getMontantPlafondSalairePlus() +
            ", montantPlafondCotisationsPlus=" + getMontantPlafondCotisationsPlus() +
            ", nbHeureSalairePlafond=" + getNbHeureSalairePlafond() +
            ", tauxSalaire=" + getTauxSalaire() +
            ", tauxCotisations=" + getTauxCotisations() +
            "}";
    }
}
