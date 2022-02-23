package com.defers.taskmanager.entity;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
class TaskTest {

    @Test
    void shouldCreateClassWithAllArguments() {

        LocalDateTime date = new Timestamp(
                new GregorianCalendar(2022, 01, 15).getTime().getTime()).toLocalDateTime();
        Task task = new Task(1L, "some info", date, new User());

        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getDescription()).isEqualTo("some info");
        assertThat(task.getDate()).isEqualTo(date);

    }

    @Test
    void shouldCreateClassWithNoArguments() {

        Task task = new Task();

        assertThat(task).isNotNull();
        assertThat(task.getId()).isNull();
        assertThat(task.getDescription()).isNull();
        assertThat(task.getDate()).isNull();

    }


}