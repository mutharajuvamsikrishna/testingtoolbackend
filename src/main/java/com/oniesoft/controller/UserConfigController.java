package com.oniesoft.controller;

import com.oniesoft.dto.UserConfigDto;
import com.oniesoft.model.UserConfig;
import com.oniesoft.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userconfig/v1")
public class UserConfigController {
    @Autowired
    private UserConfigService userConfigService;

    @PostMapping("/save")
    public UserConfig userConfig(@RequestBody UserConfig userConfig) {
        try {
            return userConfigService.addOrUpdateConfig(userConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getconfigbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) throws Exception {
        try {
            UserConfig userConfig = userConfigService.getConfigById(id);
            return ResponseEntity.ok(userConfig);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getallconfig")
    public List<UserConfig> getAllConfig() {
        return userConfigService.getAllConfig();
    }

    @GetMapping("/getconfigsbyuid/{userId}")
    public ResponseEntity<?> getListConfigByUserId(
            @PathVariable int userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {


            // Create Pageable object
            Pageable pageable = PageRequest.of(page, size);

            // Fetch paginated data
            Page<UserConfigDto> userConfigDtos = userConfigService.getListOfConfigById(userId, pageable);

            return ResponseEntity.ok(userConfigDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteconfigbyid/{id}")
    public String deleteConfig(@PathVariable int id) {
        String msg = userConfigService.deleteConfig(id);
        if (msg.isEmpty()) {
            return "Something Went Wrong";
        } else {
            return "Deleted SuccessFully";
        }
    }
}
