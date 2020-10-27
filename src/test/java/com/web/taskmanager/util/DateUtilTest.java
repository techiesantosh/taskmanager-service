package com.web.taskmanager.util;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@RunWith(JUnitPlatform.class)
class DateUtilTest {

    @Test
    public  void testFormatDate(){

        assertThat(DateUtil
                .formatDate(LocalDate.of(2020,12,12)))
                .isEqualTo("2020-12-12");
    }

}