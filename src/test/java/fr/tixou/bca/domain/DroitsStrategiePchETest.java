package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroitsStrategiePchETest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitsStrategiePchE.class);
        DroitsStrategiePchE droitsStrategiePchE1 = new DroitsStrategiePchE();
        droitsStrategiePchE1.setId(1L);
        DroitsStrategiePchE droitsStrategiePchE2 = new DroitsStrategiePchE();
        droitsStrategiePchE2.setId(droitsStrategiePchE1.getId());
        assertThat(droitsStrategiePchE1).isEqualTo(droitsStrategiePchE2);
        droitsStrategiePchE2.setId(2L);
        assertThat(droitsStrategiePchE1).isNotEqualTo(droitsStrategiePchE2);
        droitsStrategiePchE1.setId(null);
        assertThat(droitsStrategiePchE1).isNotEqualTo(droitsStrategiePchE2);
    }
}
