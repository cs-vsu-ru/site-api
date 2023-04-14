package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpecialitiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialities.class);
        Specialities specialities1 = new Specialities();
        specialities1.setId(1L);
        Specialities specialities2 = new Specialities();
        specialities2.setId(specialities1.getId());
        assertThat(specialities1).isEqualTo(specialities2);
        specialities2.setId(2L);
        assertThat(specialities1).isNotEqualTo(specialities2);
        specialities1.setId(null);
        assertThat(specialities1).isNotEqualTo(specialities2);
    }
}
