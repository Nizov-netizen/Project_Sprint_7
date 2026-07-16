package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.CANCEL_ORDER_URL;
import static data.OrderData.CREATE_ORDER_URL;
import static io.restassured.RestAssured.given;


public class OrderSteps {

    @Step("Создание заказа")
    public static Response createOrder(OrderModel order) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER_URL)
                .then()
                .extract().response();
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(String track) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER_URL)
                .then()
                .extract().response();
    }
    @Step("Получение списка заказов")
    public static Response getOrders() {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(CREATE_ORDER_URL)
                .then()
                .extract().response();
    }
}
