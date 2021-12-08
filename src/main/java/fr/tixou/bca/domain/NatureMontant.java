package fr.tixou.bca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NatureMontant.
 */
@Entity
@Table(name = "nature_montant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NatureMontant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "natureMontants")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategieCi> strategieCis = new HashSet<>();

    @ManyToMany(mappedBy = "natureMontants")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategieApa> strategieApas = new HashSet<>();

    @ManyToMany(mappedBy = "natureMontants")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategiePch> strategiePches = new HashSet<>();

    @ManyToMany(mappedBy = "natureMontants")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategiePchE> strategiePchES = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NatureMontant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public NatureMontant code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public NatureMontant libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public NatureMontant description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StrategieCi> getStrategieCis() {
        return this.strategieCis;
    }

    public void setStrategieCis(Set<StrategieCi> strategieCis) {
        if (this.strategieCis != null) {
            this.strategieCis.forEach(i -> i.removeNatureMontant(this));
        }
        if (strategieCis != null) {
            strategieCis.forEach(i -> i.addNatureMontant(this));
        }
        this.strategieCis = strategieCis;
    }

    public NatureMontant strategieCis(Set<StrategieCi> strategieCis) {
        this.setStrategieCis(strategieCis);
        return this;
    }

    public NatureMontant addStrategieCi(StrategieCi strategieCi) {
        this.strategieCis.add(strategieCi);
        strategieCi.getNatureMontants().add(this);
        return this;
    }

    public NatureMontant removeStrategieCi(StrategieCi strategieCi) {
        this.strategieCis.remove(strategieCi);
        strategieCi.getNatureMontants().remove(this);
        return this;
    }

    public Set<StrategieApa> getStrategieApas() {
        return this.strategieApas;
    }

    public void setStrategieApas(Set<StrategieApa> strategieApas) {
        if (this.strategieApas != null) {
            this.strategieApas.forEach(i -> i.removeNatureMontant(this));
        }
        if (strategieApas != null) {
            strategieApas.forEach(i -> i.addNatureMontant(this));
        }
        this.strategieApas = strategieApas;
    }

    public NatureMontant strategieApas(Set<StrategieApa> strategieApas) {
        this.setStrategieApas(strategieApas);
        return this;
    }

    public NatureMontant addStrategieApa(StrategieApa strategieApa) {
        this.strategieApas.add(strategieApa);
        strategieApa.getNatureMontants().add(this);
        return this;
    }

    public NatureMontant removeStrategieApa(StrategieApa strategieApa) {
        this.strategieApas.remove(strategieApa);
        strategieApa.getNatureMontants().remove(this);
        return this;
    }

    public Set<StrategiePch> getStrategiePches() {
        return this.strategiePches;
    }

    public void setStrategiePches(Set<StrategiePch> strategiePches) {
        if (this.strategiePches != null) {
            this.strategiePches.forEach(i -> i.removeNatureMontant(this));
        }
        if (strategiePches != null) {
            strategiePches.forEach(i -> i.addNatureMontant(this));
        }
        this.strategiePches = strategiePches;
    }

    public NatureMontant strategiePches(Set<StrategiePch> strategiePches) {
        this.setStrategiePches(strategiePches);
        return this;
    }

    public NatureMontant addStrategiePch(StrategiePch strategiePch) {
        this.strategiePches.add(strategiePch);
        strategiePch.getNatureMontants().add(this);
        return this;
    }

    public NatureMontant removeStrategiePch(StrategiePch strategiePch) {
        this.strategiePches.remove(strategiePch);
        strategiePch.getNatureMontants().remove(this);
        return this;
    }

    public Set<StrategiePchE> getStrategiePchES() {
        return this.strategiePchES;
    }

    public void setStrategiePchES(Set<StrategiePchE> strategiePchES) {
        if (this.strategiePchES != null) {
            this.strategiePchES.forEach(i -> i.removeNatureMontant(this));
        }
        if (strategiePchES != null) {
            strategiePchES.forEach(i -> i.addNatureMontant(this));
        }
        this.strategiePchES = strategiePchES;
    }

    public NatureMontant strategiePchES(Set<StrategiePchE> strategiePchES) {
        this.setStrategiePchES(strategiePchES);
        return this;
    }

    public NatureMontant addStrategiePchE(StrategiePchE strategiePchE) {
        this.strategiePchES.add(strategiePchE);
        strategiePchE.getNatureMontants().add(this);
        return this;
    }

    public NatureMontant removeStrategiePchE(StrategiePchE strategiePchE) {
        this.strategiePchES.remove(strategiePchE);
        strategiePchE.getNatureMontants().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatureMontant)) {
            return false;
        }
        return id != null && id.equals(((NatureMontant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureMontant{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
