
package org.example.service;

import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    // In-memory employee storage
    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();

    // Automatically generates employee IDs
    private final AtomicLong idGenerator = new AtomicLong(0);

    // CREATE - Add employee
    public Employee addEmployee(Employee employee) {

        Long id = idGenerator.incrementAndGet();

        employee.setId(id);

        employees.put(id, employee);

        return employee;
    }

    // READ - Get all employees
    public List<Employee> getAllEmployees() {

        return new ArrayList<>(employees.values());
    }

    // READ - Get employee by ID
    public Employee getEmployeeById(Long id) {

        return employees.get(id);
    }

    // UPDATE - Update employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        if (!employees.containsKey(id)) {
            return null;
        }

        updatedEmployee.setId(id);

        employees.put(id, updatedEmployee);

        return updatedEmployee;
    }

    // DELETE - Delete employee
    public boolean deleteEmployee(Long id) {

        return employees.remove(id) != null;
    }
}
