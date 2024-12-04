package com.oniesoft.serviceimpl;

import com.oniesoft.dto.RegisterDto;
import com.oniesoft.model.Register;
import com.oniesoft.repository.CompanyRepo;
import com.oniesoft.repository.RegisterRepo;
import com.oniesoft.service.RegisterService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Service
public class RegisterServImpl implements RegisterService {
    @Autowired
    private RegisterRepo registerRepo;
    @Value("${superAdminName}")
    private String superAdminName;
    @Value("${superAdminEmail}")
    private String superAdminEmail;
    @Value("${superAdminMob}")
    private String  superAdminMob;
    @Value("${superAdminDept}")
    private String superAdminDept;
    @Value("${superAdminRole}")
    private String superAdminRole;
    @Value("${status}")
    private boolean status;

    @Value("${superAdminPassword}")
    private String superAdminPassword;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private CompanyRepo companyRepo;
    private HashMap<String, RegisterDto> userMap = new HashMap<>();

    @Override
    public Register saveRegisters(Register register) throws Exception {
        Register findRegister=registerRepo.findByEmpEmail(register.getEmpEmail());
        System.out.println(register);
        if (findRegister==null) {
            register.setStatus(true);
            register.setPassword(passwordEncoder.encode(register.getPassword()));
            Register register1 = registerRepo.save(register);
            String subject = "";
            String body = "";
            String adminSubject = "";
            String adminBody = "";
            sendEmail(register1.getEmpEmail(), subject, body);
            sendEmail(superAdminEmail, adminSubject, adminBody);
            return register1;
        }else{
             throw new Exception("User Already Exists");
        }
    }

    @Override
    public Register upDateRegisters(Register register) {
        Register register1 = registerRepo.findByEmpEmail(register.getEmpEmail());
        if (register1 != null) {
            register1.setEmpName(register.getEmpName());
            register1.setEmpDepartment(register.getEmpDepartment());
            register1.setEmpMob(register.getEmpMob());
            register1.setStatus(register.isStatus());
            registerRepo.save(register1);
            String subject="";
            String body="";
            String adminSubject="";
            String adminBody="";
            sendEmail(register1.getEmpEmail(),subject,body);
            sendEmail(superAdminEmail,adminSubject,adminBody);
        }
        return register1;
    }

    @Override
    public List<Register> getAllRegister() {
        return registerRepo.findAll();
    }

    @PostConstruct
    public void addAdmin() {
        Register register = new Register();
        register.setEmpName(superAdminName);
        register.setEmpEmail(superAdminEmail);
        register.setEmpMob(superAdminMob);
        register.setEmpDepartment(superAdminDept);
        register.setEmpRole(superAdminRole);
        register.setStatus(status);
        register.setPassword(passwordEncoder.encode(superAdminPassword));
        Register register1 = registerRepo.findByEmpEmail(superAdminEmail);
        if (register1 == null) {
            registerRepo.save(register);
        }
    }

    @Override
    public Register getRegister(String email) {
        return registerRepo.findByEmpEmail(email);
    }
    @Override
    public Page<Register> getAllPageRegister(int page, int size) {
        // Create a Pageable object with a dynamic page number and page size
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated departments
        return registerRepo.findAll(pageable);
    }
@Override
public List<Register> searchForRegister(String query){
    return registerRepo.searchRegisterDetails(query);
}
    @Override
    public void deleteRegister(int id) throws Exception {
        if (registerRepo.existsById(id)) {
            registerRepo.deleteById(id);
        } else {
            throw new NullPointerException("Register with ID " + id + " not found.");
        }
    }

    //Self Registration
    @Override
    public String sendEmail(String receipent, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receipent);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return "otp Sent Sucessfully";
    }


}
