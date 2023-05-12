import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseUtils {

    @Test
    @DisplayName("Создание курьера")
    @Description("Успешное создание учетной записи курьера в системе")
    public void testCourierCreate() {
        CreateCourierRequestModel body = new CreateCourierRequestModel("Mamidjon123",
                "12345", "Mamidjon");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без данных")
    @Description("Попытка создания учетной записи курьера без передачи учетных данных")
    public void testCourierCreateWithoutCredentials() {
        CreateCourierRequestModel body = new CreateCourierRequestModel("",
                "", "");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Попытка создания учетной записи курьера с использованием уже существующих в системе учетных данных")
    public void testCourierCreateWithExistingCredentials() {
        CreateCourierRequestModel body = new CreateCourierRequestModel("Mamidjon" + UUID.randomUUID(),
                "12345", "Mamidjon");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
