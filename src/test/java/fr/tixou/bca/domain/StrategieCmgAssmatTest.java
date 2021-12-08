package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrategieCmgAssmatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrategieCmgAssmat.class);
        StrategieCmgAssmat strategieCmgAssmat1 = new StrategieCmgAssmat();
        strategieCmgAssmat1.setId(1L);
        StrategieCmgAssmat strategieCmgAssmat2 = new StrategieCmgAssmat();
        strategieCmgAssmat2.setId(strategieCmgAssmat1.getId());
        assertThat(strategieCmgAssmat1).isEqualTo(strategieCmgAssmat2);
        strategieCmgAssmat2.setId(2L);
        assertThat(strategieCmgAssmat1).isNotEqualTo(strategieCmgAssmat2);
        strategieCmgAssmat1.setId(null);
        assertThat(strategieCmgAssmat1).isNotEqualTo(strategieCmgAssmat2);
    }
}
