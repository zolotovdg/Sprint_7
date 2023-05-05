import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ListOrderTest extends BaseUtils {
    @Test
    public void sandbox() {

    }
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Успешное получение списка заказов курьером")
    public void testOrderList() {

        given()
                .contentType("application/json")
                .queryParam("limit", 1)
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders[0].id", not(empty()))
                .body("orders[0].courierId", not(empty()))
                .body("orders[0].firstName", not(empty()))
                .body("orders[0].lastName", not(empty()))
                .body("orders[0].address", not(empty()))
                .body("orders[0].metroStation", not(empty()))
                .body("orders[0].phone", not(empty()))
                .body("orders[0].rentTime", not(empty()))
                .body("orders[0].deliveryDate", not(empty()))
                .body("orders[0].track", not(empty()))
                .body("orders[0].comment", not(empty()))
                .body("orders[0].createdAt", not(empty()))
                .body("orders[0].updatedAt", not(empty()))
                .body("orders[0].status", not(empty()));
    }
}
