package data;

import com.github.javafaker.Faker;


public class CourierData {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_CREATE_URL = "/api/v1/courier";
    public static final String COURIER_LOGIN_URL = "/api/v1/courier/login";

    static Faker user = new Faker();

    public static  String login;
    public static  String password;
    public static  String firstName;

    public static void generateNewData() {
        login = user.name().username() + "_" + System.currentTimeMillis();
        password = user.regexify("[0-9]{6}");
        firstName = user.name().firstName();
    }

}
