package capstone.fps.model.account;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRReceiveMember;
import capstone.fps.repository.ReceiveMemberRepo;

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

    public MdlMember buildMemEntryAdm(FRAccount frAccount) {
        MdlMember mdlMember = new MdlMember();
        mdlMember.id = frAccount.getId();
        mdlMember.phone = frAccount.getPhone();
        mdlMember.name = frAccount.getName();
        mdlMember.email = frAccount.getEmail();
        mdlMember.note = frAccount.getNote();
        mdlMember.status = frAccount.getStatus();
        return mdlMember;
    }


    public MdlMember buildMemDetailAdm(FRAccount frAccount, ReceiveMemberRepo receiveMemberRepo) {
        MdlMember mdlMember = new MdlMember();
        mdlMember.id = frAccount.getId();
        mdlMember.phone = frAccount.getPhone();
        mdlMember.name = frAccount.getName();
        mdlMember.email = frAccount.getEmail();
        mdlMember.dob = frAccount.getDob();
        mdlMember.createTime = frAccount.getCreateTime();
        mdlMember.note = frAccount.getNote();
        mdlMember.status = frAccount.getStatus();
        mdlMember.editor = frAccount.getEditor() != null ? frAccount.getEditor().getPhone() : null;
        List<FRReceiveMember> receiveMembers = receiveMemberRepo.findAllByAccount(frAccount);
        mdlMember.faceList = new MdlFace[receiveMembers.size()];
        for (int i = 0; i < receiveMembers.size(); i++) {
            mdlMember.faceList[i] = new MdlFace(receiveMembers.get(i));
        }
        return mdlMember;
    }


    public MdlMember buildMemDetailMem(FRAccount frAccount, ReceiveMemberRepo receiveMemberRepo) {
        Methods methods = new Methods();
        MdlMember mdlMember = new MdlMember();
        mdlMember.phone = frAccount.getPhone();
        mdlMember.name = frAccount.getName();
        mdlMember.email = frAccount.getEmail();
        mdlMember.extraPoint = frAccount.getExtraPoint();
        mdlMember.reportPoint = frAccount.getReportPoint();
        mdlMember.dob = frAccount.getDob();
        mdlMember.status = frAccount.getStatus();
        mdlMember.wallet = frAccount.getWallet();
        mdlMember.avatar = methods.bytesToBase64(frAccount.getAvatar());
        List<FRReceiveMember> receiveMembers = receiveMemberRepo.findAllByAccount(frAccount);
        mdlMember.faceList = new MdlFace[receiveMembers.size()];
        for (int i = 0; i < receiveMembers.size(); i++) {
            mdlMember.faceList[i] = new MdlFace(receiveMembers.get(i));
        }
        return mdlMember;
    }

    public MdlMember buildMemAvatar(FRAccount frAccount) {
        Methods methods = new Methods();
        MdlMember mdlMember = new MdlMember();
        mdlMember.id = frAccount.getId();
        mdlMember.name = frAccount.getName();
        mdlMember.status = frAccount.getStatus();
        mdlMember.wallet = frAccount.getWallet();
        mdlMember.avatar = methods.bytesToBase64(frAccount.getAvatar());
        return mdlMember;
    }
}
