package capstone.fps.common;

public interface ConstantList {

    String DEF_CURRENCY = "USD";
    Double DEF_TAX_RATE = 0.1D;
    String LOCAL_URL = "http://localhost:8080/";

    String PAYPAL_CLIENT_ID = "Ae-WAkmVpZzdf5yYCDsw712GLWeT1RMqWTwirxkRRFnEEvMWKKxvpcn6WW7k2tv6Ck7RmRDEPLLUdJ0F";
    String PAYPAL_CLIENT_SECRET = "EAbfxREiU06k1rVeIbql1kC0zNc0_-3Yw6JY0hpMCMjT3VLd4PQj_eJuvop4xINX6OJProI3f6F3B2Nn";
    String PAYPAL_MODE = "sandbox";

    //Order
    int ORDER_STAT_BOOK = 1;
    int ORDER_STAT_TAKE = 2;
    int ORDER_STAT_DELI = 3;
    int ORDER_STAT_DONE = 4;
    int ORDER_STAT_VOID = 5;
}
