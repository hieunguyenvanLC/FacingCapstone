package capstone.fps.common;

import capstone.fps.entity.FRAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


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

}
