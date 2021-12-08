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
 * A SoldePchE.
 */
@Entity
@Table(name = "solde_pch_e")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoldePchE implements Serializable {

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
    @Column(name = "conso_montant_pch_e_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantPchECotisations;

    @NotNull
    @Column(name = "conso_montant_pch_e_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantPchESalaire;

    @NotNull
    @Column(name = "solde_montant_pch_e", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeMontantPchE;

    @NotNull
    @Column(name = "conso_heure_pch_e", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoHeurePchE;

    @NotNull
    @Column(name = "solde_heure_pch_e", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeHeurePchE;

    @ManyToOne
    @JsonIgnoreProperties(value = { "beneficiaire" }, allowSetters = true)
    private DroitsStrategiePchE droitsStrategiePchE;

    @JsonIgnoreProperties(value = { "soldeCi", "soldeApa", "soldePch", "soldePchE" }, allowSetters = true)
    @OneToOne(mappedBy = "soldePchE")
    private Pec pec;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SoldePchE id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public SoldePchE date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public SoldePchE isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Boolean getIsDernier() {
        return this.isDernier;
    }

    public SoldePchE isDernier(Boolean isDernier) {
        this.setIsDernier(isDernier);
        return this;
    }

    public void setIsDernier(Boolean isDernier) {
        this.isDernier = isDernier;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public SoldePchE annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return this.mois;
    }

    public SoldePchE mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public BigDecimal getConsoMontantPchECotisations() {
        return this.consoMontantPchECotisations;
    }

    public SoldePchE consoMontantPchECotisations(BigDecimal consoMontantPchECotisations) {
        this.setConsoMontantPchECotisations(consoMontantPchECotisations);
        return this;
    }

    public void setConsoMontantPchECotisations(BigDecimal consoMontantPchECotisations) {
        this.consoMontantPchECotisations = consoMontantPchECotisations;
    }

    public BigDecimal getConsoMontantPchESalaire() {
        return this.consoMontantPchESalaire;
    }

    public SoldePchE consoMontantPchESalaire(BigDecimal consoMontantPchESalaire) {
        this.setConsoMontantPchESalaire(consoMontantPchESalaire);
        return this;
    }

    public void setConsoMontantPchESalaire(BigDecimal consoMontantPchESalaire) {
        this.consoMontantPchESalaire = consoMontantPchESalaire;
    }

    public BigDecimal getSoldeMontantPchE() {
        return this.soldeMontantPchE;
    }

    public SoldePchE soldeMontantPchE(BigDecimal soldeMontantPchE) {
        this.setSoldeMontantPchE(soldeMontantPchE);
        return this;
    }

    public void setSoldeMontantPchE(BigDecimal soldeMontantPchE) {
        this.soldeMontantPchE = soldeMontantPchE;
    }

    public BigDecimal getConsoHeurePchE() {
        return this.consoHeurePchE;
    }

    public SoldePchE consoHeurePchE(BigDecimal consoHeurePchE) {
        this.setConsoHeurePchE(consoHeurePchE);
        return this;
    }

    public void setConsoHeurePchE(BigDecimal consoHeurePchE) {
        this.consoHeurePchE = consoHeurePchE;
    }

    public BigDecimal getSoldeHeurePchE() {
        return this.soldeHeurePchE;
    }

    public SoldePchE soldeHeurePchE(BigDecimal soldeHeurePchE) {
        this.setSoldeHeurePchE(soldeHeurePchE);
        return this;
    }

    public void setSoldeHeurePchE(BigDecimal soldeHeurePchE) {
        this.soldeHeurePchE = soldeHeurePchE;
    }

    public DroitsStrategiePchE getDroitsStrategiePchE() {
        return this.droitsStrategiePchE;
    }

    public void setDroitsStrategiePchE(DroitsStrategiePchE droitsStrategiePchE) {
        this.droitsStrategiePchE = droitsStrategiePchE;
    }

    public SoldePchE droitsStrategiePchE(DroitsStrategiePchE droitsStrategiePchE) {
        this.setDroitsStrategiePchE(droitsStrategiePchE);
        return this;
    }

    public Pec getPec() {
        return this.pec;
    }

    public void setPec(Pec pec) {
        if (this.pec != null) {
            this.pec.setSoldePchE(null);
        }
        if (pec != null) {
            pec.setSoldePchE(this);
        }
        this.pec = pec;
    }

    public SoldePchE pec(Pec pec) {
        this.setPec(pec);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoldePchE)) {
            return false;
        }
        return id != null && id.equals(((SoldePchE) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoldePchE{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", isDernier='" + getIsDernier() + "'" +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", consoMontantPchECotisations=" + getConsoMontantPchECotisations() +
            ", consoMontantPchESalaire=" + getConsoMontantPchESalaire() +
            ", soldeMontantPchE=" + getSoldeMontantPchE() +
            ", consoHeurePchE=" + getConsoHeurePchE() +
            ", soldeHeurePchE=" + getSoldeHeurePchE() +
            "}";
    }
}
