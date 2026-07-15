package OrderTest;

import BaseApiTest.BaseApiTestOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import steps.OrderSteps;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.greaterThan;

public class GetOrdersTest extends BaseApiTestOrder {
    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersReturnsListTest() {
        Response response = OrderSteps.getOrders();

        response.then()
                .log().all()
                .statusCode(200)
                .body("orders", instanceOf(Iterable.class))
                .body("orders.size()", greaterThan(0));

    }
}