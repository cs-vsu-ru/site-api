package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentsDTO.class);
        StudentsDTO studentsDTO1 = new StudentsDTO();
        studentsDTO1.setId(1L);
        StudentsDTO studentsDTO2 = new StudentsDTO();
        assertThat(studentsDTO1).isNotEqualTo(studentsDTO2);
        studentsDTO2.setId(studentsDTO1.getId());
        assertThat(studentsDTO1).isEqualTo(studentsDTO2);
        studentsDTO2.setId(2L);
        assertThat(studentsDTO1).isNotEqualTo(studentsDTO2);
        studentsDTO1.setId(null);
        assertThat(studentsDTO1).isNotEqualTo(studentsDTO2);
    }
}
