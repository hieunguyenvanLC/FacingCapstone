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

    public void createEmployee(String name, String phone) {
        Employee employeeEntity=new Employee();
        employeeEntity.setName(name);
        employeeEntity.setPhone(phone);
        employeeEntity.setStatus(0);
        employeeRepository.save(employeeEntity);
    }
}
