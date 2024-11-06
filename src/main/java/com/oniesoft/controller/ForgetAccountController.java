package com.oniesoft.controller;

import com.oniesoft.dto.RegisterDto;
import com.oniesoft.service.ForgetAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgetaccount/v1")
public class ForgetAccountController {
    @Autowired
    private ForgetAccount forgetAccount;
    @PostMapping("/forgetaccount")
    public ResponseEntity<?> forgetAccount(@RequestBody RegisterDto registerDto) {
        boolean flag = forgetAccount.getForgetedOTP(registerDto);
        if (flag) {
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(404).body("Data not Match");
        }
    }
    @PutMapping("/verifyforgetotp")
    public ResponseEntity<?> verifyForgetOtp(@RequestBody RegisterDto registerDto){
        boolean flag=    forgetAccount.verifyForgetOtp(registerDto);
        if(flag){
            return ResponseEntity.ok("ok");
        }else{
            return  ResponseEntity.status(404).body("unexpected error");
        }
    }
}
