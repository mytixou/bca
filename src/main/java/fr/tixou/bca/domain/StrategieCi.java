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
 * A StrategieCi.
 */
@Entity
@Table(name = "strategie_ci")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrategieCi implements Serializable {

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
    @Column(name = "date_annuelle_debut_validite", nullable = false)
    private LocalDate dateAnnuelleDebutValidite;

    @NotNull
    @Column(name = "anne", nullable = false, unique = true)
    private Integer anne;

    @NotNull
    @Column(name = "montant_plafond_defaut", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondDefaut;

    @NotNull
    @Column(name = "montant_plafond_handicape", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondHandicape;

    @NotNull
    @Column(name = "montant_plafond_defaut_plus", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondDefautPlus;

    @NotNull
    @Column(name = "montant_plafond_handicape_plus", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondHandicapePlus;

    @NotNull
    @Column(name = "taux_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal tauxSalaire;

    @ManyToOne
    @JsonIgnoreProperties(value = { "aide" }, allowSetters = true)
    private Aide aide;

    @ManyToOne
    private TiersFinanceur tiersFinanceur;

    @ManyToMany
    @JoinTable(
        name = "rel_strategie_ci__nature_activite",
        joinColumns = @JoinColumn(name = "strategie_ci_id"),
        inverseJoinColumns = @JoinColumn(name = "nature_activite_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strategieCis", "strategieApas", "strategiePches", "strategiePchES" }, allowSetters = true)
    private Set<NatureActivite> natureActivites = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_strategie_ci__nature_montant",
        joinColumns = @JoinColumn(name = "strategie_ci_id"),
        inverseJoinColumns = @JoinColumn(name = "nature_montant_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strategieCis", "strategieApas", "strategiePches", "strategiePchES" }, allowSetters = true)
    private Set<NatureMontant> natureMontants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StrategieCi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public StrategieCi isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateAnnuelleDebutValidite() {
        return this.dateAnnuelleDebutValidite;
    }

    public StrategieCi dateAnnuelleDebutValidite(LocalDate dateAnnuelleDebutValidite) {
        this.setDateAnnuelleDebutValidite(dateAnnuelleDebutValidite);
        return this;
    }

    public void setDateAnnuelleDebutValidite(LocalDate dateAnnuelleDebutValidite) {
        this.dateAnnuelleDebutValidite = dateAnnuelleDebutValidite;
    }

    public Integer getAnne() {
        return this.anne;
    }

    public StrategieCi anne(Integer anne) {
        this.setAnne(anne);
        return this;
    }

    public void setAnne(Integer anne) {
        this.anne = anne;
    }

    public BigDecimal getMontantPlafondDefaut() {
        return this.montantPlafondDefaut;
    }

    public StrategieCi montantPlafondDefaut(BigDecimal montantPlafondDefaut) {
        this.setMontantPlafondDefaut(montantPlafondDefaut);
        return this;
    }

    public void setMontantPlafondDefaut(BigDecimal montantPlafondDefaut) {
        this.montantPlafondDefaut = montantPlafondDefaut;
    }

    public BigDecimal getMontantPlafondHandicape() {
        return this.montantPlafondHandicape;
    }

    public StrategieCi montantPlafondHandicape(BigDecimal montantPlafondHandicape) {
        this.setMontantPlafondHandicape(montantPlafondHandicape);
        return this;
    }

    public void setMontantPlafondHandicape(BigDecimal montantPlafondHandicape) {
        this.montantPlafondHandicape = montantPlafondHandicape;
    }

    public BigDecimal getMontantPlafondDefautPlus() {
        return this.montantPlafondDefautPlus;
    }

    public StrategieCi montantPlafondDefautPlus(BigDecimal montantPlafondDefautPlus) {
        this.setMontantPlafondDefautPlus(montantPlafondDefautPlus);
        return this;
    }

    public void setMontantPlafondDefautPlus(BigDecimal montantPlafondDefautPlus) {
        this.montantPlafondDefautPlus = montantPlafondDefautPlus;
    }

    public BigDecimal getMontantPlafondHandicapePlus() {
        return this.montantPlafondHandicapePlus;
    }

    public StrategieCi montantPlafondHandicapePlus(BigDecimal montantPlafondHandicapePlus) {
        this.setMontantPlafondHandicapePlus(montantPlafondHandicapePlus);
        return this;
    }

    public void setMontantPlafondHandicapePlus(BigDecimal montantPlafondHandicapePlus) {
        this.montantPlafondHandicapePlus = montantPlafondHandicapePlus;
    }

    public BigDecimal getTauxSalaire() {
        return this.tauxSalaire;
    }

    public StrategieCi tauxSalaire(BigDecimal tauxSalaire) {
        this.setTauxSalaire(tauxSalaire);
        return this;
    }

    public void setTauxSalaire(BigDecimal tauxSalaire) {
        this.tauxSalaire = tauxSalaire;
    }

    public Aide getAide() {
        return this.aide;
    }

    public void setAide(Aide aide) {
        this.aide = aide;
    }

    public StrategieCi aide(Aide aide) {
        this.setAide(aide);
        return this;
    }

    public TiersFinanceur getTiersFinanceur() {
        return this.tiersFinanceur;
    }

    public void setTiersFinanceur(TiersFinanceur tiersFinanceur) {
        this.tiersFinanceur = tiersFinanceur;
    }

    public StrategieCi tiersFinanceur(TiersFinanceur tiersFinanceur) {
        this.setTiersFinanceur(tiersFinanceur);
        return this;
    }

    public Set<NatureActivite> getNatureActivites() {
        return this.natureActivites;
    }

    public void setNatureActivites(Set<NatureActivite> natureActivites) {
        this.natureActivites = natureActivites;
    }

    public StrategieCi natureActivites(Set<NatureActivite> natureActivites) {
        this.setNatureActivites(natureActivites);
        return this;
    }

    public StrategieCi addNatureActivite(NatureActivite natureActivite) {
        this.natureActivites.add(natureActivite);
        natureActivite.getStrategieCis().add(this);
        return this;
    }

    public StrategieCi removeNatureActivite(NatureActivite natureActivite) {
        this.natureActivites.remove(natureActivite);
        natureActivite.getStrategieCis().remove(this);
        return this;
    }

    public Set<NatureMontant> getNatureMontants() {
        return this.natureMontants;
    }

    public void setNatureMontants(Set<NatureMontant> natureMontants) {
        this.natureMontants = natureMontants;
    }

    public StrategieCi natureMontants(Set<NatureMontant> natureMontants) {
        this.setNatureMontants(natureMontants);
        return this;
    }

    public StrategieCi addNatureMontant(NatureMontant natureMontant) {
        this.natureMontants.add(natureMontant);
        natureMontant.getStrategieCis().add(this);
        return this;
    }

    public StrategieCi removeNatureMontant(NatureMontant natureMontant) {
        this.natureMontants.remove(natureMontant);
        natureMontant.getStrategieCis().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrategieCi)) {
            return false;
        }
        return id != null && id.equals(((StrategieCi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrategieCi{" +
            "id=" + getId() +
            ", isActif='" + getIsActif() + "'" +
            ", dateAnnuelleDebutValidite='" + getDateAnnuelleDebutValidite() + "'" +
            ", anne=" + getAnne() +
            ", montantPlafondDefaut=" + getMontantPlafondDefaut() +
            ", montantPlafondHandicape=" + getMontantPlafondHandicape() +
            ", montantPlafondDefautPlus=" + getMontantPlafondDefautPlus() +
            ", montantPlafondHandicapePlus=" + getMontantPlafondHandicapePlus() +
            ", tauxSalaire=" + getTauxSalaire() +
            "}";
    }
}
