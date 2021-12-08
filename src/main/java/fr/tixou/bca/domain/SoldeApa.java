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
 * A SoldeApa.
 */
@Entity
@Table(name = "solde_apa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoldeApa implements Serializable {

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
    @Column(name = "conso_montant_apa_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantApaCotisations;

    @NotNull
    @Column(name = "conso_montant_apa_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantApaSalaire;

    @NotNull
    @Column(name = "solde_montant_apa", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeMontantApa;

    @NotNull
    @Column(name = "conso_heure_apa", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoHeureApa;

    @NotNull
    @Column(name = "solde_heure_apa", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeHeureApa;

    @ManyToOne
    @JsonIgnoreProperties(value = { "beneficiaire" }, allowSetters = true)
    private DroitsStrategieApa droitsStrategieApa;

    @JsonIgnoreProperties(value = { "soldeCi", "soldeApa", "soldePch", "soldePchE" }, allowSetters = true)
    @OneToOne(mappedBy = "soldeApa")
    private Pec pec;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SoldeApa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public SoldeApa date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public SoldeApa isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Boolean getIsDernier() {
        return this.isDernier;
    }

    public SoldeApa isDernier(Boolean isDernier) {
        this.setIsDernier(isDernier);
        return this;
    }

    public void setIsDernier(Boolean isDernier) {
        this.isDernier = isDernier;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public SoldeApa annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return this.mois;
    }

    public SoldeApa mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public BigDecimal getConsoMontantApaCotisations() {
        return this.consoMontantApaCotisations;
    }

    public SoldeApa consoMontantApaCotisations(BigDecimal consoMontantApaCotisations) {
        this.setConsoMontantApaCotisations(consoMontantApaCotisations);
        return this;
    }

    public void setConsoMontantApaCotisations(BigDecimal consoMontantApaCotisations) {
        this.consoMontantApaCotisations = consoMontantApaCotisations;
    }

    public BigDecimal getConsoMontantApaSalaire() {
        return this.consoMontantApaSalaire;
    }

    public SoldeApa consoMontantApaSalaire(BigDecimal consoMontantApaSalaire) {
        this.setConsoMontantApaSalaire(consoMontantApaSalaire);
        return this;
    }

    public void setConsoMontantApaSalaire(BigDecimal consoMontantApaSalaire) {
        this.consoMontantApaSalaire = consoMontantApaSalaire;
    }

    public BigDecimal getSoldeMontantApa() {
        return this.soldeMontantApa;
    }

    public SoldeApa soldeMontantApa(BigDecimal soldeMontantApa) {
        this.setSoldeMontantApa(soldeMontantApa);
        return this;
    }

    public void setSoldeMontantApa(BigDecimal soldeMontantApa) {
        this.soldeMontantApa = soldeMontantApa;
    }

    public BigDecimal getConsoHeureApa() {
        return this.consoHeureApa;
    }

    public SoldeApa consoHeureApa(BigDecimal consoHeureApa) {
        this.setConsoHeureApa(consoHeureApa);
        return this;
    }

    public void setConsoHeureApa(BigDecimal consoHeureApa) {
        this.consoHeureApa = consoHeureApa;
    }

    public BigDecimal getSoldeHeureApa() {
        return this.soldeHeureApa;
    }

    public SoldeApa soldeHeureApa(BigDecimal soldeHeureApa) {
        this.setSoldeHeureApa(soldeHeureApa);
        return this;
    }

    public void setSoldeHeureApa(BigDecimal soldeHeureApa) {
        this.soldeHeureApa = soldeHeureApa;
    }

    public DroitsStrategieApa getDroitsStrategieApa() {
        return this.droitsStrategieApa;
    }

    public void setDroitsStrategieApa(DroitsStrategieApa droitsStrategieApa) {
        this.droitsStrategieApa = droitsStrategieApa;
    }

    public SoldeApa droitsStrategieApa(DroitsStrategieApa droitsStrategieApa) {
        this.setDroitsStrategieApa(droitsStrategieApa);
        return this;
    }

    public Pec getPec() {
        return this.pec;
    }

    public void setPec(Pec pec) {
        if (this.pec != null) {
            this.pec.setSoldeApa(null);
        }
        if (pec != null) {
            pec.setSoldeApa(this);
        }
        this.pec = pec;
    }

    public SoldeApa pec(Pec pec) {
        this.setPec(pec);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoldeApa)) {
            return false;
        }
        return id != null && id.equals(((SoldeApa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoldeApa{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", isDernier='" + getIsDernier() + "'" +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", consoMontantApaCotisations=" + getConsoMontantApaCotisations() +
            ", consoMontantApaSalaire=" + getConsoMontantApaSalaire() +
            ", soldeMontantApa=" + getSoldeMontantApa() +
            ", consoHeureApa=" + getConsoHeureApa() +
            ", soldeHeureApa=" + getSoldeHeureApa() +
            "}";
    }
}
