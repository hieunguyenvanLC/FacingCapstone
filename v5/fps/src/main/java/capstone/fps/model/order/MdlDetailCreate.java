package capstone.fps.model.order;

import capstone.fps.entity.FRProduct;

public class MdlDetailCreate {
    private FRProduct frProduct;
    private int quantity;

    public MdlDetailCreate(FRProduct frProduct, int quantity) {
        this.frProduct = frProduct;
        this.quantity = quantity;
    }

    public FRProduct getFrProduct() {
        return frProduct;
    }

    public int getQuantity() {
        return quantity;
    }
}
