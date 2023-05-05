import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class BaseUtils {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
        RestAssured.requestSpecification = new RequestSpecBuilder().addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();
    }

    public CreateCourierRequestModel createCourier() {
        CreateCourierRequestModel body = new CreateCourierRequestModel("Mamidjon" + UUID.randomUUID(),
                "12345", "Mamidjon");
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/courier");
        return body;
    }

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
}
