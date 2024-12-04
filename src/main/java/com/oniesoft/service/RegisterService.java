package com.oniesoft.service;

import com.oniesoft.model.Register;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RegisterService {


    Register saveRegisters(Register register) throws Exception;

    Register upDateRegisters(Register register);

    List<Register> getAllRegister();

    Register getRegister(String empId);

    Page<Register> getAllPageRegister(int page, int size);

    List<Register> searchForRegister(String query);

    void deleteRegister(int id) throws Exception;

    //Self Registration
    String sendEmail(String receipent, String subject, String body);
}
