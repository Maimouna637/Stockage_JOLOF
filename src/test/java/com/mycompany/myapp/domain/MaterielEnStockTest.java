package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.MaterielEnStockTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaterielEnStockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterielEnStock.class);
        MaterielEnStock materielEnStock1 = getMaterielEnStockSample1();
        MaterielEnStock materielEnStock2 = new MaterielEnStock();
        assertThat(materielEnStock1).isNotEqualTo(materielEnStock2);

        materielEnStock2.setId(materielEnStock1.getId());
        assertThat(materielEnStock1).isEqualTo(materielEnStock2);

        materielEnStock2 = getMaterielEnStockSample2();
        assertThat(materielEnStock1).isNotEqualTo(materielEnStock2);
    }
}
