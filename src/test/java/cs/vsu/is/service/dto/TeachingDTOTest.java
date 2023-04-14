package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeachingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingDTO.class);
        TeachingDTO teachingDTO1 = new TeachingDTO();
        teachingDTO1.setId(1L);
        TeachingDTO teachingDTO2 = new TeachingDTO();
        assertThat(teachingDTO1).isNotEqualTo(teachingDTO2);
        teachingDTO2.setId(teachingDTO1.getId());
        assertThat(teachingDTO1).isEqualTo(teachingDTO2);
        teachingDTO2.setId(2L);
        assertThat(teachingDTO1).isNotEqualTo(teachingDTO2);
        teachingDTO1.setId(null);
        assertThat(teachingDTO1).isNotEqualTo(teachingDTO2);
    }
}
