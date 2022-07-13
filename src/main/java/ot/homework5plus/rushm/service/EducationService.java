package ot.homework5plus.rushm.service;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Student;

import java.util.Random;

@Service
public class EducationService {

    private static String[] teachers = { "Эдгар Васильевич", "Олег Триньянов", "Василий Леонович" };
    private static String[] topic = { "java spring", "sql", "java basic" };

    public Student gradeStudent(Student student) throws InterruptedException {
        String teacher = teachers[new Random().nextInt(3)];
        System.out.println("Преподаватель " + teacher + " думает о выставлении оценки по теме студенту: " + student.getName());
        Thread.sleep(5000);
        student.setTopic(topic[new Random().nextInt(3)]);
        student.setGrade(new Random().ints(3, 5).findFirst().getAsInt());
        System.out.println("Преподаватель " + teacher + " сделал свои выводы и поставил оценку студенту: " + student.getName());
        return student;
    }
}