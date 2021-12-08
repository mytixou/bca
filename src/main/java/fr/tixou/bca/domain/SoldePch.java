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
 * A SoldePch.
 */
@Entity
@Table(name = "solde_pch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoldePch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @NotNull
    @Column(name = "is_dernier", nullable = false)
    private Boolean isDernier;

    @NotNull
    @Column(name = "annee", nullable = false)
    private Integer annee;

    @NotNull
    @Column(name = "mois", nullable = false)
    private Integer mois;

    @NotNull
    @Column(name = "conso_montant_pch_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantPchCotisations;

    @NotNull
    @Column(name = "conso_montant_pch_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantPchSalaire;

    @NotNull
    @Column(name = "solde_montant_pch", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeMontantPch;

    @NotNull
    @Column(name = "conso_heure_pch", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoHeurePch;

    @NotNull
    @Column(name = "solde_heure_pch", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeHeurePch;

    @ManyToOne
    @JsonIgnoreProperties(value = { "beneficiaire" }, allowSetters = true)
    private DroitsStrategiePch droitsStrategiePch;

    @JsonIgnoreProperties(value = { "soldeCi", "soldeApa", "soldePch", "soldePchE" }, allowSetters = true)
    @OneToOne(mappedBy = "soldePch")
    private Pec pec;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SoldePch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public SoldePch date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public SoldePch isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Boolean getIsDernier() {
        return this.isDernier;
    }

    public SoldePch isDernier(Boolean isDernier) {
        this.setIsDernier(isDernier);
        return this;
    }

    public void setIsDernier(Boolean isDernier) {
        this.isDernier = isDernier;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public SoldePch annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return this.mois;
    }

    public SoldePch mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public BigDecimal getConsoMontantPchCotisations() {
        return this.consoMontantPchCotisations;
    }

    public SoldePch consoMontantPchCotisations(BigDecimal consoMontantPchCotisations) {
        this.setConsoMontantPchCotisations(consoMontantPchCotisations);
        return this;
    }

    public void setConsoMontantPchCotisations(BigDecimal consoMontantPchCotisations) {
        this.consoMontantPchCotisations = consoMontantPchCotisations;
    }

    public BigDecimal getConsoMontantPchSalaire() {
        return this.consoMontantPchSalaire;
    }

    public SoldePch consoMontantPchSalaire(BigDecimal consoMontantPchSalaire) {
        this.setConsoMontantPchSalaire(consoMontantPchSalaire);
        return this;
    }

    public void setConsoMontantPchSalaire(BigDecimal consoMontantPchSalaire) {
        this.consoMontantPchSalaire = consoMontantPchSalaire;
    }

    public BigDecimal getSoldeMontantPch() {
        return this.soldeMontantPch;
    }

    public SoldePch soldeMontantPch(BigDecimal soldeMontantPch) {
        this.setSoldeMontantPch(soldeMontantPch);
        return this;
    }

    public void setSoldeMontantPch(BigDecimal soldeMontantPch) {
        this.soldeMontantPch = soldeMontantPch;
    }

    public BigDecimal getConsoHeurePch() {
        return this.consoHeurePch;
    }

    public SoldePch consoHeurePch(BigDecimal consoHeurePch) {
        this.setConsoHeurePch(consoHeurePch);
        return this;
    }

    public void setConsoHeurePch(BigDecimal consoHeurePch) {
        this.consoHeurePch = consoHeurePch;
    }

    public BigDecimal getSoldeHeurePch() {
        return this.soldeHeurePch;
    }

    public SoldePch soldeHeurePch(BigDecimal soldeHeurePch) {
        this.setSoldeHeurePch(soldeHeurePch);
        return this;
    }

    public void setSoldeHeurePch(BigDecimal soldeHeurePch) {
        this.soldeHeurePch = soldeHeurePch;
    }

    public DroitsStrategiePch getDroitsStrategiePch() {
        return this.droitsStrategiePch;
    }

    public void setDroitsStrategiePch(DroitsStrategiePch droitsStrategiePch) {
        this.droitsStrategiePch = droitsStrategiePch;
    }

    public SoldePch droitsStrategiePch(DroitsStrategiePch droitsStrategiePch) {
        this.setDroitsStrategiePch(droitsStrategiePch);
        return this;
    }

    public Pec getPec() {
        return this.pec;
    }

    public void setPec(Pec pec) {
        if (this.pec != null) {
            this.pec.setSoldePch(null);
        }
        if (pec != null) {
            pec.setSoldePch(this);
        }
        this.pec = pec;
    }

    public SoldePch pec(Pec pec) {
        this.setPec(pec);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoldePch)) {
            return false;
        }
        return id != null && id.equals(((SoldePch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoldePch{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", isDernier='" + getIsDernier() + "'" +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", consoMontantPchCotisations=" + getConsoMontantPchCotisations() +
            ", consoMontantPchSalaire=" + getConsoMontantPchSalaire() +
            ", soldeMontantPch=" + getSoldeMontantPch() +
            ", consoHeurePch=" + getConsoHeurePch() +
            ", soldeHeurePch=" + getSoldeHeurePch() +
            "}";
    }
}
