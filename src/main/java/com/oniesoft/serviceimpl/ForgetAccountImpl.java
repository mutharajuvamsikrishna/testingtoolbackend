package com.oniesoft.serviceimpl;

import com.oniesoft.dto.RegisterDto;
import com.oniesoft.model.Register;
import com.oniesoft.repository.RegisterRepo;
import com.oniesoft.service.ForgetAccount;
import com.oniesoft.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ForgetAccountImpl implements ForgetAccount {
    @Autowired
    RegisterRepo registerRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RegisterService registerService;
    private HashMap<String, RegisterDto> userMap = new HashMap<>();
    @Value("${superAdminEmail}")
    private String superAdminEmail;
    @Override
    public void saveRegisters(RegisterDto registerDto) {
        userMap.put(registerDto.getEmpId(), registerDto);
    }

    @Override
    public RegisterDto getRegister(String mob) {
        return userMap.get(mob);
    }
    @Override
    public void removeRegister(String mob) {
        userMap.remove(mob);
    }
    @Override
    public String generateOTP() {
        // Generate a random 6-digit OTP
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }
    @Override
    public boolean getForgetedOTP(RegisterDto registerDto) {
        Register register=registerRepo.findByEmpMobAndEmpEmail(registerDto.getEmpMob(),registerDto.getEmpEmail());
        if(register!=null){
            String subject="ONiE Soft ";
            String otp=  generateOTP();
            registerDto.setOtp(otp);
            saveRegisters(registerDto);
            String body = "Hello " + register.getEmpName() + " Your OTP for EmployeeManagement Forgett Acoount is " + "\n" + otp;
            if(register.getEmpEmail()!="") {
                registerService.sendEmail(register.getEmpEmail(), subject, body);
            }else {
                registerService.sendEmail(superAdminEmail, subject, body);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public  boolean verifyForgetOtp(RegisterDto registerDto){
        RegisterDto registerDto1 = getRegister(registerDto.getEmpId());
        if ((registerDto1!=null)&&(registerDto1.getOtp().equals(registerDto.getOtp()))) {
            Register register=  registerRepo.findByEmpEmail(registerDto.getEmpEmail());
            register.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            registerRepo.save(register);
            removeRegister(registerDto.getEmpId());
            return true;
        } else {
            return false;
        }
    }
}
