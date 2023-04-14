package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScientificWorkTypeMapperTest {

    private ScientificWorkTypeMapper scientificWorkTypeMapper;

    @BeforeEach
    public void setUp() {
        scientificWorkTypeMapper = new ScientificWorkTypeMapperImpl();
    }
}
