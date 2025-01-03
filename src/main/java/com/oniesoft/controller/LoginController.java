package com.oniesoft.controller;

import com.oniesoft.jwtresponse.AuthenticationResponse;
import com.oniesoft.model.Register;
import com.oniesoft.repository.RegisterRepo;
import com.oniesoft.serviceimpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login/v1")
public class LoginController {
    @Autowired
    RegisterRepo registerRepo;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateAndGetToken(@RequestBody Register authRequest) {
        String email = authRequest.getEmpEmail();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmpEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            Register userRole = registerRepo.findByEmpEmail(email);
                 if(!userRole.isStatus()){

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                 }
            final String jwt = jwtService.generateToken(authRequest.getEmpEmail());

            // Create an instance of AuthenticationResponse and return it in the ResponseEntity
            AuthenticationResponse response = new AuthenticationResponse(jwt, userRole.getEmpName(),userRole.getId(),userRole.getEmpEmail(),userRole.getEmpMob(),userRole.getEmpRole(),userRole.getEmpDepartment(),userRole.isStatus(),userRole.getBranchId());
            return ResponseEntity.ok(response);

        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }

    }

}
