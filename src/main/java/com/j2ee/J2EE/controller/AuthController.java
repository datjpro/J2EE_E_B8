package com.j2ee.J2EE.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.j2ee.J2EE.model.Account;
import com.j2ee.J2EE.model.Role;
import com.j2ee.J2EE.repository.AccountRepository;
import com.j2ee.J2EE.repository.RoleRepository;
import com.j2ee.J2EE.web.AccountRegisterForm;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AccountRepository accountRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new AccountRegisterForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") AccountRegisterForm form,
                           BindingResult bindingResult) {
        if (accountRepository.existsByLoginName(form.getLoginName())) {
            bindingResult.addError(new FieldError("registerForm", "loginName", "Login name already exists"));
        }
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        Account account = new Account();
        account.setLoginName(form.getLoginName());
        account.setPassword(passwordEncoder.encode(form.getPassword()));
        account.getRoles().add(userRole);
        accountRepository.save(account);
        return "redirect:/login?registered";
    }
}
