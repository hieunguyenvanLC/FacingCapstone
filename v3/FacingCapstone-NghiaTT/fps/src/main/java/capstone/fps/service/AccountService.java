package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRRole;
import capstone.fps.entity.FRShipper;
import capstone.fps.entity.FRSource;
import capstone.fps.model.Response;
import capstone.fps.model.account.MdlAdmAccMemGet;
import capstone.fps.model.account.MdlAdmAccAdmGet;
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
        return optional.orElse(null);
    }

    public Response createAccountMember(String phone, String pass, String name) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        phone = valid.checkPhone(phone);
        if (phone == null) {
            response.setResponse(Response.STATUS_FAIL, "Please enter valid phone number.");
            return response;
        }

        FRAccount frAccount = new FRAccount();
        frAccount.setPhone(phone);
        frAccount.setRole(initRole(Fix.ROL_MEM));
        frAccount.setPassword(methods.hashPass(pass));
        frAccount.setName(name);
        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote("");
        frAccount.setStatus(Fix.ACC_NEW.index);
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public boolean createAccountShipper(String phone, String pass, String name, String email, byte[] userImg, String natId, long natDate, long dob, String note, String bikeRegId, String introduce, byte[] natFrnt, byte[] natBack, byte[] bikeRegFrnt, byte[] bikeRegBack, int sourceId) {
        Methods methods = new Methods();
        Validator valid = new Validator();

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
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setStatus(Fix.ACC_NEW.index);
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

    public Response createAccountAdmin(String phone, String pass, String name, String email, String natId, long natDate, long dob, String note) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        phone = valid.checkUsername(phone);
        if (phone == null) {
            response.setResponse(Response.STATUS_FAIL, "Username invalid");
            return response;
        }
        name = valid.checkFullName(name);
        if (name == null) {
            response.setResponse(Response.STATUS_FAIL, "name invalid");
            return response;
        }
        natId = valid.checkNumber(natId);
        if (natId == null) {
            response.setResponse(Response.STATUS_FAIL, "NatId invalid");
            return response;
        }
        if (note == null) {
            note = "";
        }
        if (methods.getAge(dob) < 18) {
            response.setResponse(Response.STATUS_FAIL, "Age invalid");
            return response;
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
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setStatus(Fix.ACC_NEW.index);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public Response editAccountMember(Integer accId, String name, String email, long dob, String note) {
        Methods methods = new Methods();
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Optional<FRAccount> optional = accountRepo.findById(accId);
        if (!optional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find account");
            return response;
        }
        FRAccount frAccount = optional.get();
        if (!frAccount.getRole().getName().equals(Fix.ROL_MEM)) {
            response.setResponse(Response.STATUS_FAIL, "Not a member");
            return response;
        }
        if (note == null) {
            note = "";
        }

        frAccount.setName(name.trim());
        frAccount.setEmail(email.trim());
        frAccount.setDateOfBirth(dob);
        frAccount.setUpdateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public Response editAccountAdmin(int accId, String name, String email, String natId, long natDate, long dob, String note) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Optional<FRAccount> optional = accountRepo.findById(accId);
        if (!optional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find account");
            return response;
        }
        FRAccount frAccount = optional.get();
        if (!frAccount.getRole().getName().equals(Fix.ROL_ADM)) {
            response.setResponse(Response.STATUS_FAIL, "Not a member");
            return response;
        }
        name = valid.checkFullName(name);
        if (name == null) {
            response.setResponse(Response.STATUS_FAIL, "Name invalid");
            return response;
        }
        natId = valid.checkNumber(natId);
        if (natId == null) {
            response.setResponse(Response.STATUS_FAIL, "NatId invalid");
            return response;
        }
        if (note == null) {
            note = "";
        }
        if (methods.getAge(dob) < 18) {
            response.setResponse(Response.STATUS_FAIL, "Age invalid");
            return response;
        }

        frAccount.setName(name);
        frAccount.setEmail(email);
        frAccount.setNationalId(natId);
        frAccount.setNationalIdCreatedDate(natDate);
        frAccount.setDateOfBirth(dob);
        frAccount.setUpdateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

//    public boolean updateProfileMember(String phone, String pass, String name, String email, byte[] userImg, String natId, long natDate, long dob, String note) {
//        Methods methods = new Methods();
//        Validator valid = new Validator();
//
//        return false;
//    }

    public boolean banAccount(Integer accountId, String reason) {
        Methods methods = new Methods();

        if (accountId == null) {
            return false;
        }
        if (methods.getUser().getId().equals(accountId)) {
            return false;
        }
        Optional<FRAccount> optional = accountRepo.findById(accountId);
        if (!optional.isPresent()) {
            return false;
        }
        if (methods.nullOrSpace(reason)) {
            reason = "";
        }

        FRAccount frAccount = optional.get();
        frAccount.setDeleteTime(methods.getTimeNow());
        frAccount.setStatus(Fix.ACC_BAN.index);
        frAccount.setNote(reason);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        return true;
    }

    public boolean activateAccount(Integer accountId, String reason) {
        Methods methods = new Methods();

        if (accountId == null) {
            return false;
        }
        Optional<FRAccount> optional = accountRepo.findById(accountId);
        if (!optional.isPresent()) {
            return false;
        }
        if (methods.nullOrSpace(reason)) {
            reason = "";
        }

        FRAccount frAccount = optional.get();
        frAccount.setUpdateTime(methods.getTimeNow());
        frAccount.setStatus(Fix.ACC_NEW.index);
        frAccount.setNote(reason);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        return true;
    }

    public List<MdlAdmAccAdmGet> getListAdmin() {
        Base64.Encoder encoder = Base64.getEncoder();
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_ADM));
        List<MdlAdmAccAdmGet> admList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            MdlAdmAccAdmGet adm = new MdlAdmAccAdmGet();
            String avatar = null;
            if (frAccount.getAvatar() != null) {
                avatar = encoder.encodeToString(frAccount.getAvatar());
            }
            String editor = null;
            if (frAccount.getEditor() != null) {
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

    public List<MdlAdmAccMemGet> getListMember() {
        Base64.Encoder encoder = Base64.getEncoder();
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_MEM));
        List<MdlAdmAccMemGet> accList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            MdlAdmAccMemGet acc = new MdlAdmAccMemGet();

            String userImg = null;
            if (frAccount.getUserImage() != null) {
                userImg = encoder.encodeToString(frAccount.getAvatar());
            }
            String editor = null;
            if (frAccount.getEditor() != null) {
                editor = frAccount.getEditor().getPhone();
            }

            acc.setId(frAccount.getId());
            acc.setPhone(frAccount.getPhone());
            acc.setName(frAccount.getName());
            acc.setEmail(frAccount.getEmail());
            acc.setUserImg(userImg);
            acc.setDob(frAccount.getDateOfBirth());
            acc.setCreateTime(frAccount.getCreateTime());
            acc.setUpdateTime(frAccount.getUpdateTime());
            acc.setDeleteTime(frAccount.getDeleteTime());
            acc.setNote(frAccount.getNote());
            acc.setStatus(frAccount.getStatus());
            acc.setEditor(editor);
            accList.add(acc);
        }
        return accList;

    }


}
