package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccessModesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccessModes.class);
        AccessModes accessModes1 = new AccessModes();
        accessModes1.setId(1L);
        AccessModes accessModes2 = new AccessModes();
        accessModes2.setId(accessModes1.getId());
        assertThat(accessModes1).isEqualTo(accessModes2);
        accessModes2.setId(2L);
        assertThat(accessModes1).isNotEqualTo(accessModes2);
        accessModes1.setId(null);
        assertThat(accessModes1).isNotEqualTo(accessModes2);
    }
}
