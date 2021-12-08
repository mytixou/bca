package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StrategieCmgAssmat.
 */
@Entity
@Table(name = "strategie_cmg_assmat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrategieCmgAssmat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "anne", nullable = false)
    private Integer anne;

    @NotNull
    @Column(name = "mois", nullable = false)
    private Integer mois;

    @NotNull
    @Column(name = "nb_heure_seuil_plafond", precision = 21, scale = 2, nullable = false)
    private BigDecimal nbHeureSeuilPlafond;

    @NotNull
    @Column(name = "taux_salaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal tauxSalaire;

    @NotNull
    @Column(name = "taux_cotisations", precision = 21, scale = 2, nullable = false)
    private BigDecimal tauxCotisations;

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

    @OneToMany(mappedBy = "strategieCmgAssmat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strategieCmgAssmat" }, allowSetters = true)
    private Set<TrancheAideEnfantAssmat> trancheAideEnfantAssmats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "aide" }, allowSetters = true)
    private Aide aide;

    @ManyToOne
    private TiersFinanceur tiersFinanceur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StrategieCmgAssmat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnne() {
        return this.anne;
    }

    public StrategieCmgAssmat anne(Integer anne) {
        this.setAnne(anne);
        return this;
    }

    public void setAnne(Integer anne) {
        this.anne = anne;
    }

    public Integer getMois() {
        return this.mois;
    }

    public StrategieCmgAssmat mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public BigDecimal getNbHeureSeuilPlafond() {
        return this.nbHeureSeuilPlafond;
    }

    public StrategieCmgAssmat nbHeureSeuilPlafond(BigDecimal nbHeureSeuilPlafond) {
        this.setNbHeureSeuilPlafond(nbHeureSeuilPlafond);
        return this;
    }

    public void setNbHeureSeuilPlafond(BigDecimal nbHeureSeuilPlafond) {
        this.nbHeureSeuilPlafond = nbHeureSeuilPlafond;
    }

    public BigDecimal getTauxSalaire() {
        return this.tauxSalaire;
    }

    public StrategieCmgAssmat tauxSalaire(BigDecimal tauxSalaire) {
        this.setTauxSalaire(tauxSalaire);
        return this;
    }

    public void setTauxSalaire(BigDecimal tauxSalaire) {
        this.tauxSalaire = tauxSalaire;
    }

    public BigDecimal getTauxCotisations() {
        return this.tauxCotisations;
    }

    public StrategieCmgAssmat tauxCotisations(BigDecimal tauxCotisations) {
        this.setTauxCotisations(tauxCotisations);
        return this;
    }

    public void setTauxCotisations(BigDecimal tauxCotisations) {
        this.tauxCotisations = tauxCotisations;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public StrategieCmgAssmat isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public Instant getDateCreated() {
        return this.dateCreated;
    }

    public StrategieCmgAssmat dateCreated(Instant dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsUpdated() {
        return this.isUpdated;
    }

    public StrategieCmgAssmat isUpdated(Boolean isUpdated) {
        this.setIsUpdated(isUpdated);
        return this;
    }

    public void setIsUpdated(Boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public Instant getDateModified() {
        return this.dateModified;
    }

    public StrategieCmgAssmat dateModified(Instant dateModified) {
        this.setDateModified(dateModified);
        return this;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    public Set<TrancheAideEnfantAssmat> getTrancheAideEnfantAssmats() {
        return this.trancheAideEnfantAssmats;
    }

    public void setTrancheAideEnfantAssmats(Set<TrancheAideEnfantAssmat> trancheAideEnfantAssmats) {
        if (this.trancheAideEnfantAssmats != null) {
            this.trancheAideEnfantAssmats.forEach(i -> i.setStrategieCmgAssmat(null));
        }
        if (trancheAideEnfantAssmats != null) {
            trancheAideEnfantAssmats.forEach(i -> i.setStrategieCmgAssmat(this));
        }
        this.trancheAideEnfantAssmats = trancheAideEnfantAssmats;
    }

    public StrategieCmgAssmat trancheAideEnfantAssmats(Set<TrancheAideEnfantAssmat> trancheAideEnfantAssmats) {
        this.setTrancheAideEnfantAssmats(trancheAideEnfantAssmats);
        return this;
    }

    public StrategieCmgAssmat addTrancheAideEnfantAssmat(TrancheAideEnfantAssmat trancheAideEnfantAssmat) {
        this.trancheAideEnfantAssmats.add(trancheAideEnfantAssmat);
        trancheAideEnfantAssmat.setStrategieCmgAssmat(this);
        return this;
    }

    public StrategieCmgAssmat removeTrancheAideEnfantAssmat(TrancheAideEnfantAssmat trancheAideEnfantAssmat) {
        this.trancheAideEnfantAssmats.remove(trancheAideEnfantAssmat);
        trancheAideEnfantAssmat.setStrategieCmgAssmat(null);
        return this;
    }

    public Aide getAide() {
        return this.aide;
    }

    public void setAide(Aide aide) {
        this.aide = aide;
    }

    public StrategieCmgAssmat aide(Aide aide) {
        this.setAide(aide);
        return this;
    }

    public TiersFinanceur getTiersFinanceur() {
        return this.tiersFinanceur;
    }

    public void setTiersFinanceur(TiersFinanceur tiersFinanceur) {
        this.tiersFinanceur = tiersFinanceur;
    }

    public StrategieCmgAssmat tiersFinanceur(TiersFinanceur tiersFinanceur) {
        this.setTiersFinanceur(tiersFinanceur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrategieCmgAssmat)) {
            return false;
        }
        return id != null && id.equals(((StrategieCmgAssmat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrategieCmgAssmat{" +
            "id=" + getId() +
            ", anne=" + getAnne() +
            ", mois=" + getMois() +
            ", nbHeureSeuilPlafond=" + getNbHeureSeuilPlafond() +
            ", tauxSalaire=" + getTauxSalaire() +
            ", tauxCotisations=" + getTauxCotisations() +
            ", isActif='" + getIsActif() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", isUpdated='" + getIsUpdated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            "}";
    }
}
