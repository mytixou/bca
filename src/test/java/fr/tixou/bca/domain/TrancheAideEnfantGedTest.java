package fr.tixou.bca.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tixou.bca.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrancheAideEnfantGedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrancheAideEnfantGed.class);
        TrancheAideEnfantGed trancheAideEnfantGed1 = new TrancheAideEnfantGed();
        trancheAideEnfantGed1.setId(1L);
        TrancheAideEnfantGed trancheAideEnfantGed2 = new TrancheAideEnfantGed();
        trancheAideEnfantGed2.setId(trancheAideEnfantGed1.getId());
        assertThat(trancheAideEnfantGed1).isEqualTo(trancheAideEnfantGed2);
        trancheAideEnfantGed2.setId(2L);
        assertThat(trancheAideEnfantGed1).isNotEqualTo(trancheAideEnfantGed2);
        trancheAideEnfantGed1.setId(null);
        assertThat(trancheAideEnfantGed1).isNotEqualTo(trancheAideEnfantGed2);
    }
}
