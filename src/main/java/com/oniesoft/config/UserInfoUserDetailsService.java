package com.oniesoft.config;
import com.oniesoft.model.Register;
import com.oniesoft.model.UserInfoUserDetails;
import com.oniesoft.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private RegisterRepo repository;

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {

        Optional<Register> userInfo = Optional.ofNullable(repository.findByEmpId(empId));
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + empId));

    }
}

