package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrancheAideEnfantAssmatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrancheAideEnfantAssmat.class);
        TrancheAideEnfantAssmat trancheAideEnfantAssmat1 = new TrancheAideEnfantAssmat();
        trancheAideEnfantAssmat1.setId(1L);
        TrancheAideEnfantAssmat trancheAideEnfantAssmat2 = new TrancheAideEnfantAssmat();
        trancheAideEnfantAssmat2.setId(trancheAideEnfantAssmat1.getId());
        assertThat(trancheAideEnfantAssmat1).isEqualTo(trancheAideEnfantAssmat2);
        trancheAideEnfantAssmat2.setId(2L);
        assertThat(trancheAideEnfantAssmat1).isNotEqualTo(trancheAideEnfantAssmat2);
        trancheAideEnfantAssmat1.setId(null);
        assertThat(trancheAideEnfantAssmat1).isNotEqualTo(trancheAideEnfantAssmat2);
    }
}
