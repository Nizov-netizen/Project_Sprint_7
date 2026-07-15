package BaseApiTest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import steps.CourierSteps;

import static data.CourierData.BASE_URL;
import static data.CourierData.generateNewData;

public class BaseApiTestCourier {

    protected String courierId;

    @Before
    public void setup(){
        RestAssured.baseURI = BASE_URL;
        generateNewData();
    }
    @After
    public void tearDown(){
        if (courierId != null) {
            CourierSteps.delete(courierId);
        }
    }
}
