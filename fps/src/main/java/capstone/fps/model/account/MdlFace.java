package capstone.fps.model.account;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRReceiveMember;
import com.google.gson.annotations.Expose;

public class MdlFace {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String face;

    public MdlFace(FRReceiveMember frReceiveMember) {
        Methods methods = new Methods();
        this.id = frReceiveMember.getId();
        this.name = frReceiveMember.getName();
        this.face = methods.bytesToBase64(frReceiveMember.getFace());
    }
}
