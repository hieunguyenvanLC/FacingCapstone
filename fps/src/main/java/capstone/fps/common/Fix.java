package capstone.fps.common;

import capstone.fps.model.Stat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface Fix {

    //  360 / ≈40,000km
    double DEGREE_PER_KM = 0.009;


    double USD = 23199.50D;
    String DEF_CURRENCY = "USD";
    double DEF_TAX_RATE = 0.1D;
    String LOCAL_URL = "http://localhost:8080/";
    String PAY_SERVER_URL = "http://192.168.100.127:8084/";




//    Path IMG_DIR_PATH = Paths.get("src/main/resources/static/img/").toAbsolutePath().normalize();
//    Path IMG_DIR_PATH = Paths.get("../../faces/").toAbsolutePath().normalize();

    String FACE_FOLDER = "/Users/nguyenvanhieu/Project/CapstoneProject/docker/data/fps/";
    String TEST_FACE_FOLDER="/Users/nguyenvanhieu/Project/CapstoneProject/docker/data/testFace/";
    String CROP_FACE_FOLDER="/Users/nguyenvanhieu/Project/CapstoneProject/docker/output/fps/";
    String CROP_TEST_FACE_FOLDER="/Users/nguyenvanhieu/Project/CapstoneProject/docker/output/test/";
    String DEF_IMG_TYPE = "jpeg";

    String PAYPAL_CLIENT_ID = "Ae-WAkmVpZzdf5yYCDsw712GLWeT1RMqWTwirxkRRFnEEvMWKKxvpcn6WW7k2tv6Ck7RmRDEPLLUdJ0F";
    String PAYPAL_CLIENT_SECRET = "EAbfxREiU06k1rVeIbql1kC0zNc0_-3Yw6JY0hpMCMjT3VLd4PQj_eJuvop4xINX6OJProI3f6F3B2Nn";
    String PAYPAL_MODE = "sandbox";

    /* API mapping */
    String MAP_ANY = "/any";
    String MAP_LOG = "/log";
    String MAP_ADM = "/adm";
    String MAP_MEM = "/mem";
    String MAP_SHP = "/shp";
    String MAP_API = "/api";

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
    Stat[] ORD_STAT_LIST = {ORD_NEW, ORD_ASS, ORD_BUY, ORD_COM, ORD_CXL};


    /* account status */
    Stat ACC_NEW = new Stat(1, "new");
    // account info has been checked and approved
    Stat ACC_CHK = new Stat(2, "checked");
    Stat ACC_BAN = new Stat(3, "banned");
    Stat[] ACC_STAT_LIST = {ACC_NEW, ACC_CHK, ACC_BAN};


    /* Product status */
    Stat PRO_NEW = new Stat(1, "new");
    Stat PRO_HID = new Stat(2, "hidden");
    Stat[] PRO_STAT_LIST = {PRO_NEW, PRO_HID};


    /* Product status */
    Stat STO_NEW = new Stat(1, "new");
    Stat STO_HID = new Stat(2, "hidden");
    Stat[] STO_STAT_LIST = {STO_NEW, STO_HID};


}
