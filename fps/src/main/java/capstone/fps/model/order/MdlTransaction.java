package capstone.fps.model.order;

import capstone.fps.entity.FRTransaction;
import com.google.gson.annotations.Expose;

public class MdlTransaction {
    @Expose
    String paymentId;
    @Expose
    Double amount;
    @Expose
    Long time;

    public MdlTransaction(FRTransaction frTransaction) {
        this.paymentId = frTransaction.getPayId();
        this.amount = frTransaction.getAmount();
        this.time = frTransaction.getTime();
    }
}
