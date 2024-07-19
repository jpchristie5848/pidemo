package com.jipthechip.pidemo.controller;

import com.jipthechip.pidemo.model.DemoModel;
import com.jipthechip.pidemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PidemoController {

    @Autowired
    private DemoService service;

    @GetMapping("/hello-world")
    private String HelloWorld(){
        return "Hello world!";
    }

    @GetMapping("/db-test")
    private List<DemoModel> DBTest(){
        DemoModel newModel = new DemoModel();
        service.saveDemo(newModel);
        return service.getAllDemo();
    }
}
