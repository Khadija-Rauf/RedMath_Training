package com.redmath.assignment.account;

import com.redmath.assignment.balance.Balance;
import com.redmath.assignment.balance.BalanceService;
import org.springframework.cache.annotation.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService, ApplicationListener<AbstractAuthenticationEvent> {
    private final AccountRepository accountRepository;
    private final BalanceService balanceService;
    @Autowired
    public AccountService(AccountRepository accountRepository, BalanceService balanceService){
        this.accountRepository = accountRepository;
        this.balanceService = balanceService;
    }
    @Transactional
    public List<Account> findAll(){
        return accountRepository.findAll();
    }
    @Transactional
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
    @Transactional
    public Account create(Account account) {
        Balance balance = new Balance();
        if (account.getId() != null && accountRepository.existsById(account.getId())) {
            return null;
        }
        account.setRole("USER");
        if(account.getAddress() == null)    account.setAddress("Address: Not Available");
        Account saved = accountRepository.save(account);
        balance.setId(9L);
        balance.setDate(LocalDate.now());
        balance.setIndicator("0");
        balance.setAccount(account);
        balance.setAmount(0);
        balanceService.create(balance);
        return saved;
    }
    @Transactional
    public Account updateAccount(Long id, Account updateAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();
            existingAccount.setName(updateAccount.getName());
            existingAccount.setPassword(updateAccount.getPassword());
            existingAccount.setEmail(updateAccount.getEmail());
            existingAccount.setAddress(updateAccount.getAddress());
            existingAccount.setRole(updateAccount.getRole());
            return accountRepository.save(existingAccount);
        } else {
            return null;
        }
    }
    public boolean deleteAccount(Long id)
    {
        if(!accountRepository.existsById(id)){
            return false;
        }
        accountRepository.deleteById(id);
        return true;
    }
    //for security
    @Cacheable("account")
    public UserDetails loadUserByUsername(String jti, String userName) throws UsernameNotFoundException {
        return loadUserByUsername(userName);
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        Account account = accountRepository.findByName(userName);
        if(account == null){
            throw new UsernameNotFoundException("Invalid user: "+ userName);
        }
        return new org.springframework.security.core.userdetails.User(account.getName(), account.getPassword(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(account.getRole()));
    }
    //for account-holder
    @Transactional
    public Account findByAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        return accountRepository.findByName(authenticatedUsername);
    }
    @Transactional
    public Account findByUserName(String name) {
        return accountRepository.findByName(name);
    }
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent success) {
            System.out.println("security authentication successful for user:"+ success.getAuthentication().getName());
        } else if (event instanceof InteractiveAuthenticationSuccessEvent success) {
            System.out.println("security login successful for user: "+ success.getAuthentication().getName());
        } else if (event instanceof AbstractAuthenticationFailureEvent failure) {
            System.out.println("security authentication failed for user" + failure.getAuthentication().getName()+ "reason:" + String.valueOf(failure.getException()));
        } else {
            System.out.println("securityauthentication event for user:" + event.getAuthentication().getName()+ event);
        }
    }
}
