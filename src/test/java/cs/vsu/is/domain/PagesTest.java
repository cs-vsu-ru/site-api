package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pages.class);
        Pages pages1 = new Pages();
        pages1.setId(1L);
        Pages pages2 = new Pages();
        pages2.setId(pages1.getId());
        assertThat(pages1).isEqualTo(pages2);
        pages2.setId(2L);
        assertThat(pages1).isNotEqualTo(pages2);
        pages1.setId(null);
        assertThat(pages1).isNotEqualTo(pages2);
    }
}
