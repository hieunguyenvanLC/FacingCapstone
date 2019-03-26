package capstone.fps.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandPrompt {

    private List<String> result;
    private List<String> error;

    public void execute(String command) {
        result = new ArrayList<>();
        error = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec(command);
            System.out.println(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            // read the output from the command
            String line;
            while ((line = stdInput.readLine()) != null) {
                result.add(line);
            }
            // read any errors from the attempted command
            while ((line = stdError.readLine()) != null) {
                error.add(line);
            }
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }
    }

    public List<String> getResult() {
        return result;
    }

    public List<String> getError() {
        return error;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Here is the output of the command (if any):\n\n");
        for (String line : result) {
            stringBuilder.append(line).append("\n");
        }
        stringBuilder.append("Here is the standard error of the command (if any):\n\n");
        for (String line : error) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }
}
