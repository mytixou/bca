package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroitsStrategieApaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitsStrategieApa.class);
        DroitsStrategieApa droitsStrategieApa1 = new DroitsStrategieApa();
        droitsStrategieApa1.setId(1L);
        DroitsStrategieApa droitsStrategieApa2 = new DroitsStrategieApa();
        droitsStrategieApa2.setId(droitsStrategieApa1.getId());
        assertThat(droitsStrategieApa1).isEqualTo(droitsStrategieApa2);
        droitsStrategieApa2.setId(2L);
        assertThat(droitsStrategieApa1).isNotEqualTo(droitsStrategieApa2);
        droitsStrategieApa1.setId(null);
        assertThat(droitsStrategieApa1).isNotEqualTo(droitsStrategieApa2);
    }
}
