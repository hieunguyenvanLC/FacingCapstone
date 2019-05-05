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
    private String face1;
    @Expose
    private String face2;
    @Expose
    private String face3;


    public MdlFace(FRReceiveMember frReceiveMember) {
        Methods methods = new Methods();
        this.id = frReceiveMember.getId();
        this.name = frReceiveMember.getName();
        this.face1 = methods.bytesToBase64(frReceiveMember.getFace1());
        this.face2 = methods.bytesToBase64(frReceiveMember.getFace2());
        this.face3 = methods.bytesToBase64(frReceiveMember.getFace3());
    }
}
