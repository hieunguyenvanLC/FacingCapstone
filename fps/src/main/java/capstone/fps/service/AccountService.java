package capstone.fps.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class AccountService {

    public static long imageNumber = 0l;
    private String absolutePath;

    private ServletContext context;

    public AccountService(ServletContext context) {
        this.context = context;
        this.absolutePath = context.getRealPath("resources/upload");
    }

    public String handleImage(MultipartFile image) throws IOException {
        if (image != null) {
            String fileName = String.format("i%015d", imageNumber);
            imageNumber++;
//
//            Files.copy(image.getInputStream(),);
//
//
//            LOGGER.info("file name:" + fileName);
        }
        return "";
    }


    public boolean createAccount(String profileStr){
        return false;
    }

    public boolean updateProfile(String profileStr){
        return false;
    }
}
