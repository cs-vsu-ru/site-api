package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScientificWorkTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScientificWorkType.class);
        ScientificWorkType scientificWorkType1 = new ScientificWorkType();
        scientificWorkType1.setId(1L);
        ScientificWorkType scientificWorkType2 = new ScientificWorkType();
        scientificWorkType2.setId(scientificWorkType1.getId());
        assertThat(scientificWorkType1).isEqualTo(scientificWorkType2);
        scientificWorkType2.setId(2L);
        assertThat(scientificWorkType1).isNotEqualTo(scientificWorkType2);
        scientificWorkType1.setId(null);
        assertThat(scientificWorkType1).isNotEqualTo(scientificWorkType2);
    }
}
