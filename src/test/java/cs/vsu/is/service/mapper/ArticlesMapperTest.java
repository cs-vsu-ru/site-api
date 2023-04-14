package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticlesMapperTest {

    private ArticlesMapper articlesMapper;

    @BeforeEach
    public void setUp() {
        articlesMapper = new ArticlesMapperImpl();
    }
}
