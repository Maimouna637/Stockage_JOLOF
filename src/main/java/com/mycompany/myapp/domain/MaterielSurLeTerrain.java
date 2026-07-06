package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Models;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MaterielSurLeTerrain.
 */
@Entity
@Table(name = "materiel_sur_le_terrain")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaterielSurLeTerrain implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_affectattion")
    private LocalDate dateAffectattion;

    @Enumerated(EnumType.STRING)
    @Column(name = "materiel_1")
    private Models materiel1;

    @Column(name = "serie_1")
    private String serie1;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_1")
    private Etat etat1;

    @Column(name = "nombre_1")
    private Integer nombre1;

    @Enumerated(EnumType.STRING)
    @Column(name = "materiel_2")
    private Models materiel2;

    @Column(name = "serie_2")
    private String serie2;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_2")
    private Etat etat2;

    @Column(name = "nombre_2")
    private Integer nombre2;

    @Enumerated(EnumType.STRING)
    @Column(name = "materiel_3")
    private Models materiel3;

    @Column(name = "serie_3")
    private String serie3;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_3")
    private Etat etat3;

    @Column(name = "nombre_3")
    private Integer nombre3;

    @Enumerated(EnumType.STRING)
    @Column(name = "materiel_4")
    private Models materiel4;

    @Column(name = "serie_4")
    private String serie4;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_4")
    private Etat etat4;

    @Column(name = "nombre_4")
    private Integer nombre4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "materielSurLeTerrains" }, allowSetters = true)
    private Equipe equipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaterielSurLeTerrain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAffectattion() {
        return this.dateAffectattion;
    }

    public MaterielSurLeTerrain dateAffectattion(LocalDate dateAffectattion) {
        this.setDateAffectattion(dateAffectattion);
        return this;
    }

    public void setDateAffectattion(LocalDate dateAffectattion) {
        this.dateAffectattion = dateAffectattion;
    }

    public Models getMateriel1() {
        return this.materiel1;
    }

    public MaterielSurLeTerrain materiel1(Models materiel1) {
        this.setMateriel1(materiel1);
        return this;
    }

    public void setMateriel1(Models materiel1) {
        this.materiel1 = materiel1;
    }

    public String getSerie1() {
        return this.serie1;
    }

    public MaterielSurLeTerrain serie1(String serie1) {
        this.setSerie1(serie1);
        return this;
    }

    public void setSerie1(String serie1) {
        this.serie1 = serie1;
    }

    public Etat getEtat1() {
        return this.etat1;
    }

    public MaterielSurLeTerrain etat1(Etat etat1) {
        this.setEtat1(etat1);
        return this;
    }

    public void setEtat1(Etat etat1) {
        this.etat1 = etat1;
    }

    public Integer getNombre1() {
        return this.nombre1;
    }

    public MaterielSurLeTerrain nombre1(Integer nombre1) {
        this.setNombre1(nombre1);
        return this;
    }

    public void setNombre1(Integer nombre1) {
        this.nombre1 = nombre1;
    }

    public Models getMateriel2() {
        return this.materiel2;
    }

    public MaterielSurLeTerrain materiel2(Models materiel2) {
        this.setMateriel2(materiel2);
        return this;
    }

    public void setMateriel2(Models materiel2) {
        this.materiel2 = materiel2;
    }

    public String getSerie2() {
        return this.serie2;
    }

    public MaterielSurLeTerrain serie2(String serie2) {
        this.setSerie2(serie2);
        return this;
    }

    public void setSerie2(String serie2) {
        this.serie2 = serie2;
    }

    public Etat getEtat2() {
        return this.etat2;
    }

    public MaterielSurLeTerrain etat2(Etat etat2) {
        this.setEtat2(etat2);
        return this;
    }

    public void setEtat2(Etat etat2) {
        this.etat2 = etat2;
    }

    public Integer getNombre2() {
        return this.nombre2;
    }

    public MaterielSurLeTerrain nombre2(Integer nombre2) {
        this.setNombre2(nombre2);
        return this;
    }

    public void setNombre2(Integer nombre2) {
        this.nombre2 = nombre2;
    }

    public Models getMateriel3() {
        return this.materiel3;
    }

    public MaterielSurLeTerrain materiel3(Models materiel3) {
        this.setMateriel3(materiel3);
        return this;
    }

    public void setMateriel3(Models materiel3) {
        this.materiel3 = materiel3;
    }

    public String getSerie3() {
        return this.serie3;
    }

    public MaterielSurLeTerrain serie3(String serie3) {
        this.setSerie3(serie3);
        return this;
    }

    public void setSerie3(String serie3) {
        this.serie3 = serie3;
    }

    public Etat getEtat3() {
        return this.etat3;
    }

    public MaterielSurLeTerrain etat3(Etat etat3) {
        this.setEtat3(etat3);
        return this;
    }

    public void setEtat3(Etat etat3) {
        this.etat3 = etat3;
    }

    public Integer getNombre3() {
        return this.nombre3;
    }

    public MaterielSurLeTerrain nombre3(Integer nombre3) {
        this.setNombre3(nombre3);
        return this;
    }

    public void setNombre3(Integer nombre3) {
        this.nombre3 = nombre3;
    }

    public Models getMateriel4() {
        return this.materiel4;
    }

    public MaterielSurLeTerrain materiel4(Models materiel4) {
        this.setMateriel4(materiel4);
        return this;
    }

    public void setMateriel4(Models materiel4) {
        this.materiel4 = materiel4;
    }

    public String getSerie4() {
        return this.serie4;
    }

    public MaterielSurLeTerrain serie4(String serie4) {
        this.setSerie4(serie4);
        return this;
    }

    public void setSerie4(String serie4) {
        this.serie4 = serie4;
    }

    public Etat getEtat4() {
        return this.etat4;
    }

    public MaterielSurLeTerrain etat4(Etat etat4) {
        this.setEtat4(etat4);
        return this;
    }

    public void setEtat4(Etat etat4) {
        this.etat4 = etat4;
    }

    public Integer getNombre4() {
        return this.nombre4;
    }

    public MaterielSurLeTerrain nombre4(Integer nombre4) {
        this.setNombre4(nombre4);
        return this;
    }

    public void setNombre4(Integer nombre4) {
        this.nombre4 = nombre4;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public MaterielSurLeTerrain equipe(Equipe equipe) {
        this.setEquipe(equipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterielSurLeTerrain)) {
            return false;
        }
        return getId() != null && getId().equals(((MaterielSurLeTerrain) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterielSurLeTerrain{" +
            "id=" + getId() +
            ", dateAffectattion='" + getDateAffectattion() + "'" +
            ", materiel1='" + getMateriel1() + "'" +
            ", serie1='" + getSerie1() + "'" +
            ", etat1='" + getEtat1() + "'" +
            ", nombre1=" + getNombre1() +
            ", materiel2='" + getMateriel2() + "'" +
            ", serie2='" + getSerie2() + "'" +
            ", etat2='" + getEtat2() + "'" +
            ", nombre2=" + getNombre2() +
            ", materiel3='" + getMateriel3() + "'" +
            ", serie3='" + getSerie3() + "'" +
            ", etat3='" + getEtat3() + "'" +
            ", nombre3=" + getNombre3() +
            ", materiel4='" + getMateriel4() + "'" +
            ", serie4='" + getSerie4() + "'" +
            ", etat4='" + getEtat4() + "'" +
            ", nombre4=" + getNombre4() +
            "}";
    }
}
