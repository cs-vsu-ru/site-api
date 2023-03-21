package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticlesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Articles.class);
        Articles articles1 = new Articles();
        articles1.setId(1L);
        Articles articles2 = new Articles();
        articles2.setId(articles1.getId());
        assertThat(articles1).isEqualTo(articles2);
        articles2.setId(2L);
        assertThat(articles1).isNotEqualTo(articles2);
        articles1.setId(null);
        assertThat(articles1).isNotEqualTo(articles2);
    }
}
