import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseUtils {


    @BeforeAll
    @Step("Настройка RESTAssured")
    public static void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
        RestAssured.requestSpecification = new RequestSpecBuilder().addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();
    }

    @AfterEach
    @Step("Удаление тестовых данных после отработки теста")
    public void teardown() {
        String id = getCourierId("Mamidjon123", "12345");
        if (id == null) {
            return;
        } else {
            deleteCourier(id);
        }
    }

    @Step("Создание учетной записи курьера")
    public CreateCourierRequestModel createCourier() {
        CreateCourierRequestModel body = new CreateCourierRequestModel("Mamidjon123",
                "12345", "Mamidjon");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier");
        return body;
    }

    @Step("Получение ID курьера")
    public String getCourierId(String username, String password) {
        LoginCourierRequestModel body = new LoginCourierRequestModel(username, password);
        String response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().asString();
        JsonPath j = new JsonPath(response);
        String id = j.getString("id");
        return id;
    }

    @Step("Создание заказа")
    public String makeOrder() {
        List<String> color = new ArrayList<>();
        color.add("BLACK");
        MakeOrderRequestModel body = new MakeOrderRequestModel("Михаил", "Сидоров",
                "г. Москва, ул. Пушкина", 4, "+79265554433", 5,
                "2020-06-06", "Это комментарий", color);
        String response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract().asString();
        JsonPath j = new JsonPath(response);
        String track = j.getString("track");
        return track;
    }

    public HashMap<String, String> makeCourierWithOrder() {
        CreateCourierRequestModel courier = createCourier();
        String courierId = getCourierId(courier.getLogin(), courier.getPassword());
        String orderTrack = makeOrder();
        String response = given()
                .contentType("application/json")
                .queryParam("t", orderTrack)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .extract().asString();
        JsonPath j = new JsonPath(response);
        String orderId = j.getString("order.id");
        HashMap<String, String> map = new HashMap<>();
        map.put("courierId", courierId);
        map.put("orderId", orderId);
        return map;
    }

    @Step("Удаление учетной записи курьера")
    public DeleteCourierRequestModel deleteCourier(String id) {
        DeleteCourierRequestModel body = new DeleteCourierRequestModel(id);
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier/:"+ id);
        return body;
    }
}
