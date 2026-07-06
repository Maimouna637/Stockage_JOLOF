package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EquipeTestSamples.*;
import static com.mycompany.myapp.domain.MaterielSurLeTerrainTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipe.class);
        Equipe equipe1 = getEquipeSample1();
        Equipe equipe2 = new Equipe();
        assertThat(equipe1).isNotEqualTo(equipe2);

        equipe2.setId(equipe1.getId());
        assertThat(equipe1).isEqualTo(equipe2);

        equipe2 = getEquipeSample2();
        assertThat(equipe1).isNotEqualTo(equipe2);
    }

    @Test
    void materielSurLeTerrainTest() {
        Equipe equipe = getEquipeRandomSampleGenerator();
        MaterielSurLeTerrain materielSurLeTerrainBack = getMaterielSurLeTerrainRandomSampleGenerator();

        equipe.addMaterielSurLeTerrain(materielSurLeTerrainBack);
        assertThat(equipe.getMaterielSurLeTerrains()).containsOnly(materielSurLeTerrainBack);
        assertThat(materielSurLeTerrainBack.getEquipe()).isEqualTo(equipe);

        equipe.removeMaterielSurLeTerrain(materielSurLeTerrainBack);
        assertThat(equipe.getMaterielSurLeTerrains()).doesNotContain(materielSurLeTerrainBack);
        assertThat(materielSurLeTerrainBack.getEquipe()).isNull();

        equipe.materielSurLeTerrains(new HashSet<>(Set.of(materielSurLeTerrainBack)));
        assertThat(equipe.getMaterielSurLeTerrains()).containsOnly(materielSurLeTerrainBack);
        assertThat(materielSurLeTerrainBack.getEquipe()).isEqualTo(equipe);

        equipe.setMaterielSurLeTerrains(new HashSet<>());
        assertThat(equipe.getMaterielSurLeTerrains()).doesNotContain(materielSurLeTerrainBack);
        assertThat(materielSurLeTerrainBack.getEquipe()).isNull();
    }
}
