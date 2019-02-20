package capstone.fps;

import capstone.fps.common.Methods;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication//(scanBasePackages = "capstone.fps")
public class FpsApplication {

    public static void main(String[] args) {
        Methods methods = new Methods();
        System.out.println(methods.getTimeNow());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s = bCryptPasswordEncoder.encode("abc");
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw("zzz", salt);
        boolean b = bCryptPasswordEncoder.matches("zzz", password);

        System.out.println(password);
        System.out.println(b);
        System.out.println(s);

        SpringApplication.run(FpsApplication.class, args);
    }

}

