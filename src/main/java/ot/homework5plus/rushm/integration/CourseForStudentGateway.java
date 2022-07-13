package ot.homework5plus.rushm.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ot.homework5plus.rushm.domain.Student;

@MessagingGateway
public interface CourseForStudentGateway {

    @Gateway(requestChannel = "studentChannel", replyChannel = "courseChannel")
    Student process(Student student);
}