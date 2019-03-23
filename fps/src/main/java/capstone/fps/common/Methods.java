package capstone.fps.common;

import capstone.fps.entity.FRAccount;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        return "data:image/jpg;base64," + StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false));
    }

    public byte[] base64ToBytes(String base64) {
        if (base64 == null) {
            return null;
        }
        return Base64.decodeBase64(base64);
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


    public double caculateShpEarn(double buyerLon, double buyerLat, double storeLon, double storeLat, double shipperLon, double shipperLat) {

        // GG API
        double dis = Math.sqrt((buyerLon - storeLon) * (buyerLon - storeLon) + (buyerLat - storeLat) * (buyerLat - storeLat)) * 40000 / 360;


        double price = 14000;
        int kms = (int) Math.ceil(dis);
        if (kms > 0) {
            price += kms * 1000;
        }
        kms -= 5;
        if (kms > 0) {
            price += kms * 1000;
        }
        kms -= 5;
        if (kms > 0) {
            price += kms * 1000;
        }
        return price;
    }

    // Converting InputStream to String
    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}
