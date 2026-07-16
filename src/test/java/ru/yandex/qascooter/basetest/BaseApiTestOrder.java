package ru.yandex.qascooter.basetest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import steps.OrderSteps;

import static data.OrderData.BASE_URL;
import static org.apache.http.HttpStatus.SC_OK;


public class BaseApiTestOrder {

    protected String orderTrack;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void tearDown() {
        if (orderTrack != null) {
            OrderSteps.cancelOrder(orderTrack)
                    .then()
                    .statusCode(SC_OK);

        }
    }
}