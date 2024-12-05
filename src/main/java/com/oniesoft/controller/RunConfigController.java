package com.oniesoft.controller;

import com.oniesoft.service.RunConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/runconfig/v1")
public class RunConfigController {
    @Autowired
    private RunConfigService runConfigService;

}
