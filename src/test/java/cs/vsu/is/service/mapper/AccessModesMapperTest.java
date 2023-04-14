package cs.vsu.is.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccessModesMapperTest {

    private AccessModesMapper accessModesMapper;

    @BeforeEach
    public void setUp() {
        accessModesMapper = new AccessModesMapperImpl();
    }
}
