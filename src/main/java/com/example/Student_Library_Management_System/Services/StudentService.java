package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.StudentMobileUpdateRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Student;
import com.example.Student_Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public String createStudent(Student student) {
        //Basic attributes of the student will be set through object passed by Postman

        //Library Card should be auto-generated when creating student
        Card card = new Card();
        card.setCardStatus(CardStatus.ACTIVE);
        card.setStudentVariableName(student);//this is foreign-key in card table
        //Setting(assigning) card for student entity
        student.setCard(card);

        //If there was unidirectional mapping: then we would have to save both entities separately(explicitly).
        //But here we have done bidirectional mapping so, child entity will automatically be saved.
        studentRepository.save(student);
        //By cascading effect child entity is being saved automatically.
        return "Student and Card added successfully";
    }

    public Student getById(Integer id) {
        return studentRepository.findById(id).get();
    }

    public Student getByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public String updateMob(StudentMobileUpdateRequestDto studentMobileUpdateRequestDto) {
        Student student = studentRepository.findById(studentMobileUpdateRequestDto.getId()).get();
        student.setMobileNo(studentMobileUpdateRequestDto.getMobileNo());
        //save
        studentRepository.save(student);
        return student.getName() +", your mobile no. is updated.";
    }
}
