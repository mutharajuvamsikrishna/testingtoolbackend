package com.oniesoft.controller;

import com.oniesoft.model.UserConfig;
import com.oniesoft.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userconfig/v1")
public class UserConfigController {
    @Autowired
    private UserConfigService userConfigService;
    @PostMapping("/save")
    public UserConfig userConfig(@RequestBody UserConfig userConfig){
        return userConfigService.addOrUpdateConfig(userConfig);
    }
    @GetMapping("/getconfigbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) throws Exception {
        try {
            UserConfig userConfig = userConfigService.getConfigById(id);
            return ResponseEntity.ok(userConfig);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getallconfig")
    public List<UserConfig> getAllConfig(){
        return userConfigService.getAllConfig();
    }
    @GetMapping("/getconfigsbyuid/{userId}")
    public List<UserConfig> getListConfigByUserId(@PathVariable int userId){
        return userConfigService.getAllUserConfigByUserId(userId);
    }
    @DeleteMapping("/deleteconfigbyid/{id}")
    public String deleteConfig(@PathVariable int id){
        String msg=userConfigService.deleteConfig(id);
        if(msg.isEmpty()){
            return "Something Went Wrong";
        }else {
            return "Deleted SuccessFully";
        }
    }
}
