package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierModel;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создаем курьера")
    public static Response createCourier(CourierModel courier){

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE_URL)
                .then()
                .extract().response();
    }
    @Step("Логин курьера в системе")
    public static Response loginCourier(CourierLogin loginData){

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post(COURIER_LOGIN_URL)
                .then()
                .extract().response();

    }
    @Step("Удаляем курьера")
    public static Response delete(String id){

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(COURIER_CREATE_URL + "/" + id)
                .then()
                .extract().response();
    }

}
