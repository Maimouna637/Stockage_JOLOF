package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipe implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipe")
    @JsonIgnoreProperties(value = { "equipe" }, allowSetters = true)
    private Set<MaterielSurLeTerrain> materielSurLeTerrains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Equipe nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Equipe prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Set<MaterielSurLeTerrain> getMaterielSurLeTerrains() {
        return this.materielSurLeTerrains;
    }

    public void setMaterielSurLeTerrains(Set<MaterielSurLeTerrain> materielSurLeTerrains) {
        if (this.materielSurLeTerrains != null) {
            this.materielSurLeTerrains.forEach(i -> i.setEquipe(null));
        }
        if (materielSurLeTerrains != null) {
            materielSurLeTerrains.forEach(i -> i.setEquipe(this));
        }
        this.materielSurLeTerrains = materielSurLeTerrains;
    }

    public Equipe materielSurLeTerrains(Set<MaterielSurLeTerrain> materielSurLeTerrains) {
        this.setMaterielSurLeTerrains(materielSurLeTerrains);
        return this;
    }

    public Equipe addMaterielSurLeTerrain(MaterielSurLeTerrain materielSurLeTerrain) {
        this.materielSurLeTerrains.add(materielSurLeTerrain);
        materielSurLeTerrain.setEquipe(this);
        return this;
    }

    public Equipe removeMaterielSurLeTerrain(MaterielSurLeTerrain materielSurLeTerrain) {
        this.materielSurLeTerrains.remove(materielSurLeTerrain);
        materielSurLeTerrain.setEquipe(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipe)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
