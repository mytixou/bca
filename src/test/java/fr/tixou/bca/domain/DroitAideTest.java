package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroitAideTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitAide.class);
        DroitAide droitAide1 = new DroitAide();
        droitAide1.setId(1L);
        DroitAide droitAide2 = new DroitAide();
        droitAide2.setId(droitAide1.getId());
        assertThat(droitAide1).isEqualTo(droitAide2);
        droitAide2.setId(2L);
        assertThat(droitAide1).isNotEqualTo(droitAide2);
        droitAide1.setId(null);
        assertThat(droitAide1).isNotEqualTo(droitAide2);
    }
}
