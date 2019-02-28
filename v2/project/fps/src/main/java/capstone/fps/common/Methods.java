package capstone.fps.common;

import capstone.fps.entity.FRAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

    public String hashPass(String input){
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

}
