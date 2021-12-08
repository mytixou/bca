package fr.tixou.bca.domain;

import fr.tixou.bca.domain.enumeration.TypeProduit;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nom", nullable = false, unique = true)
    private TypeProduit nom;

    @NotNull
    @Column(name = "is_actif", nullable = false)
    private Boolean isActif;

    @NotNull
    @Column(name = "date_lancement", nullable = false)
    private LocalDate dateLancement;

    @NotNull
    @Column(name = "anne_lancement", nullable = false)
    private Integer anneLancement;

    @NotNull
    @Column(name = "mois_lancement", nullable = false)
    private Integer moisLancement;

    @Column(name = "date_resiliation")
    private LocalDate dateResiliation;

    @Column(name = "derniere_annee")
    private Integer derniereAnnee;

    @Column(name = "dernier_mois")
    private Integer dernierMois;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeProduit getNom() {
        return this.nom;
    }

    public Produit nom(TypeProduit nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(TypeProduit nom) {
        this.nom = nom;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Produit isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateLancement() {
        return this.dateLancement;
    }

    public Produit dateLancement(LocalDate dateLancement) {
        this.setDateLancement(dateLancement);
        return this;
    }

    public void setDateLancement(LocalDate dateLancement) {
        this.dateLancement = dateLancement;
    }

    public Integer getAnneLancement() {
        return this.anneLancement;
    }

    public Produit anneLancement(Integer anneLancement) {
        this.setAnneLancement(anneLancement);
        return this;
    }

    public void setAnneLancement(Integer anneLancement) {
        this.anneLancement = anneLancement;
    }

    public Integer getMoisLancement() {
        return this.moisLancement;
    }

    public Produit moisLancement(Integer moisLancement) {
        this.setMoisLancement(moisLancement);
        return this;
    }

    public void setMoisLancement(Integer moisLancement) {
        this.moisLancement = moisLancement;
    }

    public LocalDate getDateResiliation() {
        return this.dateResiliation;
    }

    public Produit dateResiliation(LocalDate dateResiliation) {
        this.setDateResiliation(dateResiliation);
        return this;
    }

    public void setDateResiliation(LocalDate dateResiliation) {
        this.dateResiliation = dateResiliation;
    }

    public Integer getDerniereAnnee() {
        return this.derniereAnnee;
    }

    public Produit derniereAnnee(Integer derniereAnnee) {
        this.setDerniereAnnee(derniereAnnee);
        return this;
    }

    public void setDerniereAnnee(Integer derniereAnnee) {
        this.derniereAnnee = derniereAnnee;
    }

    public Integer getDernierMois() {
        return this.dernierMois;
    }

    public Produit dernierMois(Integer dernierMois) {
        this.setDernierMois(dernierMois);
        return this;
    }

    public void setDernierMois(Integer dernierMois) {
        this.dernierMois = dernierMois;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", dateLancement='" + getDateLancement() + "'" +
            ", anneLancement=" + getAnneLancement() +
            ", moisLancement=" + getMoisLancement() +
            ", dateResiliation='" + getDateResiliation() + "'" +
            ", derniereAnnee=" + getDerniereAnnee() +
            ", dernierMois=" + getDernierMois() +
            "}";
    }
}
