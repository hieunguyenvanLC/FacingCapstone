package capstone.fps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "capstone.fps")
public class FpsApplication {

    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String s = bCryptPasswordEncoder.encode("abc");
//        String salt = BCrypt.gensalt();
//        String password = BCrypt.hashpw("zxc", salt);
//        boolean b = bCryptPasswordEncoder.matches("zxc", password);
//        System.out.println(password);
//        System.out.println(b);
//        System.out.println(s);
        SpringApplication.run(FpsApplication.class, args);
    }

}

