
package org.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseUITest {

    protected WebDriver driver;

    // Runs before every UI test
    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Open browser in normal visible mode
        options.addArguments("--start-maximized");

        // Initialize Chrome browser
        driver = new ChromeDriver(options);

        System.out.println("Chrome browser launched successfully!");
    }

    // Runs after every UI test
    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("Chrome browser closed successfully!");
        }
    }
}
