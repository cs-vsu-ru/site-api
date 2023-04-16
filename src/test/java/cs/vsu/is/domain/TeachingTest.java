package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeachingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teaching.class);
        Teaching teaching1 = new Teaching();
        teaching1.setId(1L);
        Teaching teaching2 = new Teaching();
        teaching2.setId(teaching1.getId());
        assertThat(teaching1).isEqualTo(teaching2);
        teaching2.setId(2L);
        assertThat(teaching1).isNotEqualTo(teaching2);
        teaching1.setId(null);
        assertThat(teaching1).isNotEqualTo(teaching2);
    }
}
