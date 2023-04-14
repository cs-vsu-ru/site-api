package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScientificLeadershipsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScientificLeadershipsDTO.class);
        ScientificLeadershipsDTO scientificLeadershipsDTO1 = new ScientificLeadershipsDTO();
        scientificLeadershipsDTO1.setId(1L);
        ScientificLeadershipsDTO scientificLeadershipsDTO2 = new ScientificLeadershipsDTO();
        assertThat(scientificLeadershipsDTO1).isNotEqualTo(scientificLeadershipsDTO2);
        scientificLeadershipsDTO2.setId(scientificLeadershipsDTO1.getId());
        assertThat(scientificLeadershipsDTO1).isEqualTo(scientificLeadershipsDTO2);
        scientificLeadershipsDTO2.setId(2L);
        assertThat(scientificLeadershipsDTO1).isNotEqualTo(scientificLeadershipsDTO2);
        scientificLeadershipsDTO1.setId(null);
        assertThat(scientificLeadershipsDTO1).isNotEqualTo(scientificLeadershipsDTO2);
    }
}
