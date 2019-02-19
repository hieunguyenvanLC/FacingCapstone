package capstone.fps.model;


import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FROrder;
import com.google.gson.annotations.Expose;

import java.util.Optional;

public class MdlOrderNew {

    @Expose
    private String shipAddress;
    @Expose
    private Integer frDistrictId;
    @Expose
    private String customerDescription;
    @Expose
    private Integer[] productList;
    @Expose
    private Integer[] quantityList;


    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Integer getFrDistrictId() {
        return frDistrictId;
    }

    public void setFrDistrictId(Integer frDistrictId) {
        this.frDistrictId = frDistrictId;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public Integer[] getProductList() {
        return productList;
    }

    public void setProductList(Integer[] productList) {
        this.productList = productList;
    }

    public Integer[] getQuantityList() {
        return quantityList;
    }

    public void setQuantityList(Integer[] quantityList) {
        this.quantityList = quantityList;
    }
}
