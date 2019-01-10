package group.demo.service;

import group.demo.entity.Employee;
import group.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findByStatus(0);
        return employees;
    }

    public void createEmployee(String name, String phone, String password) {
        Employee employeeEntity=new Employee();
        employeeEntity.setName(name);
        employeeEntity.setPhone(phone);
        employeeEntity.setPassword(password);
        employeeEntity.setStatus(0);
        employeeRepository.save(employeeEntity);
    }

    public void updateEmployee(Integer editEmployeeId,String name, String phone, String password) {
        Employee employeeEntity= employeeRepository.findById(editEmployeeId).get();
        employeeEntity.setName(name);
        employeeEntity.setPassword(password);
        employeeRepository.save(employeeEntity);
    }

    public void hideEmployee(Integer employeeId) {
        Employee employeeEntity= employeeRepository.findById(employeeId).get();
        employeeEntity.setStatus(1);
        employeeRepository.save(employeeEntity);
    }
}
