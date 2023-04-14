package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagesDTO.class);
        PagesDTO pagesDTO1 = new PagesDTO();
        pagesDTO1.setId(1L);
        PagesDTO pagesDTO2 = new PagesDTO();
        assertThat(pagesDTO1).isNotEqualTo(pagesDTO2);
        pagesDTO2.setId(pagesDTO1.getId());
        assertThat(pagesDTO1).isEqualTo(pagesDTO2);
        pagesDTO2.setId(2L);
        assertThat(pagesDTO1).isNotEqualTo(pagesDTO2);
        pagesDTO1.setId(null);
        assertThat(pagesDTO1).isNotEqualTo(pagesDTO2);
    }
}
