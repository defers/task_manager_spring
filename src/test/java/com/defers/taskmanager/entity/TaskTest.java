package com.defers.taskmanager.entity;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
class TaskTest {

    @Test
    void shouldCreateClassWithAllArguments() {

        Date date = new GregorianCalendar(2022, 01, 15).getTime();
        Task task = new Task(1L, "some info", date);

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