
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task1Application {

    public static void main(String[] args) {

        SpringApplication.run(Task1Application.class, args);

        System.out.println("====================================");
        System.out.println(" Employee Management Application");
        System.out.println(" Application Started Successfully!");
        System.out.println("====================================");
    }
}
