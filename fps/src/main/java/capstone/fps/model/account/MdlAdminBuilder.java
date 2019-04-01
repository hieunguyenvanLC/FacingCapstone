package capstone.fps.model.account;

import capstone.fps.entity.FRAccount;

public class MdlAdminBuilder {

    public MdlAdmin buildAdmDetail(FRAccount frAccount) {
        MdlAdmin mdlAccount = new MdlAdmin();
        mdlAccount.id = frAccount.getId();
        mdlAccount.phone = frAccount.getPhone();
        mdlAccount.name = frAccount.getName();
        mdlAccount.email = frAccount.getEmail();
        mdlAccount.natId = frAccount.getNatId();
        mdlAccount.natDate = frAccount.getNatDate();
        mdlAccount.dob = frAccount.getDateOfBirth();
        mdlAccount.createTime = frAccount.getCreateTime();
        mdlAccount.updateTime = frAccount.getUpdateTime();
        mdlAccount.deleteTime = frAccount.getDeleteTime();
        mdlAccount.note = frAccount.getNote();
        mdlAccount.status = frAccount.getStatus();
        mdlAccount.editor = frAccount.getEditor() != null ? frAccount.getEditor().getPhone() : null;
        return mdlAccount;
    }
}
