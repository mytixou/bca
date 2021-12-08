package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TrancheAideEnfantGed.
 */
@Entity
@Table(name = "tranche_aide_enfant_ged")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TrancheAideEnfantGed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "anne", nullable = false, unique = true)
    private Integer anne;

    @NotNull
    @Column(name = "mois", nullable = false, unique = true)
    private Integer mois;

    @NotNull
    @Column(name = "age_enfant_revolu_sur_periode", nullable = false, unique = true)
    private Integer ageEnfantRevoluSurPeriode;

    @NotNull
    @Column(name = "montant_plafond_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPlafondSalaire;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @NotNull
    @Column(name = "is_updated", nullable = false)
    private Boolean isUpdated;

    @Column(name = "date_modified")
    private Instant dateModified;

    @ManyToOne
    @JsonIgnoreProperties(value = { "trancheAideEnfantAssmats", "aide", "tiersFinanceur" }, allowSetters = true)
    private StrategieCmgGed strategieCmgGed;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrancheAideEnfantGed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnne() {
        return this.anne;
    }

    public TrancheAideEnfantGed anne(Integer anne) {
        this.setAnne(anne);
        return this;
    }

    public void setAnne(Integer anne) {
        this.anne = anne;
    }

    public Integer getMois() {
        return this.mois;
    }

    public TrancheAideEnfantGed mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAgeEnfantRevoluSurPeriode() {
        return this.ageEnfantRevoluSurPeriode;
    }

    public TrancheAideEnfantGed ageEnfantRevoluSurPeriode(Integer ageEnfantRevoluSurPeriode) {
        this.setAgeEnfantRevoluSurPeriode(ageEnfantRevoluSurPeriode);
        return this;
    }

    public void setAgeEnfantRevoluSurPeriode(Integer ageEnfantRevoluSurPeriode) {
        this.ageEnfantRevoluSurPeriode = ageEnfantRevoluSurPeriode;
    }

    public BigDecimal getMontantPlafondSalaire() {
        return this.montantPlafondSalaire;
    }

    public TrancheAideEnfantGed montantPlafondSalaire(BigDecimal montantPlafondSalaire) {
        this.setMontantPlafondSalaire(montantPlafondSalaire);
        return this;
    }

    public void setMontantPlafondSalaire(BigDecimal montantPlafondSalaire) {
        this.montantPlafondSalaire = montantPlafondSalaire;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public TrancheAideEnfantGed isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Instant getDateCreated() {
        return this.dateCreated;
    }

    public TrancheAideEnfantGed dateCreated(Instant dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsUpdated() {
        return this.isUpdated;
    }

    public TrancheAideEnfantGed isUpdated(Boolean isUpdated) {
        this.setIsUpdated(isUpdated);
        return this;
    }

    public void setIsUpdated(Boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public Instant getDateModified() {
        return this.dateModified;
    }

    public TrancheAideEnfantGed dateModified(Instant dateModified) {
        this.setDateModified(dateModified);
        return this;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    public StrategieCmgGed getStrategieCmgGed() {
        return this.strategieCmgGed;
    }

    public void setStrategieCmgGed(StrategieCmgGed strategieCmgGed) {
        this.strategieCmgGed = strategieCmgGed;
    }

    public TrancheAideEnfantGed strategieCmgGed(StrategieCmgGed strategieCmgGed) {
        this.setStrategieCmgGed(strategieCmgGed);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrancheAideEnfantGed)) {
            return false;
        }
        return id != null && id.equals(((TrancheAideEnfantGed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrancheAideEnfantGed{" +
            "id=" + getId() +
            ", anne=" + getAnne() +
            ", mois=" + getMois() +
            ", ageEnfantRevoluSurPeriode=" + getAgeEnfantRevoluSurPeriode() +
            ", montantPlafondSalaire=" + getMontantPlafondSalaire() +
            ", isActif='" + getIsActif() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", isUpdated='" + getIsUpdated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            "}";
    }
}
