package capstone.fps.common;

import capstone.fps.model.Stat;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface Fix {

    //1 km ~ 0.02823
    double DEGREE_PER_KM = 0.02823;

    String DEF_CURRENCY = "USD";
    double DEF_TAX_RATE = 0.1D;
    String LOCAL_URL = "http://localhost:8080/";
    Path IMG_DIR_PATH = Paths.get("src/main/resources/static/img/").toAbsolutePath().normalize();


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

    /* Role */
    String ROL_ADM = "ROLE_ADMIN";
    String ROL_MEM = "ROLE_MEMBER";
    String ROL_SHP = "ROLE_SHIPPER";

    /* Order status */
    Stat ORD_NEW = new Stat(1, "new");
    Stat ORD_ASS = new Stat(2, "assigned");
    Stat ORD_BUY = new Stat(3, "bought");
    Stat ORD_COM = new Stat(4, "done");
    Stat ORD_CXL = new Stat(5, "cancel");

    /* account status */
    Stat ACC_NEW = new Stat(1, "new");
    // account info has been checked and approved
    Stat ACC_CHK = new Stat(2, "checked");
    Stat ACC_BAN = new Stat(3, "banned");

    /* Product status */
    Stat PRO_NEW = new Stat(1, "new");
    Stat PRO_HID = new Stat(2, "hidden");

    /* Product status */
    Stat STO_NEW = new Stat(1, "new");
    Stat STO_HID = new Stat(2, "hidden");


}
