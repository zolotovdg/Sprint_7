

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest extends BaseUtils {

    @Test
    @DisplayName("Логин курьера")
    @Description("Успешная авторизация в системе с использованием корректных данных")
    public void testCourierLoginCourier() {
        CreateCourierRequestModel courier = createCourier();
        LoginCourierRequestModel body = new LoginCourierRequestModel(courier.getLogin(), courier.getPassword());
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .body("id", not(emptyOrNullString()));
    }

    @Test
    @DisplayName("Логин курьера без логина и пароля")
    @Description("Попытка авторизации в системе без передачи логина и пароля")
    public void testCourierLoginWithoutCredentials() {
        LoginCourierRequestModel body = new LoginCourierRequestModel("", "");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера с неверными данными")
    @Description("Попытка авторизации в системе с использованием НЕкорректных данных")
    public void testCourierLoginWithWrongCredentials() {
        LoginCourierRequestModel body = new LoginCourierRequestModel("wronglog", "wrongpass");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
