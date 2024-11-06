package com.oniesoft.service;

import com.oniesoft.dto.RegisterDto;

public interface ForgetAccount {
    void saveRegisters(RegisterDto registerDto);

    RegisterDto getRegister(String mob);

    void removeRegister(String mob);

    String generateOTP();

    public boolean getForgetedOTP(RegisterDto registerDto);
    public  boolean verifyForgetOtp(RegisterDto registerDto);
}
