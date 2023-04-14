package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScientificWorkTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScientificWorkTypeDTO.class);
        ScientificWorkTypeDTO scientificWorkTypeDTO1 = new ScientificWorkTypeDTO();
        scientificWorkTypeDTO1.setId(1L);
        ScientificWorkTypeDTO scientificWorkTypeDTO2 = new ScientificWorkTypeDTO();
        assertThat(scientificWorkTypeDTO1).isNotEqualTo(scientificWorkTypeDTO2);
        scientificWorkTypeDTO2.setId(scientificWorkTypeDTO1.getId());
        assertThat(scientificWorkTypeDTO1).isEqualTo(scientificWorkTypeDTO2);
        scientificWorkTypeDTO2.setId(2L);
        assertThat(scientificWorkTypeDTO1).isNotEqualTo(scientificWorkTypeDTO2);
        scientificWorkTypeDTO1.setId(null);
        assertThat(scientificWorkTypeDTO1).isNotEqualTo(scientificWorkTypeDTO2);
    }
}
