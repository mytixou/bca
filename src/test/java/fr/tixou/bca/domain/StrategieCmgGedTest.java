package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrategieCmgGedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrategieCmgGed.class);
        StrategieCmgGed strategieCmgGed1 = new StrategieCmgGed();
        strategieCmgGed1.setId(1L);
        StrategieCmgGed strategieCmgGed2 = new StrategieCmgGed();
        strategieCmgGed2.setId(strategieCmgGed1.getId());
        assertThat(strategieCmgGed1).isEqualTo(strategieCmgGed2);
        strategieCmgGed2.setId(2L);
        assertThat(strategieCmgGed1).isNotEqualTo(strategieCmgGed2);
        strategieCmgGed1.setId(null);
        assertThat(strategieCmgGed1).isNotEqualTo(strategieCmgGed2);
    }
}
