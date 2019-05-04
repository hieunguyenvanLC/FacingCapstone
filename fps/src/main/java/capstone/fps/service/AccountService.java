package capstone.fps.service;

import capstone.fps.common.*;
import capstone.fps.entity.*;
import capstone.fps.model.Response;
import capstone.fps.model.account.*;
import capstone.fps.repository.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepo accountRepo;
    private RoleRepo roleRepo;
    private SourceRepo sourceRepo;
    private PriceLevelRepo priceLevelRepo;
    private ShipperRepo shipperRepo;
    private ReceiveMemberRepo receiveMemberRepo;
    private PaymentTypeRepo paymentTypeRepo;
    private PaymentInfoRepo paymentInfoRepo;


    public AccountService(AccountRepo accountRepo, RoleRepo roleRepo, SourceRepo sourceRepo, PriceLevelRepo priceLevelRepo, ShipperRepo shipperRepo, ReceiveMemberRepo receiveMemberRepo, PaymentTypeRepo paymentTypeRepo, PaymentInfoRepo paymentInfoRepo) {
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
        this.sourceRepo = sourceRepo;
        this.priceLevelRepo = priceLevelRepo;
        this.shipperRepo = shipperRepo;
        this.receiveMemberRepo = receiveMemberRepo;
        this.paymentTypeRepo = paymentTypeRepo;
        this.paymentInfoRepo = paymentInfoRepo;
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


    private FRPaymentType initPaymentType(String type) {
        Optional<FRPaymentType> optional;
        optional = paymentTypeRepo.findByType(type);
        if (optional.isPresent()) {
            return optional.get();
        }
        FRPaymentType paymentType = new FRPaymentType();
        paymentType.setName("paypal");
        paymentTypeRepo.save(paymentType);
        optional = paymentTypeRepo.findByType(type);
        return optional.orElse(null);
    }


//    public boolean banAccount(Integer accountId, String reason) {
//        Methods methods = new Methods();
//
//        if (accountId == null) {
//            return false;
//        }
//        if (methods.getUser().getId().equals(accountId)) {
//            return false;
//        }
//        Optional<FRAccount> optional = accountRepo.findById(accountId);
//        if (!optional.isPresent()) {
//            return false;
//        }
//        if (methods.nullOrSpace(reason)) {
//            reason = "";
//        }
//
//        FRAccount frAccount = optional.get();
//        frAccount.setDeleteTime(methods.getTimeNow());
//        frAccount.setStatus(Fix.ACC_BAN.index);
//        frAccount.setNote(reason);
//        frAccount.setEditor(methods.getUser());
//        accountRepo.save(frAccount);
//        return true;
//    }
//
//    public boolean activateAccount(Integer accountId, String reason) {
//        Methods methods = new Methods();
//
//        if (accountId == null) {
//            return false;
//        }
//        Optional<FRAccount> optional = accountRepo.findById(accountId);
//        if (!optional.isPresent()) {
//            return false;
//        }
//        if (methods.nullOrSpace(reason)) {
//            reason = "";
//        }
//
//        FRAccount frAccount = optional.get();
//        frAccount.setUpdateTime(methods.getTimeNow());
//        frAccount.setStatus(Fix.ACC_NEW.index);
//        frAccount.setNote(reason);
//        frAccount.setEditor(methods.getUser());
//        accountRepo.save(frAccount);
//        return true;
//    }

    // Web Admin - Member - Begin
    public Response<List<MdlMember>> getMemberList() {
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();
        Response<List<MdlMember>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_MEM));
        List<MdlMember> accList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            accList.add(mdlMemberBuilder.buildMemEntryAdm(frAccount));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, accList);
        return response;
    }

    public Response<MdlMember> getMemberDetailAdm(int accId) {
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount frAccount = accountRepo.findById(accId).orElse(null);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlMemberBuilder.buildMemDetailAdm(frAccount, receiveMemberRepo));
        return response;
    }

    public Response<MdlMember> updateMemberAdm(int accId, String name, String email, Long dob, String note, Integer status) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FRAccount frAccount = accountRepo.findById(accId).orElse(null);
        if (frAccount == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find account");
            return response;
        }
        if (!frAccount.getRole().getName().equals(Fix.ROL_MEM)) {
            response.setResponse(Response.STATUS_FAIL, "Not a member");
            return response;
        }
        if (name != null) {
            frAccount.setName(name.trim());
        }
        if (email != null) {
            frAccount.setEmail(email.trim());
        }
        if (note != null) {
            frAccount.setNote(note.trim());
        }
        if (dob != null) {
            frAccount.setDob(dob);
        }
        frAccount.setUpdateTime(methods.getTimeNow());
        frAccount.setStatus(valid.checkUpdateStatus(frAccount.getStatus(), status, Fix.ACC_STAT_LIST));
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlMemberBuilder.buildMemDetailAdm(frAccount, receiveMemberRepo));
        return response;
    }
    // Web Admin - Member - End


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
        frAccount.setDob(dob);
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote(note);
        frAccount.setStatus(Fix.ACC_NEW.index);
        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public Response editAccountAdmin(int accId, String name, String email, String natId, Long natDate, Long dob, String note) {
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
            response.setResponse(Response.STATUS_FAIL, "Not a admin");
            return response;
        }

        name = valid.checkFullName(name);
        if (name != null) {
            frAccount.setName(name);
        }
        if (email != null) {
            frAccount.setEmail(email);
        }
        natId = valid.checkNumber(natId);
        if (natId != null) {
            frAccount.setNatId(natId);
        }
        if (natDate != null) {
            frAccount.setNatDate(natDate);
        }
        if (note != null) {
            frAccount.setNote(note);
        }
        if (methods.getAge(dob) >= 18) {
            frAccount.setDob(dob);
        }
        frAccount.setUpdateTime(methods.getTimeNow());

        frAccount.setEditor(methods.getUser());
        accountRepo.save(frAccount);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public Response<List<MdlAdmin>> getListAdmin() {
        MdlAdminBuilder mdlAdminBuilder = new MdlAdminBuilder();
        Response<List<MdlAdmin>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<FRAccount> frAccountList = accountRepo.findAllByRole(initRole(Fix.ROL_ADM));
        List<MdlAdmin> accList = new ArrayList<>();
        for (FRAccount frAccount : frAccountList) {
            accList.add(mdlAdminBuilder.buildAdmDetail(frAccount));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, accList);
        return response;
    }

    public Response<MdlAdmin> getAdminProfileAdm() {
        Methods methods = new Methods();
        FRAccount frAccount = methods.getUser();
        Response<MdlAdmin> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlAdminBuilder mdlAdminBuilder = new MdlAdminBuilder();
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlAdminBuilder.buildAdmProfile(frAccount));
        return response;
    }

    public Response<MdlAdmin> updateProfileAdm(String password, String name, MultipartFile avatar) {
        Methods methods = new Methods();
        FRAccount frAccount = methods.getUser();
        Response<MdlAdmin> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        if (password != null) {
            frAccount.setPassword(methods.hashPass(password));
        }
        if (!methods.nullOrSpace(name)) {
            frAccount.setName(name.trim());
        }
        if (avatar != null) {
            frAccount.setAvatar(methods.multipartToBytes(avatar));
        }
        accountRepo.save(frAccount);
        MdlAdminBuilder mdlAdminBuilder = new MdlAdminBuilder();
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlAdminBuilder.buildAdmProfile(frAccount));
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
        frAccount.setDob(dob);
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setUpdateTime(null);
        frAccount.setDeleteTime(null);
        frAccount.setNote(valid.nullProof(note));
        frAccount.setStatus(Fix.ACC_NEW.index);
        frAccount.setEditor(methods.getUser());
        frAccount.setAvatar(null);
        accountRepo.save(frAccount);

        FRShipper frShipper = new FRShipper();
        frShipper.setIntroduce(valid.nullProof(introduce));
        frShipper.setSumRevenue(0d);
        frShipper.setNatIdFrontImage(methods.multipartToBytes(natFront));
        frShipper.setNatIdBackImage(methods.multipartToBytes(natBack));
        frShipper.setBikeRegId(bikeRegId);
        frShipper.setBikeRegDate(bikeRegDate);
        frShipper.setBikeRegFront(methods.multipartToBytes(bikeRegFront));
        frShipper.setBikeRegBack(methods.multipartToBytes(bikeRegBack));
        frShipper.setAccount(frAccount);
        frShipper.setSource(frSource);
        frShipper.setPriceLevel(frPriceLevel);
        frShipper.setRating(0d);
        frShipper.setRatingCount(0);
        frShipper.setOrderCount(0);
        shipperRepo.save(frShipper);

        MdlShipper mdlShipper = shipperBuilder.buildFull(repo.getAccount(frAccount.getId(), accountRepo), shipperRepo.findById(frShipper.getId()).orElse(null));
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
            frAccount.setDob(dob);
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
            frShipper.setNatIdFrontImage(methods.multipartToBytes(natFront));
        }
        if (natBack != null) {
            frShipper.setNatIdBackImage(methods.multipartToBytes(natBack));
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

        MdlShipper mdlShipper = shipperBuilder.buildFull(frAccount, frShipper);
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

        MdlShipper mdlShipper = shipperBuilder.buildFull(frAccount, frAccount.getShipper());
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlShipper);
        return response;
    }
    // Web - Shipper - End


    // Mobile Member - Register - Begin
    public Response createAccountMember(String phone, String pass, String name, String face1, String face2, String face3, String payUsername, String payPassword) {
        Methods methods = new Methods();
        Validator valid = new Validator();
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        phone = valid.checkPhone(phone);
        if (phone == null) {
            response.setResponse(Response.STATUS_FAIL, "Please enter valid phone number.");
            return response;
        }
        byte[] faceBytes1 = methods.base64ToBytes(face1);
        byte[] faceBytes2 = methods.base64ToBytes(face2);
        byte[] faceBytes3 = methods.base64ToBytes(face3);

        FRAccount frAccount = new FRAccount();
        frAccount.setPhone(phone);
        frAccount.setRole(initRole(Fix.ROL_MEM));
        frAccount.setPassword(methods.hashPass(pass));
        frAccount.setName(name);
        frAccount.setEmail("");
        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setUserImage(null);
        frAccount.setNatId(null);
        frAccount.setNatDate(null);
        frAccount.setDob(null);
        frAccount.setCreateTime(methods.getTimeNow());
        frAccount.setNote("");
        frAccount.setStatus(Fix.ACC_CHK.index);
        frAccount.setEditor(null);
        frAccount.setAvatar(faceBytes1);
        accountRepo.save(frAccount);


        frAccount = accountRepo.findById(frAccount.getId()).orElse(null);
        FRReceiveMember frReceiveMember = new FRReceiveMember();
        frReceiveMember.setName("default");
        frReceiveMember.setFace1(faceBytes1);
        frReceiveMember.setFace2(faceBytes2);
        frReceiveMember.setFace3(faceBytes3);
        frReceiveMember.setAccount(frAccount);
        receiveMemberRepo.save(frReceiveMember);


        FRPaymentInformation frPaymentInformation = new FRPaymentInformation();
        frPaymentInformation.setPaymentType(initPaymentType("sale"));
        frPaymentInformation.setAccount(frAccount);
        frPaymentInformation.setUsername(payUsername);
        frPaymentInformation.setPassword(methods.basicEncrypt(payPassword));
        paymentInfoRepo.save(frPaymentInformation);

        // training in python here
//        Map<Integer, FaceTrain> trainQueue = AppData.getTrainQueue();
//        int revMemId = frReceiveMember.getId();
//        trainQueue.put(revMemId, new FaceTrain(true));
        int revMemId = frReceiveMember.getId();
        String dirName = createPythonDir(revMemId);
        putFaceToPythonDir(dirName, faceBytes1);
        putFaceToPythonDir(dirName, faceBytes2);
        putFaceToPythonDir(dirName, faceBytes3);
        pythonCrop();
        deletePythonDir(dirName);


//        trainingFaceRecognise(frReceiveMember);

        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    private String createPythonDir(int revMemId) {
        String folderName = Fix.FACE_FOLDER + "fps" + revMemId;
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return folderName;
    }

    private boolean deletePythonDir(String dirName) {
        try {
            new Methods().deleteDirectoryWalkTree(dirName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean putFaceToPythonDir(String folderName, byte[] faceByte) {
        String jpgName = "p" + new Date().getTime() + ".jpg";
        File jpgFile = new File(folderName + "/" + jpgName);
        ByteArrayInputStream bis = new ByteArrayInputStream(faceByte);
        try {
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, Fix.DEF_IMG_TYPE, jpgFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private CommandPrompt pythonCrop() {
        CommandPrompt commandPrompt = new CommandPrompt();
        String cropCenter = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/preprocess.py --input-dir /docker/data/fps --output-dir /docker/output/fps --crop-dim 180";
        commandPrompt.execute(cropCenter);
        return commandPrompt;
    }

    private CommandPrompt pythonTrain() {
        CommandPrompt commandPrompt = new CommandPrompt();
        String trainingAI = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/train_classifier.py --input-dir /docker/output/fps --model-path /docker/etc/20170511-185253/20170511-185253.pb --classifier-path /docker/output/classifier.pkl --num-threads 16 --num-epochs 25 --min-num-images-per-class 5 --is-train";
        commandPrompt.execute(trainingAI);
        return commandPrompt;
    }

//    public void trainingFaceRecognise(FRReceiveMember frReceiveMember) {
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Methods methods = new Methods();
//                String folderName = Fix.FACE_FOLDER + "fps" + frReceiveMember.getId();
//                String jpgName = "p" + methods.getTimeNow() + "." + "jpg";
//                File directory = new File(folderName);
//                if (!directory.exists()) {
//                    directory.mkdir();
//                }
//                File jpgFile = new File(folderName + "/" + jpgName);
//                ByteArrayInputStream bis = new ByteArrayInputStream(frReceiveMember.getFace());
//                try {
//                    BufferedImage bufferedImage = ImageIO.read(bis);
//                    ImageIO.write(bufferedImage, Fix.DEF_IMG_TYPE, jpgFile);
//                    CommandPrompt commandPrompt = new CommandPrompt();
//                    String cutAndRecenter = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/preprocess.py --input-dir /docker/data/fps --output-dir /docker/output/fps --crop-dim 180";
//                    commandPrompt.execute(cutAndRecenter);
//                    String trainingAI = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/train_classifier.py --input-dir /docker/output/fps --model-path /docker/etc/20170511-185253/20170511-185253.pb --classifier-path /docker/output/classifier.pkl --num-threads 16 --num-epochs 25 --min-num-images-per-class 5 --is-train";
//                    commandPrompt.execute(trainingAI);
//                    System.out.println(commandPrompt);
//                    // delete folder generated by Python
////                    methods.deleteDirectoryWalkTree(Fix.CROP_FACE_FOLDER + "fps" + frReceiveMember.getId());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                // delete folder generated by Java
//                try {
//                    methods.deleteDirectoryWalkTree(folderName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();
//    }
    // Mobile Member - Register - End


    // Mobile Member - Profile - Begin
    public Response<MdlMember> getMemberDetailMem() {
        Methods methods = new Methods();
        FRAccount currentUser = methods.getUser();
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();

        MdlMember mdlMember = mdlMemberBuilder.buildMemDetailMem(currentUser, receiveMemberRepo);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlMember);
        return response;
    }

    public Response<MdlMember> updateMemberDetailMem(String name, String email, Long dob) {
        Methods methods = new Methods();
        FRAccount currentUser = methods.getUser();
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        if (name != null) {
            currentUser.setName(name.trim());
        }
        if (email != null) {
            currentUser.setEmail(email.trim());
        }
        if (dob != null) {
            currentUser.setDob(dob);
        }
        currentUser.setUpdateTime(methods.getTimeNow());
        currentUser.setEditor(currentUser);
        accountRepo.save(currentUser);
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();
        MdlMember mdlMember = mdlMemberBuilder.buildMemDetailMem(currentUser, receiveMemberRepo);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlMember);
        return response;
    }

    private void setRevMemFace(Methods methods, FRReceiveMember frReceiveMember, String face, int pointer, String dirName) {
        if (face != null) {
            byte[] faceBytes = methods.base64ToBytes(face);
            switch (pointer) {
                case 1:
                    frReceiveMember.setFace1(faceBytes);
                    break;
                case 2:
                    frReceiveMember.setFace2(faceBytes);
                    break;
                case 3:
                    frReceiveMember.setFace3(faceBytes);
                    break;
            }
            putFaceToPythonDir(dirName, faceBytes);
        }
    }

    public Response<String> updateMemberFaceMem(Integer revMemId, String revMemName, String face1, String face2, String face3) {
        Methods methods = new Methods();
        Repo repo = new Repo();
        FRAccount currentUser = methods.getUser();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FRReceiveMember frReceiveMember = repo.getReceiveMember(revMemId, receiveMemberRepo);
        if (frReceiveMember == null) {
            if (revMemName == null) {
                response.setResponse(Response.STATUS_FAIL, "Name is null");
                return response;
            }
            if (face1 == null || face2 == null || face3 == null) {
                response.setResponse(Response.STATUS_FAIL, "Need 3 images");
                return response;
            }
            frReceiveMember = new FRReceiveMember();
            frReceiveMember.setAccount(currentUser);
            frReceiveMember.setName(revMemName);
            byte[] faceBytes1 = methods.base64ToBytes(face1);
            byte[] faceBytes2 = methods.base64ToBytes(face2);
            byte[] faceBytes3 = methods.base64ToBytes(face3);
            frReceiveMember.setFace1(faceBytes1);
            frReceiveMember.setFace2(faceBytes2);
            frReceiveMember.setFace3(faceBytes3);
            receiveMemberRepo.save(frReceiveMember);

            // training in python here
//            Map<Integer, FaceTrain> trainQueue = AppData.getTrainQueue();
//            revMemId = frReceiveMember.getId();
//            FaceTrain faceTrain = trainQueue.get(revMemId);

            revMemId = frReceiveMember.getId();
            String dirName = createPythonDir(revMemId);
            putFaceToPythonDir(dirName, faceBytes1);
            putFaceToPythonDir(dirName, faceBytes2);
            putFaceToPythonDir(dirName, faceBytes3);
            pythonCrop();
            deletePythonDir(dirName);

            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            return response;
        }

        if (revMemName != null) {
            frReceiveMember.setName(revMemName);
        }
        // train here
        String dirName = createPythonDir(revMemId);
        setRevMemFace(methods, frReceiveMember, face1, 1, dirName);
        setRevMemFace(methods, frReceiveMember, face2, 2, dirName);
        setRevMemFace(methods, frReceiveMember, face3, 3, dirName);
        pythonCrop();
        deletePythonDir(dirName);
        receiveMemberRepo.save(frReceiveMember);

        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }


    public Response<MdlMember> getAvatarMem() {
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Methods methods = new Methods();
        FRAccount currentUser = methods.getUser();
        MdlMemberBuilder mdlMemberBuilder = new MdlMemberBuilder();

        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlMemberBuilder.buildMemAvatar(currentUser));
        return response;
    }


    public Response<String> updateAvatar(String avatar) {
        Methods methods = new Methods();
        FRAccount currentUser = methods.getUser();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        if (avatar == null) {
            response.setResponse(Response.STATUS_FAIL, "Avatar is null");
            return response;
        }
        currentUser.setAvatar(methods.base64ToBytes(avatar));
        accountRepo.save(currentUser);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }
    // Mobile Member - Profile - End


    // Mobile Shipper - Profile - Begin
    public Response<MdlShipper> getShipperDetailShp() {
        Methods methods = new Methods();
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlShipperBuilder shipperBuilder = new MdlShipperBuilder();

        FRAccount frAccount = methods.getUser();
        if (frAccount == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find account");
            return response;
        }

        MdlShipper mdlShipper = shipperBuilder.buildFull(frAccount, frAccount.getShipper());
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlShipper);
        return response;
    }


    // Mobile Shipper - Profile - End

}
