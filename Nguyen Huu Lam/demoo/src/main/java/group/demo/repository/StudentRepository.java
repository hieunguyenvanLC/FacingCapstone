package group.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import group.demo.entity.Student;

import java.util.List;

//Class phải public mới import đc

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAll();

    List<Student> findByStatus(Integer status);
}
