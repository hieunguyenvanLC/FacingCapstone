package capstone.fps.service;

import java.util.Optional;

import capstone.fps.entity.FRAccount;
import capstone.fps.repository.AccountRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service

public class LoginService implements UserDetailsService {

    private AccountRepo accountRepository;

    public LoginService(AccountRepo accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<FRAccount> optional = accountRepository.findByPhone(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optional.get();
    }
}
