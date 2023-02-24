package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.DTOs.StudentMobileUpdateRequestDto;
import com.example.Student_Library_Management_System.Models.Student;
import com.example.Student_Library_Management_System.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("/add")//  '/' is optional in endpoint
    public String createStudent(@RequestBody Student student) {

        return studentService.createStudent(student);
    }

    @GetMapping("/getById")
    public Student getByID(@RequestParam("id") Integer id) {
        return studentService.getById(id);
    }

    @GetMapping("/getByEmail")
    public Student getById(@RequestParam("email") String email) {
        return studentService.getByEmail(email);
    }

    @PutMapping("/updateMob")
    public String updateMob(@RequestBody StudentMobileUpdateRequestDto studentMobileUpdateRequestDto) {
        return studentService.updateMob(studentMobileUpdateRequestDto);
    }
}
