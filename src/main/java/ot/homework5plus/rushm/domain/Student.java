package ot.homework5plus.rushm.domain;

import lombok.Data;

@Data
public class Student {

    String name;

    Integer grade;

    String topic;

    public Student(String name) {
        this.name = name;
    }
}