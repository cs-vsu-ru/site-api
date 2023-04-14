package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScientificLeadershipsMapperTest {

    private ScientificLeadershipsMapper scientificLeadershipsMapper;

    @BeforeEach
    public void setUp() {
        scientificLeadershipsMapper = new ScientificLeadershipsMapperImpl();
    }
}
