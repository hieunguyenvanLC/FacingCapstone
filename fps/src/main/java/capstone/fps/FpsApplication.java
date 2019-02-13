package capstone.fps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FpsApplication {

    public static void main(String[] args) {
//        BCrypt.gensalt();
//        System.out.println( BCrypt.hashpw());
        SpringApplication.run(FpsApplication.class, args);
    }

}

