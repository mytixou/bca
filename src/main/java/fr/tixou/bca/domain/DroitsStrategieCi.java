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
 * A DroitsStrategieCi.
 */
@Entity
@Table(name = "droits_strategie_ci")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DroitsStrategieCi implements Serializable {

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

    public DroitsStrategieCi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public DroitsStrategieCi isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Integer getAnne() {
        return this.anne;
    }

    public DroitsStrategieCi anne(Integer anne) {
        this.setAnne(anne);
        return this;
    }

    public void setAnne(Integer anne) {
        this.anne = anne;
    }

    public BigDecimal getMontantPlafondDefaut() {
        return this.montantPlafondDefaut;
    }

    public DroitsStrategieCi montantPlafondDefaut(BigDecimal montantPlafondDefaut) {
        this.setMontantPlafondDefaut(montantPlafondDefaut);
        return this;
    }

    public void setMontantPlafondDefaut(BigDecimal montantPlafondDefaut) {
        this.montantPlafondDefaut = montantPlafondDefaut;
    }

    public BigDecimal getMontantPlafondHandicape() {
        return this.montantPlafondHandicape;
    }

    public DroitsStrategieCi montantPlafondHandicape(BigDecimal montantPlafondHandicape) {
        this.setMontantPlafondHandicape(montantPlafondHandicape);
        return this;
    }

    public void setMontantPlafondHandicape(BigDecimal montantPlafondHandicape) {
        this.montantPlafondHandicape = montantPlafondHandicape;
    }

    public BigDecimal getMontantPlafondDefautPlus() {
        return this.montantPlafondDefautPlus;
    }

    public DroitsStrategieCi montantPlafondDefautPlus(BigDecimal montantPlafondDefautPlus) {
        this.setMontantPlafondDefautPlus(montantPlafondDefautPlus);
        return this;
    }

    public void setMontantPlafondDefautPlus(BigDecimal montantPlafondDefautPlus) {
        this.montantPlafondDefautPlus = montantPlafondDefautPlus;
    }

    public BigDecimal getMontantPlafondHandicapePlus() {
        return this.montantPlafondHandicapePlus;
    }

    public DroitsStrategieCi montantPlafondHandicapePlus(BigDecimal montantPlafondHandicapePlus) {
        this.setMontantPlafondHandicapePlus(montantPlafondHandicapePlus);
        return this;
    }

    public void setMontantPlafondHandicapePlus(BigDecimal montantPlafondHandicapePlus) {
        this.montantPlafondHandicapePlus = montantPlafondHandicapePlus;
    }

    public BigDecimal getTauxSalaire() {
        return this.tauxSalaire;
    }

    public DroitsStrategieCi tauxSalaire(BigDecimal tauxSalaire) {
        this.setTauxSalaire(tauxSalaire);
        return this;
    }

    public void setTauxSalaire(BigDecimal tauxSalaire) {
        this.tauxSalaire = tauxSalaire;
    }

    public LocalDate getDateOuverture() {
        return this.dateOuverture;
    }

    public DroitsStrategieCi dateOuverture(LocalDate dateOuverture) {
        this.setDateOuverture(dateOuverture);
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateFermeture() {
        return this.dateFermeture;
    }

    public DroitsStrategieCi dateFermeture(LocalDate dateFermeture) {
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

    public DroitsStrategieCi beneficiaire(Beneficiaire beneficiaire) {
        this.setBeneficiaire(beneficiaire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroitsStrategieCi)) {
            return false;
        }
        return id != null && id.equals(((DroitsStrategieCi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroitsStrategieCi{" +
            "id=" + getId() +
            ", isActif='" + getIsActif() + "'" +
            ", anne=" + getAnne() +
            ", montantPlafondDefaut=" + getMontantPlafondDefaut() +
            ", montantPlafondHandicape=" + getMontantPlafondHandicape() +
            ", montantPlafondDefautPlus=" + getMontantPlafondDefautPlus() +
            ", montantPlafondHandicapePlus=" + getMontantPlafondHandicapePlus() +
            ", tauxSalaire=" + getTauxSalaire() +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateFermeture='" + getDateFermeture() + "'" +
            "}";
    }
}
