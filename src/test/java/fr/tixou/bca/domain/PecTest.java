package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PecTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pec.class);
        Pec pec1 = new Pec();
        pec1.setId(UUID.randomUUID());
        Pec pec2 = new Pec();
        pec2.setId(pec1.getId());
        assertThat(pec1).isEqualTo(pec2);
        pec2.setId(UUID.randomUUID());
        assertThat(pec1).isNotEqualTo(pec2);
        pec1.setId(null);
        assertThat(pec1).isNotEqualTo(pec2);
    }
}
