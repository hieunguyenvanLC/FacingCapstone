export class Constant {
    // public APIURL = "http://localhost:8080";
    public APIURL = "http://192.168.43.247:8080";
    public GOOGLEAPI = "https://maps.googleapis.com/maps/api/directions/json?origin="
    //https://maps.googleapis.com/maps/api/directions/json?origin=10.8529021,106.6295327&destination=10.857733,106.628304&key=AIzaSyDjWQt6GF2HGafIImK98n_OzfaC0tk2hUU
    public GOOGLEKEY = "AIzaSyASMHVlLhJr78esAqJVglJU67r-SD-VBNQ";
    // public GOOGLEKEY = "AIzaSyDjWQt6GF2HGafIImK98n_OzfaC0tk2hUU";
    

    // MAPPING API
    public MAP_ANY = "/any";
    public MAP_LOG = "/log";
    public MAP_ADM = "/adm";
    public MAP_MEM = "/mem";
    public MAP_SHP = "/shp";
    public MAP_API = "/api";

    // Role
    public ROL_ADM = "ROLE_ADMIN";
    public ROL_MEM = "ROLE_MEMBER";
    public ROL_SHP = "ROLE_SHIPPER";

    // Page
    public LOGIN = "/sign_in";
    public LOGOUT = "/sign_out";
    public ACCOUNT = "/account";
    public STORE = "/store";
    public ORDER = "/order";

    public FACE = "/face";

    //Msg
    public LOADINGMSG = "Loading...";
    public CHECKOUT = "/checkout"
}
