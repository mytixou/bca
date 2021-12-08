package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroitsStrategieCiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitsStrategieCi.class);
        DroitsStrategieCi droitsStrategieCi1 = new DroitsStrategieCi();
        droitsStrategieCi1.setId(1L);
        DroitsStrategieCi droitsStrategieCi2 = new DroitsStrategieCi();
        droitsStrategieCi2.setId(droitsStrategieCi1.getId());
        assertThat(droitsStrategieCi1).isEqualTo(droitsStrategieCi2);
        droitsStrategieCi2.setId(2L);
        assertThat(droitsStrategieCi1).isNotEqualTo(droitsStrategieCi2);
        droitsStrategieCi1.setId(null);
        assertThat(droitsStrategieCi1).isNotEqualTo(droitsStrategieCi2);
    }
}
