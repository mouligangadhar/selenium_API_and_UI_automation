
package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeePageController {

    // Open the employee management homepage
    @GetMapping("/")
    public String showHomePage() {
        return "employees";
    }

    // Open employee management page directly
    @GetMapping("/employees")
    public String showEmployeePage() {
        return "employees";
    }
}
