package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccessModesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccessModesDTO.class);
        AccessModesDTO accessModesDTO1 = new AccessModesDTO();
        accessModesDTO1.setId(1L);
        AccessModesDTO accessModesDTO2 = new AccessModesDTO();
        assertThat(accessModesDTO1).isNotEqualTo(accessModesDTO2);
        accessModesDTO2.setId(accessModesDTO1.getId());
        assertThat(accessModesDTO1).isEqualTo(accessModesDTO2);
        accessModesDTO2.setId(2L);
        assertThat(accessModesDTO1).isNotEqualTo(accessModesDTO2);
        accessModesDTO1.setId(null);
        assertThat(accessModesDTO1).isNotEqualTo(accessModesDTO2);
    }
}
