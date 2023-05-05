
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MakeOrderTest extends BaseUtils {

    static Stream<MakeOrderRequestModel> orderProvider() {
        List<String> black = new ArrayList<>();
        black.add("BLACK");
        List<String> gray = new ArrayList<>();
        gray.add("GRAY");
        List<String> both = new ArrayList<>();
        both.add("BLACK");
        both.add("GRAY");
        return Stream.of(new MakeOrderRequestModel("Михаил", "Сидоров",
                "г. Москва, ул. Пушкина", 4, "+79265554433", 5,
                "2020-06-06", "Это комментарий", black),
                new MakeOrderRequestModel("Михаил", "Сидоров",
                        "г. Москва, ул. Пушкина", 4, "+79265554433", 5,
                        "2020-06-06", "Это комментарий", gray),
                new MakeOrderRequestModel("Михаил", "Сидоров",
                        "г. Москва, ул. Пушкина", 4, "+79265554433", 5,
                        "2020-06-06", "Это комментарий", both),
                new MakeOrderRequestModel("Михаил", "Сидоров",
                        "г. Москва, ул. Пушкина", 4, "+79265554433", 5,
                        "2020-06-06", "Это комментарий", new ArrayList<>())
        );
    }

    @ParameterizedTest
    @MethodSource("orderProvider")
    @DisplayName("Создание заказа")
    @Description("Успешное создание заказа")
    public void testOrderCreate(MakeOrderRequestModel body) {
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .body("track", not(empty()));
    }

}
