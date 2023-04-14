package cs.vsu.is.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticlesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlesDTO.class);
        ArticlesDTO articlesDTO1 = new ArticlesDTO();
        articlesDTO1.setId(1L);
        ArticlesDTO articlesDTO2 = new ArticlesDTO();
        assertThat(articlesDTO1).isNotEqualTo(articlesDTO2);
        articlesDTO2.setId(articlesDTO1.getId());
        assertThat(articlesDTO1).isEqualTo(articlesDTO2);
        articlesDTO2.setId(2L);
        assertThat(articlesDTO1).isNotEqualTo(articlesDTO2);
        articlesDTO1.setId(null);
        assertThat(articlesDTO1).isNotEqualTo(articlesDTO2);
    }
}
