package ot.homework5plus.rushm.util;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Student;
import ot.homework5plus.rushm.integration.CourseForStudentGateway;

import java.util.Random;

@Service
@AllArgsConstructor
public class ScheduledTask {

    private final CourseForStudentGateway courseForStudentGateway;
    
    private static String[] students = { "Вася Попов", "Георгий Тримоноф", "Кирилл Суров" };

    @Scheduled(fixedRate = 5000)
    public void start() throws InterruptedException {
        Thread.sleep(4000);
        Thread thread = new Thread(() -> {
            Student student = new Student(students[new Random().nextInt(3)]);
            System.out.println("Проходит зачет для студента: " + student.getName());
            Student finishedStudent = courseForStudentGateway.process(student);
            System.out.println("Зачёт был окончен. Студент " + finishedStudent.getName() + " получил оценку: " + finishedStudent.getGrade() + " по теме: " + finishedStudent.getTopic());
        });
        thread.start();
    }
}