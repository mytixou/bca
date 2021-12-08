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
 * A SoldeCi.
 */
@Entity
@Table(name = "solde_ci")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoldeCi implements Serializable {

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
    @Column(name = "annee", nullable = false, unique = true)
    private Integer annee;

    @NotNull
    @Column(name = "conso_montant_ci", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoMontantCi;

    @NotNull
    @Column(name = "conso_ci_rec", precision = 21, scale = 2, nullable = false)
    private BigDecimal consoCiRec;

    @NotNull
    @Column(name = "solde_montant_ci", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeMontantCi;

    @NotNull
    @Column(name = "solde_montant_ci_rec", precision = 21, scale = 2, nullable = false)
    private BigDecimal soldeMontantCiRec;

    @ManyToOne
    @JsonIgnoreProperties(value = { "beneficiaire" }, allowSetters = true)
    private DroitsStrategieCi droitsStrategieCi;

    @JsonIgnoreProperties(value = { "soldeCi", "soldeApa", "soldePch", "soldePchE" }, allowSetters = true)
    @OneToOne(mappedBy = "soldeCi")
    private Pec pec;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SoldeCi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public SoldeCi date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public SoldeCi isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Boolean getIsDernier() {
        return this.isDernier;
    }

    public SoldeCi isDernier(Boolean isDernier) {
        this.setIsDernier(isDernier);
        return this;
    }

    public void setIsDernier(Boolean isDernier) {
        this.isDernier = isDernier;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public SoldeCi annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public BigDecimal getConsoMontantCi() {
        return this.consoMontantCi;
    }

    public SoldeCi consoMontantCi(BigDecimal consoMontantCi) {
        this.setConsoMontantCi(consoMontantCi);
        return this;
    }

    public void setConsoMontantCi(BigDecimal consoMontantCi) {
        this.consoMontantCi = consoMontantCi;
    }

    public BigDecimal getConsoCiRec() {
        return this.consoCiRec;
    }

    public SoldeCi consoCiRec(BigDecimal consoCiRec) {
        this.setConsoCiRec(consoCiRec);
        return this;
    }

    public void setConsoCiRec(BigDecimal consoCiRec) {
        this.consoCiRec = consoCiRec;
    }

    public BigDecimal getSoldeMontantCi() {
        return this.soldeMontantCi;
    }

    public SoldeCi soldeMontantCi(BigDecimal soldeMontantCi) {
        this.setSoldeMontantCi(soldeMontantCi);
        return this;
    }

    public void setSoldeMontantCi(BigDecimal soldeMontantCi) {
        this.soldeMontantCi = soldeMontantCi;
    }

    public BigDecimal getSoldeMontantCiRec() {
        return this.soldeMontantCiRec;
    }

    public SoldeCi soldeMontantCiRec(BigDecimal soldeMontantCiRec) {
        this.setSoldeMontantCiRec(soldeMontantCiRec);
        return this;
    }

    public void setSoldeMontantCiRec(BigDecimal soldeMontantCiRec) {
        this.soldeMontantCiRec = soldeMontantCiRec;
    }

    public DroitsStrategieCi getDroitsStrategieCi() {
        return this.droitsStrategieCi;
    }

    public void setDroitsStrategieCi(DroitsStrategieCi droitsStrategieCi) {
        this.droitsStrategieCi = droitsStrategieCi;
    }

    public SoldeCi droitsStrategieCi(DroitsStrategieCi droitsStrategieCi) {
        this.setDroitsStrategieCi(droitsStrategieCi);
        return this;
    }

    public Pec getPec() {
        return this.pec;
    }

    public void setPec(Pec pec) {
        if (this.pec != null) {
            this.pec.setSoldeCi(null);
        }
        if (pec != null) {
            pec.setSoldeCi(this);
        }
        this.pec = pec;
    }

    public SoldeCi pec(Pec pec) {
        this.setPec(pec);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoldeCi)) {
            return false;
        }
        return id != null && id.equals(((SoldeCi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoldeCi{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", isDernier='" + getIsDernier() + "'" +
            ", annee=" + getAnnee() +
            ", consoMontantCi=" + getConsoMontantCi() +
            ", consoCiRec=" + getConsoCiRec() +
            ", soldeMontantCi=" + getSoldeMontantCi() +
            ", soldeMontantCiRec=" + getSoldeMontantCiRec() +
            "}";
    }
}
