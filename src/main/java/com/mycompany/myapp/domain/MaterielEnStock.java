package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Models;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MaterielEnStock.
 */
@Entity
@Table(name = "materiel_en_stock")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaterielEnStock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_reception")
    private LocalDate dateReception;

    @Column(name = "quantite")
    private Integer quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "materiel")
    private Models materiel;

    @Column(name = "noserie")
    private String noserie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaterielEnStock id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateReception() {
        return this.dateReception;
    }

    public MaterielEnStock dateReception(LocalDate dateReception) {
        this.setDateReception(dateReception);
        return this;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public MaterielEnStock quantite(Integer quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Models getMateriel() {
        return this.materiel;
    }

    public MaterielEnStock materiel(Models materiel) {
        this.setMateriel(materiel);
        return this;
    }

    public void setMateriel(Models materiel) {
        this.materiel = materiel;
    }

    public String getNoserie() {
        return this.noserie;
    }

    public MaterielEnStock noserie(String noserie) {
        this.setNoserie(noserie);
        return this;
    }

    public void setNoserie(String noserie) {
        this.noserie = noserie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterielEnStock)) {
            return false;
        }
        return getId() != null && getId().equals(((MaterielEnStock) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterielEnStock{" +
            "id=" + getId() +
            ", dateReception='" + getDateReception() + "'" +
            ", quantite=" + getQuantite() +
            ", materiel='" + getMateriel() + "'" +
            ", noserie='" + getNoserie() + "'" +
            "}";
    }
}
