package capstone.fps.common;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FROrder;
import capstone.fps.entity.FRStore;
import capstone.fps.repository.AccountRepo;
import capstone.fps.repository.DistrictRepo;
import capstone.fps.repository.OrderRepo;
import capstone.fps.repository.StoreRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public final class Methods {

    public Methods() {
    }

    public long getTimeNow() {
        return new java.util.Date().getTime();
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> roles = (List<? extends GrantedAuthority>) authentication.getAuthorities();
        return roles.get(0).getAuthority();
    }

    public FRAccount getUser() {
        return (FRAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String hashPass(String input) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(input, salt);
    }

    public int getAge(long time) {
        Calendar firstCal = GregorianCalendar.getInstance();
        Calendar secondCal = GregorianCalendar.getInstance();
        firstCal.setTimeInMillis(time);
        secondCal.setTime(new Date());
        secondCal.add(Calendar.DAY_OF_YEAR, 1 - firstCal.get(Calendar.DAY_OF_YEAR));
        return secondCal.get(Calendar.YEAR) - firstCal.get(Calendar.YEAR);
    }


    public boolean nullOrSpace(String str) {
        if (str == null) {
            return true;
        }
        if (str.isEmpty()) {
            return true;
        }
        if (str.trim().isEmpty()) {
            return true;
        }
        return false;
    }


    public String handleImage(MultipartFile image) {
        if (image != null) {
            String fileName = image.getOriginalFilename();
//            Files.createDirectories(rootLocation);
            try {
                Files.copy(image.getInputStream(), Fix.IMG_DIR_PATH.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String bytesToBase64(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false)));
        return sb.toString();
    }

    public byte[] multipartToBytes(MultipartFile input) {
        if (input != null) {
            try {
                return input.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public FRDistrict getDIst(Integer distId, DistrictRepo districtRepository) {
        if (distId == null) {
            return null;
        }
        Optional<FRDistrict> optional = districtRepository.findById(distId);
        return optional.orElse(null);
    }

    public FRStore getStore(Integer storeId, StoreRepo storeRepository) {
        if (storeId == null) {
            return null;
        }
        Optional<FRStore> optional = storeRepository.findById(storeId);
        return optional.orElse(null);
    }

    public FROrder getOrder(Integer orderId, OrderRepo orderRepository) {
        if (orderId == null) {
            return null;
        }
        Optional<FROrder> optional = orderRepository.findById(orderId);
        return optional.orElse(null);
    }

    public FRAccount getAccount(Integer accId, AccountRepo accountRepository) {
        if (accId == null) {
            return null;
        }
        Optional<FRAccount> optional = accountRepository.findById(accId);
        return optional.orElse(null);
    }

    public FRAccount getAccountByPhone(String phone, AccountRepo accountRepository) {
        if (nullOrSpace(phone)) {
            return null;
        }
        Optional<FRAccount> optional = accountRepository.findByPhone(phone);
        return optional.orElse(null);
    }
}
