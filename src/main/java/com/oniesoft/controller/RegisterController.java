package com.oniesoft.controller;

import com.oniesoft.model.Register;
import com.oniesoft.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register/v1")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/addregister/{role}")
    public ResponseEntity<?> addRegister(@PathVariable String role,@RequestBody Register register) {
        Register register1 = null;
        try {
            register1 = registerService.saveRegisters(register,role);
            return ResponseEntity.ok(register1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving the register: " + e.getMessage());
        }

    }

    @PostMapping("/updateregister")
    public ResponseEntity<Register> updateRegister(@RequestBody Register register) {
        Register register1 = registerService.upDateRegisters(register);
        if (register1 != null) {
            return ResponseEntity.ok(register1);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getallreg")
    public List<Register> getAllRegister() {

        return registerService.getAllRegister();
    }
    @GetMapping("/getallpageregister")
    public Page<Register> getAllPageDept(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        // Call the service method with page and size parameters
        return registerService.getAllPageRegister(page,size);
    }
    @GetMapping("/getreg")
    public Register getAdminRegister(@RequestParam String email) {

        return registerService.getRegister(email);
    }


    @DeleteMapping("/deleteregister")
    public ResponseEntity<String> deleteRegister(@RequestParam int id) {
        try {
            // Call the service to delete the register
            registerService.deleteRegister(id);
            return ResponseEntity.ok("Register deleted successfully");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the register");
        }
    }

}