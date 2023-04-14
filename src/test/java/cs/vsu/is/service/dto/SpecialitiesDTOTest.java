package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpecialitiesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialitiesDTO.class);
        SpecialitiesDTO specialitiesDTO1 = new SpecialitiesDTO();
        specialitiesDTO1.setId(1L);
        SpecialitiesDTO specialitiesDTO2 = new SpecialitiesDTO();
        assertThat(specialitiesDTO1).isNotEqualTo(specialitiesDTO2);
        specialitiesDTO2.setId(specialitiesDTO1.getId());
        assertThat(specialitiesDTO1).isEqualTo(specialitiesDTO2);
        specialitiesDTO2.setId(2L);
        assertThat(specialitiesDTO1).isNotEqualTo(specialitiesDTO2);
        specialitiesDTO1.setId(null);
        assertThat(specialitiesDTO1).isNotEqualTo(specialitiesDTO2);
    }
}
