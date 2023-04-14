package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeachingMapperTest {

    private TeachingMapper teachingMapper;

    @BeforeEach
    public void setUp() {
        teachingMapper = new TeachingMapperImpl();
    }
}
