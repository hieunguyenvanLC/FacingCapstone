package capstone.fps.service;

import capstone.fps.common.ConstantList;
import capstone.fps.common.Methods;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRRole;
import capstone.fps.model.MdlAccEdt;
import capstone.fps.repository.AccountRepository;
import capstone.fps.repository.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Optional;

@Service
public class AccountService {

    private ServletContext context;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;

    public final FRRole roleAdm;
    public final FRRole roleMem;
    public final FRRole roleShp;

    public AccountService(ServletContext context, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.context = context;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;

        this.roleAdm = initRole(ConstantList.ROL_ADM);
        this.roleMem = initRole(ConstantList.ROL_MEM);
        this.roleShp = initRole(ConstantList.ROL_SHP);
    }

    private FRRole initRole(String name) {
        Optional<FRRole> optional;
        optional = roleRepository.findByName(name);
        if (optional.isPresent()) {
            return optional.get();
        }
        FRRole role = new FRRole();
        role.setName(name);
        roleRepository.save(role);
        optional = roleRepository.findByName(name);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public boolean updateProfile(MdlAccEdt mdlAccEdt) {
        Methods methods = new Methods();
        long date = methods.getTimeNow();

        return false;
    }

    public boolean createAccountMember(String phone, String pass, String name) {
        Methods methods = new Methods();
        long timeNow = methods.getTimeNow();
        FRAccount frAccount = new FRAccount();

        frAccount.setPhone(phone);
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw(pass, salt);
        frAccount.setPassword(password);
        frAccount.setName(name);

        frAccount.setExtraPoint(0);
        frAccount.setReportPoint(0);
        frAccount.setCreateTime(timeNow);
        frAccount.setUpdateTime(timeNow);
        frAccount.setRole(roleMem);
        frAccount.setStatus(ConstantList.ACC_NEW);
        accountRepository.save(frAccount);
        return true;
    }

    public boolean banAccount(Integer accountId, String reason) {
        if (accountId == null) {
            return false;
        }
        Optional<FRAccount> optional = accountRepository.findById(accountId);
        if (!optional.isPresent()) {
            return false;
        }
        FRAccount frAccount = optional.get();
        Methods methods = new Methods();
        long timeNow = methods.getTimeNow();
        frAccount.setUpdateTime(timeNow);
        frAccount.setDeleteTime(timeNow);
        frAccount.setStatus(ConstantList.ACC_BAN);
        frAccount.setNote(reason);
        return true;
    }
}
