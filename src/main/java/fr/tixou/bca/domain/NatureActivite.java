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
 * A NatureActivite.
 */
@Entity
@Table(name = "nature_activite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NatureActivite implements Serializable {

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

    @ManyToMany(mappedBy = "natureActivites")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategieCi> strategieCis = new HashSet<>();

    @ManyToMany(mappedBy = "natureActivites")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategieApa> strategieApas = new HashSet<>();

    @ManyToMany(mappedBy = "natureActivites")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategiePch> strategiePches = new HashSet<>();

    @ManyToMany(mappedBy = "natureActivites")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aide", "tiersFinanceur", "natureActivites", "natureMontants" }, allowSetters = true)
    private Set<StrategiePchE> strategiePchES = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NatureActivite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public NatureActivite code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public NatureActivite libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public NatureActivite description(String description) {
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
            this.strategieCis.forEach(i -> i.removeNatureActivite(this));
        }
        if (strategieCis != null) {
            strategieCis.forEach(i -> i.addNatureActivite(this));
        }
        this.strategieCis = strategieCis;
    }

    public NatureActivite strategieCis(Set<StrategieCi> strategieCis) {
        this.setStrategieCis(strategieCis);
        return this;
    }

    public NatureActivite addStrategieCi(StrategieCi strategieCi) {
        this.strategieCis.add(strategieCi);
        strategieCi.getNatureActivites().add(this);
        return this;
    }

    public NatureActivite removeStrategieCi(StrategieCi strategieCi) {
        this.strategieCis.remove(strategieCi);
        strategieCi.getNatureActivites().remove(this);
        return this;
    }

    public Set<StrategieApa> getStrategieApas() {
        return this.strategieApas;
    }

    public void setStrategieApas(Set<StrategieApa> strategieApas) {
        if (this.strategieApas != null) {
            this.strategieApas.forEach(i -> i.removeNatureActivite(this));
        }
        if (strategieApas != null) {
            strategieApas.forEach(i -> i.addNatureActivite(this));
        }
        this.strategieApas = strategieApas;
    }

    public NatureActivite strategieApas(Set<StrategieApa> strategieApas) {
        this.setStrategieApas(strategieApas);
        return this;
    }

    public NatureActivite addStrategieApa(StrategieApa strategieApa) {
        this.strategieApas.add(strategieApa);
        strategieApa.getNatureActivites().add(this);
        return this;
    }

    public NatureActivite removeStrategieApa(StrategieApa strategieApa) {
        this.strategieApas.remove(strategieApa);
        strategieApa.getNatureActivites().remove(this);
        return this;
    }

    public Set<StrategiePch> getStrategiePches() {
        return this.strategiePches;
    }

    public void setStrategiePches(Set<StrategiePch> strategiePches) {
        if (this.strategiePches != null) {
            this.strategiePches.forEach(i -> i.removeNatureActivite(this));
        }
        if (strategiePches != null) {
            strategiePches.forEach(i -> i.addNatureActivite(this));
        }
        this.strategiePches = strategiePches;
    }

    public NatureActivite strategiePches(Set<StrategiePch> strategiePches) {
        this.setStrategiePches(strategiePches);
        return this;
    }

    public NatureActivite addStrategiePch(StrategiePch strategiePch) {
        this.strategiePches.add(strategiePch);
        strategiePch.getNatureActivites().add(this);
        return this;
    }

    public NatureActivite removeStrategiePch(StrategiePch strategiePch) {
        this.strategiePches.remove(strategiePch);
        strategiePch.getNatureActivites().remove(this);
        return this;
    }

    public Set<StrategiePchE> getStrategiePchES() {
        return this.strategiePchES;
    }

    public void setStrategiePchES(Set<StrategiePchE> strategiePchES) {
        if (this.strategiePchES != null) {
            this.strategiePchES.forEach(i -> i.removeNatureActivite(this));
        }
        if (strategiePchES != null) {
            strategiePchES.forEach(i -> i.addNatureActivite(this));
        }
        this.strategiePchES = strategiePchES;
    }

    public NatureActivite strategiePchES(Set<StrategiePchE> strategiePchES) {
        this.setStrategiePchES(strategiePchES);
        return this;
    }

    public NatureActivite addStrategiePchE(StrategiePchE strategiePchE) {
        this.strategiePchES.add(strategiePchE);
        strategiePchE.getNatureActivites().add(this);
        return this;
    }

    public NatureActivite removeStrategiePchE(StrategiePchE strategiePchE) {
        this.strategiePchES.remove(strategiePchE);
        strategiePchE.getNatureActivites().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatureActivite)) {
            return false;
        }
        return id != null && id.equals(((NatureActivite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureActivite{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
