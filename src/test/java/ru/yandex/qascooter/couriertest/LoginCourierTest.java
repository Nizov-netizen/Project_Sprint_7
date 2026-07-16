package ru.yandex.qascooter.couriertest;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierModel;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qascooter.basetest.BaseApiTestCourier;
import steps.CourierSteps;

import static data.CourierData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.createCourier;

public class LoginCourierTest extends BaseApiTestCourier {

    private CourierModel createdCourier;

@Before
public void createCourierTest(){
    createdCourier = new CourierModel(login, password, firstName);
    Response createResponse = createCourier(createdCourier);
    createResponse.then()
            .log().all()
            .statusCode(SC_CREATED)
            .body("ok",equalTo(true));
}


    @Test
    @DisplayName("Курьер может авторизоваться, успешный запрос возвращает 'id'")
    public void loginWithCorrectData() {
        Response loginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));

        loginResponse.then()
                .log().all()
                .statusCode(SC_OK)
                .body("id",notNullValue());
    }
    @Test
    @DisplayName("Если нет поля 'login' запрос возвращает ошибку ")
    public void loginWithoutLoginData(){
        CourierSteps.loginCourier(new CourierLogin("", password))
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Если нет поля 'password' запрос возвращает ошибку ")
    public void loginWithoutPasswordData(){
        CourierSteps.loginCourier(new CourierLogin(login, ""))
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }



    @Test
    @DisplayName("Запрос с неправильным логином выдает ошибку")
    public void loginIncorrectLoginData(){
        CourierSteps.loginCourier(new CourierLogin(login + "Sun", password))
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("Запрос с неправильным паролем выдает ошибку")
    public void loginIncorrectPasswordData(){
        CourierSteps.loginCourier(new CourierLogin(login, password + "5"))
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}