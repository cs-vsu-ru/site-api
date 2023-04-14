package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PagesMapperTest {

    private PagesMapper pagesMapper;

    @BeforeEach
    public void setUp() {
        pagesMapper = new PagesMapperImpl();
    }
}
