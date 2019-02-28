package capstone.fps.common;

public interface Fix {

    //1 km ~ 0.02823
    double DEGREE_PER_KM = 0.02823;

    String DEF_CURRENCY = "USD";
    double DEF_TAX_RATE = 0.1D;
    String LOCAL_URL = "http://localhost:8080/";

    String PAYPAL_CLIENT_ID = "Ae-WAkmVpZzdf5yYCDsw712GLWeT1RMqWTwirxkRRFnEEvMWKKxvpcn6WW7k2tv6Ck7RmRDEPLLUdJ0F";
    String PAYPAL_CLIENT_SECRET = "EAbfxREiU06k1rVeIbql1kC0zNc0_-3Yw6JY0hpMCMjT3VLd4PQj_eJuvop4xINX6OJProI3f6F3B2Nn";
    String PAYPAL_MODE = "sandbox";

    /* API mapping */
    String MAP_ANY = "/any/";
    String MAP_LOG = "/log/";
    String MAP_ADM = "/adm/";
    String MAP_MEM = "/mem/";
    String MAP_SHP = "/shp/";
    String MAP_API = "api/";

    /* Order status */
    int ORD_NEW = 1;    //Status new
    int ORD_ASS = 2;    // accepted by shipper (delivering),
    int ORD_BUY = 3;    //waitting shipper
    int ORD_REV = 4;    // success
    int ORD_CXL = 5;    // cancel

    /* Role */
    String ROL_ADM = "ROLE_ADMIN";
    String ROL_MEM = "ROLE_MEMBER";
    String ROL_SHP = "ROLE_SHIPPER";

    /* Account status */
    int ACC_NEW = 1;
    // Account info has been checked and approved
    int ACC_CHK = 2;
    int ACC_BAN = 3; //Deactive

    /* Product status */
    int PRO_NEW = 1;
    int PRO_REV = 2;

    /* Product status */
    int STO_NEW = 1;
    int STO_CHK = 2;
    int STO_REV = 3;
}
