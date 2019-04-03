package capstone.fps.common;

import capstone.fps.entity.FRAccount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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

    public int getAge(Long time) {
        if (time == null) {
            return -1;
        }
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


//    public double caculateShpEarn(double buyerLon, double buyerLat, double storeLon, double storeLat, double shipperLon, double shipperLat) {
//        // GG API
//        double dis = Math.sqrt((buyerLon - storeLon) * (buyerLon - storeLon) + (buyerLat - storeLat) * (buyerLat - storeLat)) * 40000 / 360;
//        double price = 14000;
//        int kms = (int) Math.ceil(dis);
//        if (kms > 0) {
//            price += kms * 1000;
//        }
//        kms -= 5;
//        if (kms > 0) {
//            price += kms * 1000;
//        }
//        kms -= 5;
//        if (kms > 0) {
//            price += kms * 1000;
//        }
//        return price;
//    }


    public double calculateShpEarn(double dis) {
        double price = 14000d;
        if (dis > 0) {
            price += dis * 1000d;
        }
        dis -= 5;
        if (dis > 0) {
            price += dis * 1000d;
        }
        dis -= 5;
        if (dis > 0) {
            price += dis * 1000d;
        }
        return Math.ceil(price / 1000d) * 1000d;
    }

    // Converting InputStream to String
    public String readStream(InputStream in) {
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

    public void deleteDirectoryWalkTree(String folderName) throws IOException {
        Path deleteDirPath = Paths.get(folderName).toAbsolutePath().normalize();
        FileVisitor visitor = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(deleteDirPath, visitor);
    }


    public String sendHttpRequest(String url, Map<String, String> header, JsonObject body) {
        URL urlObj;
        HttpURLConnection urlConnection;

        String method = "POST";
        try {
            urlObj = new URL(url);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            for (Map.Entry<String, String> entry : header.entrySet()) {
                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            os.write(body.toString().getBytes("UTF-8"));
            os.close();

            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//
//            }
            return readStream(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
