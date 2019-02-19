package capstone.fps.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRRole;
import capstone.fps.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class LoginService implements UserDetailsService{

    private AccountRepository accountRepository;

    public LoginService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public FRAccount findByPhone(Double phone) {
        Optional<FRAccount> optional = accountRepository.findByPhone(phone);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from the database (throw exception if not found)
        Optional<FRAccount> optional = accountRepository.findByPhone(Double.valueOf(username));
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optional.get();
    }
}
