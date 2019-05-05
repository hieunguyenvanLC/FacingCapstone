package capstone.fps;

import capstone.fps.common.CommandPrompt;
import capstone.fps.common.Methods;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication//(scanBasePackages = "capstone.fps")
public class FpsApplication {


    public static void main(String[] args) {
        SpringApplication.run(FpsApplication.class, args);
        Methods methods = new Methods();
        while (true) {
            try {
                Thread.sleep(methods.getTomorrow() - methods.getTimeNow());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pythonTrain();
        }
    }


    private static CommandPrompt pythonTrain() {
        CommandPrompt commandPrompt = new CommandPrompt();
        String trainingAI = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/train_classifier.py --input-dir /docker/output/fps --model-path /docker/etc/20170511-185253/20170511-185253.pb --classifier-path /docker/output/classifier.pkl --num-threads 16 --num-epochs 25 --min-num-images-per-class 5 --is-train";
        commandPrompt.execute(trainingAI);
        return commandPrompt;
    }
}

