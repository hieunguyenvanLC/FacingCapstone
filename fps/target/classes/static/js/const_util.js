function Stat(index, msg) {
    this.index = index;
    this.msg = msg;
}

var fpsBackEnd = "http://" + window.location.hostname + ":8080";
// var fpsBackEnd = "http://localhost:8080";
// var fpsBackEnd = "http://10.82.137.209:8080";
var defaultImg = "/img/no_img.png";

/* API mapping */
var MAP_ANY = "/any";
var MAP_LOG = "/log";
var MAP_ADM = "/adm";
var MAP_MEM = "/mem";
var MAP_SHP = "/shp";
var MAP_API = "/api";


var RESP_SUCCESS = 1;
var RESP_NO_RESULT = 0;
var RESP_FAIL = -1;
var RESP_SERVER_ERROR = -2;


/* Role */
var ROL_ADM = "ROLE_ADMIN";
var ROL_MEM = "ROLE_MEMBER";
var ROL_SHP = "ROLE_SHIPPER";


/* Order status */
var ORD_NEW = new Stat(1, "new");
var ORD_ASS = new Stat(2, "assigned");
var ORD_BUY = new Stat(3, "on delivery");
var ORD_COM = new Stat(4, "done");
var ORD_CXL = new Stat(5, "cancel");
var ORD_STAT_LIST = [ORD_NEW, ORD_ASS, ORD_BUY, ORD_COM, ORD_CXL];

/* account status */
var ACC_NEW = new Stat(1, "in use");
var ACC_CHK = new Stat(2, "unverified");
var ACC_BAN = new Stat(3, "banned");
var accStatList = [ACC_NEW, ACC_CHK, ACC_BAN];


/* Product status */
var PRO_NEW = new Stat(1, "activated");
var PRO_HID = new Stat(2, "deactivated");
var PRO_STAT_LIST = [PRO_NEW, PRO_HID];


/* Product status */
var STO_NEW = new Stat(1, "activated");
var STO_HID = new Stat(2, "deactivated");
var stoStatList = [STO_NEW, STO_HID];