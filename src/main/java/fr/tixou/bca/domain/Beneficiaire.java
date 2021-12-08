package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Beneficiaire.
 */
@Entity
@Table(name = "beneficiaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Beneficiaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "externe_id", nullable = false, unique = true)
    private UUID externeId;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @Column(name = "date_desactivation")
    private LocalDate dateDesactivation;

    @NotNull
    @Column(name = "is_inscrit", nullable = false)
    private Boolean isInscrit;

    @NotNull
    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription;

    @Column(name = "date_resiliation")
    private LocalDate dateResiliation;

    @OneToMany(mappedBy = "beneficiaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "beneficiaire" }, allowSetters = true)
    private Set<Enfant> enfants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beneficiaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getExterneId() {
        return this.externeId;
    }

    public Beneficiaire externeId(UUID externeId) {
        this.setExterneId(externeId);
        return this;
    }

    public void setExterneId(UUID externeId) {
        this.externeId = externeId;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Beneficiaire isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateDesactivation() {
        return this.dateDesactivation;
    }

    public Beneficiaire dateDesactivation(LocalDate dateDesactivation) {
        this.setDateDesactivation(dateDesactivation);
        return this;
    }

    public void setDateDesactivation(LocalDate dateDesactivation) {
        this.dateDesactivation = dateDesactivation;
    }

    public Boolean getIsInscrit() {
        return this.isInscrit;
    }

    public Beneficiaire isInscrit(Boolean isInscrit) {
        this.setIsInscrit(isInscrit);
        return this;
    }

    public void setIsInscrit(Boolean isInscrit) {
        this.isInscrit = isInscrit;
    }

    public LocalDate getDateInscription() {
        return this.dateInscription;
    }

    public Beneficiaire dateInscription(LocalDate dateInscription) {
        this.setDateInscription(dateInscription);
        return this;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDate getDateResiliation() {
        return this.dateResiliation;
    }

    public Beneficiaire dateResiliation(LocalDate dateResiliation) {
        this.setDateResiliation(dateResiliation);
        return this;
    }

    public void setDateResiliation(LocalDate dateResiliation) {
        this.dateResiliation = dateResiliation;
    }

    public Set<Enfant> getEnfants() {
        return this.enfants;
    }

    public void setEnfants(Set<Enfant> enfants) {
        if (this.enfants != null) {
            this.enfants.forEach(i -> i.setBeneficiaire(null));
        }
        if (enfants != null) {
            enfants.forEach(i -> i.setBeneficiaire(this));
        }
        this.enfants = enfants;
    }

    public Beneficiaire enfants(Set<Enfant> enfants) {
        this.setEnfants(enfants);
        return this;
    }

    public Beneficiaire addEnfant(Enfant enfant) {
        this.enfants.add(enfant);
        enfant.setBeneficiaire(this);
        return this;
    }

    public Beneficiaire removeEnfant(Enfant enfant) {
        this.enfants.remove(enfant);
        enfant.setBeneficiaire(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beneficiaire)) {
            return false;
        }
        return id != null && id.equals(((Beneficiaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beneficiaire{" +
            "id=" + getId() +
            ", externeId='" + getExterneId() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", dateDesactivation='" + getDateDesactivation() + "'" +
            ", isInscrit='" + getIsInscrit() + "'" +
            ", dateInscription='" + getDateInscription() + "'" +
            ", dateResiliation='" + getDateResiliation() + "'" +
            "}";
    }
}
