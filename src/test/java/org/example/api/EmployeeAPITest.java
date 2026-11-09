
package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EmployeeAPITest {

    private static Long employeeId;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        System.out.println("REST Assured API Testing Started");
    }

    @Test(priority = 1)
    public void createEmployee() {

        String requestBody = """
                {
                    "name": "Mouli API",
                    "email": "mouli.api@test.com",
                    "department": "Testing"
                }
                """;

        Response response =
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("/api/employees");

        response.then().statusCode(201);

        employeeId = response.jsonPath().getLong("id");

        Assert.assertNotNull(employeeId);

        Assert.assertEquals(
                response.jsonPath().getString("name"),
                "Mouli API"
        );

        System.out.println("CREATE Employee: PASSED");
    }

    @Test(priority = 2, dependsOnMethods = "createEmployee")
    public void getEmployeeById() {

        RestAssured.given()
                .when()
                .get("/api/employees/" + employeeId)
                .then()
                .statusCode(200)
                .body("name",
                        org.hamcrest.Matchers.equalTo("Mouli API"));

        System.out.println("GET Employee: PASSED");
    }

    @Test(priority = 3, dependsOnMethods = "createEmployee")
    public void updateEmployee() {

        String updatedBody = """
                {
                    "name": "Mouli Updated",
                    "email": "mouli.updated@test.com",
                    "department": "Development"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updatedBody)
                .when()
                .put("/api/employees/" + employeeId)
                .then()
                .statusCode(200)
                .body("name",
                        org.hamcrest.Matchers.equalTo("Mouli Updated"));

        System.out.println("UPDATE Employee: PASSED");
    }

    @Test(priority = 4, dependsOnMethods = "createEmployee")
    public void getAllEmployees() {

        RestAssured.given()
                .when()
                .get("/api/employees")
                .then()
                .statusCode(200);

        System.out.println("GET All Employees: PASSED");
    }

    @Test(priority = 5, dependsOnMethods = "createEmployee")
    public void deleteEmployee() {

        RestAssured.given()
                .when()
                .delete("/api/employees/" + employeeId)
                .then()
                .statusCode(204);

        System.out.println("DELETE Employee: PASSED");
    }

    @Test(priority = 6, dependsOnMethods = "deleteEmployee")
    public void verifyDeletedEmployee() {

        RestAssured.given()
                .when()
                .get("/api/employees/" + employeeId)
                .then()
                .statusCode(404);

        System.out.println("VERIFY DELETE: PASSED");
    }
}
