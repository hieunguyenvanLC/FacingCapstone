package capstone.fps.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service

public class LoginService {

//    private AccountRepository accountRepository;

//    public LoginService(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }

    //    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<FRAccount> optional = accountRepository.findByAccountName(username);
//        if (!optional.isPresent()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        FRAccount user = optional.get();
//                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Set<FRRole> roles = new HashSet<>();
//        roles.add(user.getRole());
//        for (FRRole role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), grantedAuthorities);
//    }


    public boolean checkLogin(Double phone, String password) {
        if (phone == null || password == null) {
            return false;
        }
//        Optional<FRAccount> accountOptional = accountRepository.findByPhoneAndPassword(phone, password);
//        return accountOptional.isPresent();
        return true;
    }

}
