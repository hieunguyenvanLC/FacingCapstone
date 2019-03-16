package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Repo;
import capstone.fps.common.Validator;
import capstone.fps.entity.*;
import capstone.fps.model.Response;
import capstone.fps.model.account.*;
import capstone.fps.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    public Response<List<MdlAccount>> getListMember() {
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();
        Response<List<MdlAccount>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_MEM));
        List<MdlAccount> accList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            accList.add(mdlMemberBuilder.buildMemDetailAdm(frAccount));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, accList);
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

    // Web - Admin - Begin
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
        frAccount.setNatId(natId);
        frAccount.setNatDate(natDate);
        frAccount.setDateOfBirth(dob);
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setStatus(Fix.ACC_NEW.index);
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
        frAccount.setNatId(natId);
        frAccount.setNatDate(natDate);
        frAccount.setDateOfBirth(dob);
        frAccount.setUpdateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public  Response<List<MdlAccount>> getListAdmin() {
        MdlAdminBuilder mdlAdminBuilder = new MdlAdminBuilder();
        Response<List<MdlAccount>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_ADM));
        List<MdlAccount> accList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            accList.add(mdlAdminBuilder.buildAdmDetail(frAccount));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, accList);
        return response;
    }

    // Web - Admin - End

    // Web - Shipper - Begin
    public Response<MdlShipper> createShipper(String phone, String password, String name, Integer sourceId, long dob, String email, String note, Integer priceLevelId, MultipartFile userFace, String introduce, String natId, long natDate, String bikeRegId, long bikeRegDate, MultipartFile natFront, MultipartFile natBack, MultipartFile bikeRegFront, MultipartFile bikeRegBack) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        Repo repo = new Repo();
        MdlShipperBuilder shipperBuilder = new MdlShipperBuilder();
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        phone = valid.checkPhone(phone);
        if (phone == null) {
            response.setResponse(Response.STATUS_FAIL, "Please enter valid phone number.");
            return response;
        }
        name = valid.checkFullName(name);
        if (name == null) {
            response.setResponse(Response.STATUS_FAIL, "Please enter valid name");
            return response;
        }
        FRSource frSource = repo.getSource(sourceId, sourceRepo);
        if (frSource == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find source");
            return response;
        }
        FRPriceLevel frPriceLevel = repo.getPriceLevel(priceLevelId, priceLevelRepo);
        if (frPriceLevel == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find Price Level");
            return response;
        }

        FRAccount frAccount = new FRAccount();
        frAccount.setPhone(phone);
        frAccount.setRole(initRole(Fix.ROL_SHP));
        frAccount.setPassword(methods.hashPass(password));
        frAccount.setName(name);
        frAccount.setEmail(email);
        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setUserImage(methods.multipartToBytes(userFace));
        frAccount.setNatId(natId);
        frAccount.setNatDate(natDate);
        frAccount.setDateOfBirth(dob);
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote(valid.nullProof(note));
        frAccount.setStatus(Fix.ACC_NEW.index);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);

        FRShipper frShipper = new FRShipper();
        frShipper.setAccount(frAccount);
        frShipper.setBikeRegId(bikeRegId);
        frShipper.setBikeRegDate(bikeRegDate);
        frShipper.setIntroduce(valid.nullProof(introduce));
        frShipper.setNationalIdFrontImage(methods.multipartToBytes(natFront));
        frShipper.setNationalIdBackImage(methods.multipartToBytes(natBack));
        frShipper.setSumRevenue(0D);
        frShipper.setBikeRegFront(methods.multipartToBytes(bikeRegFront));
        frShipper.setBikeRegBack(methods.multipartToBytes(bikeRegBack));
        frShipper.setPriceLevel(frPriceLevel);
        frShipper.setSource(frSource);
        shipperRepo.save(frShipper);

        MdlShipper mdlShipper = shipperBuilder.buildFull(repo.getAccount(frAccount.getId(), accountRepo));
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlShipper);
        return response;
    }

    public Response<MdlShipper> updateShipper(int accId, String phone, Double sumRevenue, String name, Integer sourceId, Long dob, String email, String note, Integer priceLevelId, MultipartFile userFace, String introduce, Integer extraPoint, Integer reportPoint, Integer status, String natId, Long natDate, String bikeRegId, Long bikeRegDate, MultipartFile natFront, MultipartFile natBack, MultipartFile bikeRegFront, MultipartFile bikeRegBack) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        FRAccount currentUser = methods.getUser();
        MdlShipperBuilder shipperBuilder = new MdlShipperBuilder();
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FRAccount frAccount = repo.getAccount(accId, accountRepo);
        if (frAccount == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find account");
            return response;
        }

        phone = valid.checkPhone(phone);
        if (phone != null) {
            frAccount.setPhone(phone);
        }
        name = valid.checkFullName(name);
        if (name != null) {
            frAccount.setName(name);
        }
        if (email != null) {
            frAccount.setEmail(email);
        }
        if (extraPoint != null) {
            frAccount.setExtraPoint(extraPoint);
        }
        if (reportPoint != null) {
            frAccount.setReportPoint(reportPoint);
        }
        if (userFace != null) {
            frAccount.setUserImage(methods.multipartToBytes(userFace));
        }
        if (natId != null) {
            frAccount.setNatId(natId);
        }
        if (natDate != null) {
            frAccount.setNatDate(natDate);
        }
        if (dob != null) {
            frAccount.setDateOfBirth(dob);
        }
        if (email != null) {
            frAccount.setEmail(email);
        }
        if (note != null) {
            frAccount.setNote(valid.nullProof(note));
        }
        frAccount.setUpdateTime(time);
        frAccount.setStatus(valid.checkUpdateStatus(frAccount.getStatus(), status, Fix.ACC_STAT_LIST));
        frAccount.setEditor(currentUser);
        accountRepo.save(frAccount);


        FRShipper frShipper = frAccount.getShipper();
        if (bikeRegId != null) {
            frShipper.setBikeRegId(bikeRegId);
        }
        if (bikeRegDate != null) {
            frShipper.setBikeRegDate(bikeRegDate);
        }
        if (introduce != null) {
            frShipper.setIntroduce(introduce);
        }
        if (natFront != null) {
            frShipper.setNationalIdFrontImage(methods.multipartToBytes(natFront));
        }
        if (natBack != null) {
            frShipper.setNationalIdBackImage(methods.multipartToBytes(natBack));
        }
        if (sumRevenue != null) {
            frShipper.setSumRevenue(sumRevenue);
        }
        if (bikeRegFront != null) {
            frShipper.setBikeRegFront(methods.multipartToBytes(bikeRegFront));
        }
        if (bikeRegBack != null) {
            frShipper.setBikeRegBack(methods.multipartToBytes(bikeRegBack));
        }
        FRSource frSource = repo.getSource(sourceId, sourceRepo);
        if (frSource != null) {
            frShipper.setSource(frSource);
        }
        FRPriceLevel frPriceLevel = repo.getPriceLevel(priceLevelId, priceLevelRepo);
        if (frPriceLevel != null) {
            frShipper.setPriceLevel(frPriceLevel);
        }

        shipperRepo.save(frShipper);

        MdlShipper mdlShipper = shipperBuilder.buildFull(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlShipper);
        return response;
    }

    public Response<List<MdlShipper>> getShipperListAdm() {
        Response<List<MdlShipper>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlShipperBuilder shipperBuilder = new MdlShipperBuilder();
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_SHP));
        List<MdlShipper> accList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            accList.add(shipperBuilder.buildTableRowAdm(frAccount));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, accList);
        return response;
    }

    public Response<MdlShipper> getShipperDetailAdm(int accId) {
        Repo repo = new Repo();
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlShipperBuilder shipperBuilder = new MdlShipperBuilder();

        FRAccount frAccount = repo.getAccount(accId, accountRepo);
        if (frAccount == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find account");
            return response;
        }

        MdlShipper mdlShipper = shipperBuilder.buildFull(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlShipper);
        return response;
    }
    // Web - Shipper - End
}
