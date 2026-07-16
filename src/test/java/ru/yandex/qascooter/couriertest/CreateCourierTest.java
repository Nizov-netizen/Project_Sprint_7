package ru.yandex.qascooter.couriertest;

import ru.yandex.qascooter.basetest.BaseApiTestCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierModel;
import org.junit.Test;
import steps.CourierSteps;

import static data.CourierData.*;
import static org.apache.http.HttpStatus.*;
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
                .statusCode(SC_CREATED)
                .body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров, код ответа 409, 'message': 'Этот логин уже используется. Попробуйте другой.'")
    public void checkCannotCreateTwoIdenticalCourier(){

        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("ok",equalTo(true));


        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutLogin(){
        CourierModel courier = new CourierModel(null, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля, код ответа 400, 'message':'Недостаточно данных для создания учетной записи'")
    public void checkCannotCreateCourierWithoutPassword(){
        CourierModel courier = new CourierModel(login, null, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
}
