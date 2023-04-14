package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpecialitiesMapperTest {

    private SpecialitiesMapper specialitiesMapper;

    @BeforeEach
    public void setUp() {
        specialitiesMapper = new SpecialitiesMapperImpl();
    }
}
