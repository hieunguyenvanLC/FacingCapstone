package capstone.fps.common;

public interface ConstantList {

    String DEF_CURRENCY = "USD";
    Double DEF_TAX_RATE = 0.1D;
    String LOCAL_URL = "http://localhost:8080/";

    String PAYPAL_CLIENT_ID = "Ae-WAkmVpZzdf5yYCDsw712GLWeT1RMqWTwirxkRRFnEEvMWKKxvpcn6WW7k2tv6Ck7RmRDEPLLUdJ0F";
    String PAYPAL_CLIENT_SECRET = "EAbfxREiU06k1rVeIbql1kC0zNc0_-3Yw6JY0hpMCMjT3VLd4PQj_eJuvop4xINX6OJProI3f6F3B2Nn";
    String PAYPAL_MODE = "sandbox";

    /* Order status */
    int ORD_NEW = 1;
    int ORD_ASS = 2;
    int ORD_BUY = 3;
    int ORD_REV = 4;
    int ORD_CXL = 5;

    /* Role */
    String ROL_ADM = "admin";
    String ROL_MEM = "member";
    String ROL_SHP = "shipper";

    /* Account status */
    int ACC_NEW = 1;
    // Account info has been checked and approved
    int ACC_CHK = 2;
    int ACC_BAN = 3;
}
