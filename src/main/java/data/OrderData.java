package data;

import com.github.javafaker.Faker;
import model.OrderModel;

public class OrderData {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String CREATE_ORDER_URL = "/api/v1/orders";
    public static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel";

    static Faker user = new Faker();

    public static final String FIRST_NAME = "Евстахий";
    public static final String LAST_NAME = "Пробов";
    public static final String ADDRESS = "Прибежище странника, 23";
    public static final String METRO_STATION= "2";
    public static final String PHONE = "+7 920 123 45 67";
    public static final String RENT_TIME= "5";
    public static final String DELIVERY_DATE= "2026-08-25";
    public static final String COMMENT= "Нежить найдёт способ покинуть Прибежище";

    public static final String[] COLOR_BLACK = {"BLACK"};
    public static final String[] COLOR_GREY = {"GREY"};
    public static final String[] COLOR_BOTH = {"BLACK", "GREY"};
    public static final String[] COLOR_NONE = {};

    public static OrderModel createOrderWithColors(String[] colors) {
        return new OrderModel(
                FIRST_NAME,
                LAST_NAME,
                ADDRESS,
                METRO_STATION,
                PHONE,
                RENT_TIME,
                DELIVERY_DATE,
                COMMENT,
                colors
        );
    }
}
