package ot.homework5plus.rushm.domain;

import lombok.Data;

@Data
public class Course {

    String name;

    public Course(String name) {
        this.name = name;
    }
}