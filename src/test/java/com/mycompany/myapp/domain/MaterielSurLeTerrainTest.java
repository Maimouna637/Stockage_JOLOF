package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EquipeTestSamples.*;
import static com.mycompany.myapp.domain.MaterielSurLeTerrainTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaterielSurLeTerrainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterielSurLeTerrain.class);
        MaterielSurLeTerrain materielSurLeTerrain1 = getMaterielSurLeTerrainSample1();
        MaterielSurLeTerrain materielSurLeTerrain2 = new MaterielSurLeTerrain();
        assertThat(materielSurLeTerrain1).isNotEqualTo(materielSurLeTerrain2);

        materielSurLeTerrain2.setId(materielSurLeTerrain1.getId());
        assertThat(materielSurLeTerrain1).isEqualTo(materielSurLeTerrain2);

        materielSurLeTerrain2 = getMaterielSurLeTerrainSample2();
        assertThat(materielSurLeTerrain1).isNotEqualTo(materielSurLeTerrain2);
    }

    @Test
    void equipeTest() {
        MaterielSurLeTerrain materielSurLeTerrain = getMaterielSurLeTerrainRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        materielSurLeTerrain.setEquipe(equipeBack);
        assertThat(materielSurLeTerrain.getEquipe()).isEqualTo(equipeBack);

        materielSurLeTerrain.equipe(null);
        assertThat(materielSurLeTerrain.getEquipe()).isNull();
    }
}
