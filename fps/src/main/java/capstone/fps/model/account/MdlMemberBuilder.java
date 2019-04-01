package capstone.fps.model.account;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRReceiveMember;
import capstone.fps.repository.ReceiveMemberRepo;

import java.util.ArrayList;
import java.util.List;

public class MdlMemberBuilder {

    /*
    public MdlAccount buildFull(FRAccount frAccount) {
        Methods methods = new Methods();
        MdlAccount mdlAccount = new MdlAccount();
        mdlAccount.id = frAccount.getId();
        mdlAccount.phone = frAccount.getPhone();
        mdlAccount.role = frAccount.getRole().getName();
        mdlAccount.password = frAccount.getPassword();
        mdlAccount.name = frAccount.getName();
        mdlAccount.email = frAccount.getEmail();
        mdlAccount.extraPoint = frAccount.getExtraPoint();
        mdlAccount.reportPoint = frAccount.getReportPoint();
        mdlAccount.userImage = methods.bytesToBase64(frAccount.getUserImage());
        mdlAccount.natId = frAccount.getNatId();
        mdlAccount.natDate = frAccount.getNatDate();
        mdlAccount.dob = frAccount.getDateOfBirth();
        mdlAccount.createTime = frAccount.getCreateTime();
        mdlAccount.updateTime = frAccount.getUpdateTime();
        mdlAccount.deleteTime = frAccount.getDeleteTime();
        mdlAccount.note = frAccount.getNote();
        mdlAccount.status = frAccount.getStatus();
        mdlAccount.editor = frAccount.getEditor() != null ? frAccount.getEditor().getPhone() : null;
        mdlAccount.avatar = methods.bytesToBase64(frAccount.getUserImage());
        return mdlAccount;
    }
    */


    public MdlMember buildMemDetailAdm(FRAccount frAccount) {
        Methods methods = new Methods();
        MdlMember mdlAccount = new MdlMember();
        mdlAccount.id = frAccount.getId();
        mdlAccount.phone = frAccount.getPhone();
        mdlAccount.name = frAccount.getName();
        mdlAccount.email = frAccount.getEmail();
        mdlAccount.userImage = methods.bytesToBase64(frAccount.getUserImage());
        mdlAccount.dob = frAccount.getDateOfBirth();
        mdlAccount.createTime = frAccount.getCreateTime();
        mdlAccount.note = frAccount.getNote();
        mdlAccount.status = frAccount.getStatus();
        mdlAccount.editor = frAccount.getEditor() != null ? frAccount.getEditor().getPhone() : null;
        return mdlAccount;
    }


    public MdlMember buildMemDetailMem(FRAccount frAccount, ReceiveMemberRepo receiveMemberRepo) {
        Methods methods = new Methods();
        MdlMember mdlMember = new MdlMember();
        mdlMember.phone = frAccount.getPhone();
        mdlMember.name = frAccount.getName();
        mdlMember.email = frAccount.getEmail();
        mdlMember.extraPoint = frAccount.getExtraPoint();
        mdlMember.reportPoint = frAccount.getReportPoint();
        mdlMember.dob = frAccount.getDateOfBirth();
        mdlMember.avatar = methods.bytesToBase64(frAccount.getUserImage());
        List<FRReceiveMember> receiveMembers = receiveMemberRepo.findAllByAccount(frAccount);
        mdlMember.faceList = new ArrayList<>();
        for (FRReceiveMember frReceiveMember : receiveMembers) {
            mdlMember.faceList.add(new MdlFace(frReceiveMember));
        }
        return mdlMember;
    }
}
