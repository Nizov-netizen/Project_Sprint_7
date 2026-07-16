package ru.yandex.qascooter.ordertest;

import ru.yandex.qascooter.basetest.BaseApiTestOrder;
import data.OrderData;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;

import static data.OrderData.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTestOrder {

    private final String colorsName;
    private final String[] colors;

    public CreateOrderTest(String colorsName, String[] colors) {
        this.colorsName = colorsName;
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Только черный цвет", COLOR_BLACK},
                {"Только серый цвет", COLOR_GREY},
                {"Черный и серый цвета", COLOR_BOTH},
                {"Без выбора цвета", COLOR_NONE},
        });
    }

    @Test
    public void createOrderWithColors() {
        OrderModel order = OrderData.createOrderWithColors(colors);

        Response response = OrderSteps.createOrder(order);

        response.then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        orderTrack = response.jsonPath().getString("track");
    }
}

