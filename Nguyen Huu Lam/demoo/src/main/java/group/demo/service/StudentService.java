package group.demo.service;

import group.demo.entity.Student;
import group.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    //Phải khai báo mới xài đc Student Service
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll(){
        List<Student> students= studentRepository.findByStatus(0);
        return students;
    }

    public void createStudent(String name, double age, String student_code){
        Student studentEntity= new Student();
        studentEntity.setName(name);
        studentEntity.setAge(age);
        studentEntity.setStudent_code(student_code);
        studentEntity.setStatus(0);
        studentRepository.save(studentEntity);
    }

    public void updateStudent(Integer editStudentId, String name, double age, String student_code){
        Student studentEntity= studentRepository.findById(editStudentId).get();
        studentEntity.setName(name);
        studentEntity.setAge(age);
        studentEntity.setStudent_code(student_code);

        studentRepository.save(studentEntity);
    }
    public void hideStudent(Integer studentId){
        Student studentEntity= studentRepository.findById(studentId).get();
        studentEntity.setStatus(1);
        studentRepository.save(studentEntity);
    }
}
