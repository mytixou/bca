package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tixou.bca.domain.enumeration.TypeProduit;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Pec.
 */
@Entity
@Table(name = "pec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pec implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @NotNull
    @Column(name = "id_produit", nullable = false)
    private String idProduit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "produit", nullable = false)
    private TypeProduit produit;

    @NotNull
    @Column(name = "is_plus", nullable = false)
    private Boolean isPlus;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @NotNull
    @Column(name = "is_updated", nullable = false)
    private Boolean isUpdated;

    @Column(name = "date_modified")
    private Instant dateModified;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "pec_details", nullable = false)
    private String pecDetails;

    @JsonIgnoreProperties(value = { "droitsStrategieCi", "pec" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SoldeCi soldeCi;

    @JsonIgnoreProperties(value = { "droitsStrategieApa", "pec" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SoldeApa soldeApa;

    @JsonIgnoreProperties(value = { "droitsStrategiePch", "pec" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SoldePch soldePch;

    @JsonIgnoreProperties(value = { "droitsStrategiePchE", "pec" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SoldePchE soldePchE;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Pec id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdProduit() {
        return this.idProduit;
    }

    public Pec idProduit(String idProduit) {
        this.setIdProduit(idProduit);
        return this;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public TypeProduit getProduit() {
        return this.produit;
    }

    public Pec produit(TypeProduit produit) {
        this.setProduit(produit);
        return this;
    }

    public void setProduit(TypeProduit produit) {
        this.produit = produit;
    }

    public Boolean getIsPlus() {
        return this.isPlus;
    }

    public Pec isPlus(Boolean isPlus) {
        this.setIsPlus(isPlus);
        return this;
    }

    public void setIsPlus(Boolean isPlus) {
        this.isPlus = isPlus;
    }

    public Instant getDateCreated() {
        return this.dateCreated;
    }

    public Pec dateCreated(Instant dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsUpdated() {
        return this.isUpdated;
    }

    public Pec isUpdated(Boolean isUpdated) {
        this.setIsUpdated(isUpdated);
        return this;
    }

    public void setIsUpdated(Boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public Instant getDateModified() {
        return this.dateModified;
    }

    public Pec dateModified(Instant dateModified) {
        this.setDateModified(dateModified);
        return this;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Pec isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public String getPecDetails() {
        return this.pecDetails;
    }

    public Pec pecDetails(String pecDetails) {
        this.setPecDetails(pecDetails);
        return this;
    }

    public void setPecDetails(String pecDetails) {
        this.pecDetails = pecDetails;
    }

    public SoldeCi getSoldeCi() {
        return this.soldeCi;
    }

    public void setSoldeCi(SoldeCi soldeCi) {
        this.soldeCi = soldeCi;
    }

    public Pec soldeCi(SoldeCi soldeCi) {
        this.setSoldeCi(soldeCi);
        return this;
    }

    public SoldeApa getSoldeApa() {
        return this.soldeApa;
    }

    public void setSoldeApa(SoldeApa soldeApa) {
        this.soldeApa = soldeApa;
    }

    public Pec soldeApa(SoldeApa soldeApa) {
        this.setSoldeApa(soldeApa);
        return this;
    }

    public SoldePch getSoldePch() {
        return this.soldePch;
    }

    public void setSoldePch(SoldePch soldePch) {
        this.soldePch = soldePch;
    }

    public Pec soldePch(SoldePch soldePch) {
        this.setSoldePch(soldePch);
        return this;
    }

    public SoldePchE getSoldePchE() {
        return this.soldePchE;
    }

    public void setSoldePchE(SoldePchE soldePchE) {
        this.soldePchE = soldePchE;
    }

    public Pec soldePchE(SoldePchE soldePchE) {
        this.setSoldePchE(soldePchE);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pec)) {
            return false;
        }
        return id != null && id.equals(((Pec) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pec{" +
            "id=" + getId() +
            ", idProduit='" + getIdProduit() + "'" +
            ", produit='" + getProduit() + "'" +
            ", isPlus='" + getIsPlus() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", isUpdated='" + getIsUpdated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", pecDetails='" + getPecDetails() + "'" +
            "}";
    }
}
