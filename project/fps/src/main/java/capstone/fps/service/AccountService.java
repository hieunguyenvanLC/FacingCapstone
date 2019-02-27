package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRRole;
import capstone.fps.entity.FRShipper;
import capstone.fps.entity.FRSource;
import capstone.fps.model.MdlAccAdmAdm;
import capstone.fps.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepo accountRepo;
    private RoleRepo roleRepo;
    private SourceRepo sourceRepo;
    private PriceLevelRepo priceLevelRepo;
    private ShipperRepo shipperRepo;

    public AccountService(AccountRepo accountRepo, RoleRepo roleRepo, SourceRepo sourceRepo, PriceLevelRepo priceLevelRepo, ShipperRepo shipperRepo) {
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
        this.sourceRepo = sourceRepo;
        this.priceLevelRepo = priceLevelRepo;
        this.shipperRepo = shipperRepo;
    }

    private FRRole initRole(String name) {
        Optional<FRRole> optional;
        optional = roleRepo.findByName(name);
        if (optional.isPresent()) {
            return optional.get();
        }
        FRRole role = new FRRole();
        role.setName(name);
        roleRepo.save(role);
        optional = roleRepo.findByName(name);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public boolean createAccountMember(String phone, String pass, String name) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        long timeNow = methods.getTimeNow();

        phone = valid.checkPhone(phone);
        if (phone == null) {
            return false;
        }

        FRAccount frAccount = new FRAccount();
        frAccount.setPhone(phone);
        frAccount.setRole(initRole(Fix.ROL_MEM));
        frAccount.setPassword(methods.hashPass(pass));
        frAccount.setName(name);
        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setCreateTime(timeNow);
        frAccount.setNote("");
        frAccount.setStatus(Fix.ACC_NEW);
        accountRepo.save(frAccount);
        return true;
    }

    public boolean createAccountShipper(String phone, String pass, String name, String email, byte[] userImg, String natId, long natDate, long dob, String note, String bikeRegId, String introduce, byte[] natFrnt, byte[] natBack, byte[] bikeRegFrnt, byte[] bikeRegBack, int sourceId) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        long timeNow = methods.getTimeNow();

        phone = valid.checkPhone(phone);
        if (phone == null) {
            return false;
        }
        name = valid.checkFullName(name);
        if (name == null) {
            return false;
        }
        if (!valid.checkFace(userImg)) {
            return false;
        }
        natId = valid.checkNumber(natId);
        if (natId == null) {
            return false;
        }
        if (note == null) {
            note = "";
        }
        if (methods.getAge(dob) < 18) {
            return false;
        }
        bikeRegId = valid.checkNumber(bikeRegId);
        if (bikeRegId == null) {
            return false;
        }
        if (introduce == null) {
            introduce = "";
        }
        Optional<FRSource> sourceOptional = sourceRepo.findById(sourceId);
        if (!sourceOptional.isPresent()) {
            return false;
        }

        FRAccount frAccount = new FRAccount();
        frAccount.setPhone(phone);
        frAccount.setRole(initRole(Fix.ROL_SHP));
        frAccount.setPassword(methods.hashPass(pass));
        frAccount.setName(name);
        frAccount.setEmail(email);
        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setUserImage(userImg);
        frAccount.setNationalId(natId);
        frAccount.setNationalIdCreatedDate(natDate);
        frAccount.setDateOfBirth(dob);
        frAccount.setCreateTime(timeNow);
        frAccount.setNote(note);
        frAccount.setStatus(Fix.ACC_NEW);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);

        FRShipper frShipper = new FRShipper();
        frShipper.setAccount(frAccount);
        frShipper.setBikeRegistrationId(bikeRegId);
        frShipper.setIntroduce(introduce);
        frShipper.setNationalIdFrontImage(natFrnt);
        frShipper.setNationalIdBackImage(natBack);
        frShipper.setSumRevenue(0D);
        frShipper.setBikeRegistrationFrontImage(bikeRegFrnt);
        frShipper.setBikeRegistrationBackImage(bikeRegBack);
        frShipper.setPriceLevel(priceLevelRepo.getOne(1));
        frShipper.setSource(sourceOptional.get());
        shipperRepo.save(frShipper);
        return true;
    }

    public boolean createAccountAdmin(String phone, String pass, String name, String email, String natId, long natDate, long dob, String note) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        long timeNow = methods.getTimeNow();

        phone = valid.checkUsername(phone);
        if (phone == null) {
            return false;
        }
        name = valid.checkFullName(name);
        if (name == null) {
            return false;
        }
        natId = valid.checkNumber(natId);
        if (natId == null) {
            return false;
        }
        if (note == null) {
            note = "";
        }
        if (methods.getAge(dob) < 18) {
            return false;
        }

        FRAccount frAccount = new FRAccount();
        frAccount.setPhone(phone);
        frAccount.setRole(initRole(Fix.ROL_ADM));
        frAccount.setPassword(methods.hashPass(pass));
        frAccount.setName(name);
        frAccount.setEmail(email);
        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setNationalId(natId);
        frAccount.setNationalIdCreatedDate(natDate);
        frAccount.setDateOfBirth(dob);
        frAccount.setCreateTime(timeNow);
        frAccount.setNote(note);
        frAccount.setStatus(Fix.ACC_NEW);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        return true;
    }

    public boolean updateProfileMember(String phone, String pass, String name, String email, byte[] userImg, String natId, long natDate, long dob, String note) {
        Methods methods = new Methods();
        long date = methods.getTimeNow();
        Validator valid = new Validator();

        return false;
    }

    public boolean banAccount(Integer accountId, String reason) {
        if (accountId == null) {
            return false;
        }
        Optional<FRAccount> optional = accountRepo.findById(accountId);
        if (!optional.isPresent()) {
            return false;
        }
        FRAccount frAccount = optional.get();
        Methods methods = new Methods();
        long timeNow = methods.getTimeNow();
        frAccount.setDeleteTime(timeNow);
        frAccount.setStatus(Fix.ACC_BAN);
        frAccount.setNote(reason);
        accountRepo.save(frAccount);
        return true;
    }

    public List<MdlAccAdmAdm> getListAdmin() {
        Base64.Encoder encoder = Base64.getEncoder();
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_ADM));
        List<MdlAccAdmAdm> admList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            MdlAccAdmAdm adm = new MdlAccAdmAdm();
            String avatar = null;
            if(frAccount.getAvatar() != null){
                avatar = encoder.encodeToString(frAccount.getAvatar());
            }
            String editor = null;
            if(frAccount.getEditor() != null){
                editor = frAccount.getEditor().getPhone();
            }
            adm.setId(frAccount.getId());
            adm.setUsername(frAccount.getPhone());
            adm.setName(frAccount.getName());
            adm.setEmail(frAccount.getEmail());
            adm.setAvatar(avatar);
            adm.setNationalId(frAccount.getNationalId());
            adm.setNatIdDate(frAccount.getNationalIdCreatedDate());
            adm.setDob(frAccount.getDateOfBirth());
            adm.setCreateTime(frAccount.getCreateTime());
            adm.setUpdateTime(frAccount.getUpdateTime());
            adm.setDeleteTime(frAccount.getDeleteTime());
            adm.setNote(frAccount.getNote());
            adm.setStatus(frAccount.getStatus());
            adm.setEditor(editor);
            admList.add(adm);
        }
        return admList;
    }
}
