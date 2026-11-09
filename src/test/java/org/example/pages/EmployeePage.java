
package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Web elements
    private final By nameField = By.id("name");
    private final By emailField = By.id("email");
    private final By departmentField = By.id("department");
    private final By submitButton = By.id("addEmployeeButton");
    private final By employeeTable = By.id("employeeTableBody");
    private final By message = By.id("message");

    public EmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Open employee management page
    public void openPage() {
        driver.get("http://localhost:8080/employees");
    }

    // Add employee details
    public void enterEmployeeDetails(
            String name, String email, String department) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField))
                .clear();
        driver.findElement(nameField).sendKeys(name);

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);

        driver.findElement(departmentField).sendKeys(department);
    }

    // Submit employee form
    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton))
                .click();
    }

    // Get employee table content
    public String getEmployeeTableText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeTable)
        ).getText();
    }

    // Get application message
    public String getMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(message)
        ).getText();
    }

    // Check whether employee appears in table
    public boolean isEmployeeDisplayed(String name) {
        return wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        employeeTable, name
                )
        );
    }
}
