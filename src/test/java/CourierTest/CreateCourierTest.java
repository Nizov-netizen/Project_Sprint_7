package CourierTest;

import BaseApiTest.BaseApiTestCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierModel;
import org.junit.Test;
import steps.CourierSteps;

import static data.CourierData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;

public class CreateCourierTest extends BaseApiTestCourier {

    @Test
    @DisplayName("Курьера можно создать,код ответа 201, запрос возвращает 'ok: true'")
    public void testsCreateCourierSuccess(){
        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));

        CourierLogin loginData = new CourierLogin(login, password);
        Response loginResponse = CourierSteps.loginCourier(loginData);

        courierId = loginResponse.jsonPath().getString("id");

    }
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров, код ответа 409, 'message': 'Этот логин уже используется. Попробуйте другой.'")
    public void checkCannotCreateTwoIdenticalCourier(){

        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));


        createCourier(courier)
                .then()
                .log().all()
                .statusCode(409)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));

        CourierLogin loginData = new CourierLogin(login, password);
        Response loginResponse = CourierSteps.loginCourier(loginData);

        courierId = loginResponse.jsonPath().getString("id");
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutLogin(){
        CourierModel courier = new CourierModel(null, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutPassword(){
        CourierModel courier = new CourierModel(login, null, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
}
