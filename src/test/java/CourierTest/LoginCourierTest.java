package CourierTest;

import BaseApiTest.BaseApiTestCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierModel;
import org.junit.Test;
import steps.CourierSteps;

import static data.CourierData.*;
import static data.CourierData.login;
import static data.CourierData.password;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.createCourier;

public class LoginCourierTest extends BaseApiTestCourier {
    @Test
    @DisplayName("Курьер может авторизоваться, успешный запрос возвращает 'id'")
    public void loginWithCorrectData() {
        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));
        Response loginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));

        loginResponse.then()
                .log().all()
                .statusCode(200)
                .body("id",notNullValue());
        courierId = loginResponse.jsonPath().getString("id");
    }
    @Test
    @DisplayName("Если нет поля 'login' запрос возвращает ошибку ")
    public void loginWithoutLoginData(){
        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));

        CourierSteps.loginCourier(new CourierLogin("", password))
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));


        Response validLoginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));
        courierId = validLoginResponse.jsonPath().getString("id");

    }


    @Test
    @DisplayName("Если нет поля 'password' запрос возвращает ошибку ")
    public void loginWithoutPasswordData(){
        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));

        CourierSteps.loginCourier(new CourierLogin(login, ""))
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        Response loginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));
        courierId = loginResponse.jsonPath().getString("id");
    }



    @Test
    @DisplayName("Запрос с неправильным логином выдает ошибку")
    public void loginIncorrectLoginData(){
        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));

        CourierSteps.loginCourier(new CourierLogin(login + "Sun", password))
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        Response loginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));
        courierId = loginResponse.jsonPath().getString("id");
    }


    @Test
    @DisplayName("Запрос с неправильным паролем выдает ошибку")
    public void loginIncorrectPasswordData(){
        CourierModel courier = new CourierModel(login, password, firstName);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok",equalTo(true));

        CourierSteps.loginCourier(new CourierLogin(login, password + "5"))
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        Response loginResponse = CourierSteps.loginCourier(new CourierLogin(login, password));
        courierId = loginResponse.jsonPath().getString("id");
    }
}
