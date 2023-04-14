package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EduSchedulePlaceMapperTest {

    private EduSchedulePlaceMapper eduSchedulePlaceMapper;

    @BeforeEach
    public void setUp() {
        eduSchedulePlaceMapper = new EduSchedulePlaceMapperImpl();
    }
}
