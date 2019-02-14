package capstone.fps.model;


import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FROrder;

import java.util.Optional;

public class MdlOrderNew {

    private String shipAddress;
    private Integer frDistrictId;
    private String customerDescription;
    private Integer[] productList;
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
