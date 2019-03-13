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
        return str.trim().isEmpty();
    }


//    public String handleImage(MultipartFile image) {
//        if (image != null) {
//            String fileName = image.getOriginalFilename();
////            Files.createDirectories(rootLocation);
//            try {
//                Files.copy(image.getInputStream(), Fix.IMG_DIR_PATH.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//                return fileName;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public String bytesToBase64(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return "data:image/png;base64," + StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false));
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


    public double caculateShpEarn(double buyerLon, double buyerLat, double storeLon, double storeLat) {
        double price = 14000;
        double dis = ((buyerLon - storeLon) * (buyerLon - storeLon) + (buyerLat - storeLat) * (buyerLat - storeLat)) * 40000 / 360;
        if (dis > 0) {
            price += dis * 1000;
        }
        dis -= 5;
        if (dis > 0) {
            price += dis * 1000;
        }
        dis -= 5;
        if (dis > 0) {
            price += dis * 1000;
        }
        return price;
    }

}
