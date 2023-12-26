package com.example.departmentservice.controller;

import com.example.departmentservice.client.EmployeeClient;
import com.example.departmentservice.model.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentRepository repository;

    private static final Logger LOGGER=LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private EmployeeClient employeeClient;
    @PostMapping(value = "/post")
    public Department add(@RequestBody Department department){
        LOGGER.info("Department add: {}",department);
        return repository.addDepartment(department);
    }
    @GetMapping()
    public List<Department> findAll(){
        LOGGER.info("Department find");
        return repository.findAll();
    }
    @GetMapping(value = "/get/{id}")
    public Department findById(@PathVariable Long id){
        LOGGER.info("Department find:id= {}",id);
        return repository.findById(id);

    }
    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees(){
        LOGGER.info("department find");
        List<Department> departments=repository.findAll();

        departments.forEach(department -> department.setEmployees(employeeClient.findByDepartment(department.getId())));

        return departments;
    }

}
