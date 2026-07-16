package ru.yandex.qascooter.basetest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import steps.CourierSteps;

import static data.CourierData.*;

public class BaseApiTestCourier {

    protected String courierId;

    @Before
    public void setup(){
        RestAssured.baseURI = BASE_URL;
        generateNewData();
    }
    @After
    public void tearDown(){
        if (courierId == null) {
            Response loginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));
            courierId = loginResponse.jsonPath().getString("id");
        }
        if (courierId != null) {
            CourierSteps.delete(courierId);
        }
    }
}
