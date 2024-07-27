package com.jipthechip.pidemo.controller;

import com.jipthechip.pidemo.model.LightLevel;
import com.jipthechip.pidemo.service.LightLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LightLevelController {

    @Autowired
    LightLevelService service;

    @GetMapping("/light-level-json")
    private List<LightLevel> lightLevelJson(){
        return service.findAllLightLevel();
    }

    @PostMapping("/light-level")
    private void postLightLevel(@RequestBody LightLevel lightLevel){
        service.saveLightLevel(lightLevel);
    }

}
