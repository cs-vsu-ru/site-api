package cs.vsu.is.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cs.vsu.is.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EduSchedulePlaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EduSchedulePlace.class);
        EduSchedulePlace eduSchedulePlace1 = new EduSchedulePlace();
        eduSchedulePlace1.setId(1L);
        EduSchedulePlace eduSchedulePlace2 = new EduSchedulePlace();
        eduSchedulePlace2.setId(eduSchedulePlace1.getId());
        assertThat(eduSchedulePlace1).isEqualTo(eduSchedulePlace2);
        eduSchedulePlace2.setId(2L);
        assertThat(eduSchedulePlace1).isNotEqualTo(eduSchedulePlace2);
        eduSchedulePlace1.setId(null);
        assertThat(eduSchedulePlace1).isNotEqualTo(eduSchedulePlace2);
    }
}
