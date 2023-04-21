package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScientificLeadeshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScientificLeadeship.class);
        ScientificLeadeship scientificLeadeship1 = new ScientificLeadeship();
        scientificLeadeship1.setId(1L);
        ScientificLeadeship scientificLeadeship2 = new ScientificLeadeship();
        scientificLeadeship2.setId(scientificLeadeship1.getId());
        assertThat(scientificLeadeship1).isEqualTo(scientificLeadeship2);
        scientificLeadeship2.setId(2L);
        assertThat(scientificLeadeship1).isNotEqualTo(scientificLeadeship2);
        scientificLeadeship1.setId(null);
        assertThat(scientificLeadeship1).isNotEqualTo(scientificLeadeship2);
    }
}
