package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EduSchedulePlaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EduSchedulePlaceDTO.class);
        EduSchedulePlaceDTO eduSchedulePlaceDTO1 = new EduSchedulePlaceDTO();
        eduSchedulePlaceDTO1.setId(1L);
        EduSchedulePlaceDTO eduSchedulePlaceDTO2 = new EduSchedulePlaceDTO();
        assertThat(eduSchedulePlaceDTO1).isNotEqualTo(eduSchedulePlaceDTO2);
        eduSchedulePlaceDTO2.setId(eduSchedulePlaceDTO1.getId());
        assertThat(eduSchedulePlaceDTO1).isEqualTo(eduSchedulePlaceDTO2);
        eduSchedulePlaceDTO2.setId(2L);
        assertThat(eduSchedulePlaceDTO1).isNotEqualTo(eduSchedulePlaceDTO2);
        eduSchedulePlaceDTO1.setId(null);
        assertThat(eduSchedulePlaceDTO1).isNotEqualTo(eduSchedulePlaceDTO2);
    }
}
