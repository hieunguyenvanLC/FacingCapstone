package capstone.fps.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletContext;
import java.util.Collection;
import java.util.List;

public final class Methods {

    public Methods() {
    }

    public java.sql.Date getSqlNow() {
        return new java.sql.Date(new java.util.Date().getTime());
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> roles = (List<? extends GrantedAuthority>) authentication.getAuthorities();
        return roles.get(0).getAuthority();
    }

}
