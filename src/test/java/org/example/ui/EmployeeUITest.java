
package org.example.ui;

import org.example.base.BaseUITest;
import org.example.pages.EmployeePage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeUITest extends BaseUITest {

    @Test
    public void verifyAddEmployeeFunctionality() {

        // Create Page Object
        EmployeePage employeePage = new EmployeePage(driver);

        // Test data
        String employeeName = "Mouli Selenium";
        String employeeEmail = "mouli.selenium@test.com";
        String department = "Testing";

        // Step 1: Open application
        employeePage.openPage();

        System.out.println("Application opened successfully.");

        // Step 2: Enter employee details
        employeePage.enterEmployeeDetails(
                employeeName,
                employeeEmail,
                department
        );

        System.out.println("Employee details entered.");

        // Step 3: Submit form
        employeePage.submitForm();

        // Step 4: Verify employee appears in table
        boolean isDisplayed =
                employeePage.isEmployeeDisplayed(employeeName);

        Assert.assertTrue(
                isDisplayed,
                "Employee was not added successfully!"
        );

        // Step 5: Verify success message
        Assert.assertEquals(
                employeePage.getMessage(),
                "Employee added successfully!",
                "Success message does not match!"
        );

        System.out.println("TEST PASSED: Employee added successfully!");
    }
}
