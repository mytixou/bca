package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroitsStrategiePchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitsStrategiePch.class);
        DroitsStrategiePch droitsStrategiePch1 = new DroitsStrategiePch();
        droitsStrategiePch1.setId(1L);
        DroitsStrategiePch droitsStrategiePch2 = new DroitsStrategiePch();
        droitsStrategiePch2.setId(droitsStrategiePch1.getId());
        assertThat(droitsStrategiePch1).isEqualTo(droitsStrategiePch2);
        droitsStrategiePch2.setId(2L);
        assertThat(droitsStrategiePch1).isNotEqualTo(droitsStrategiePch2);
        droitsStrategiePch1.setId(null);
        assertThat(droitsStrategiePch1).isNotEqualTo(droitsStrategiePch2);
    }
}
