package com.jipthechip.pidemo.service;

import com.jipthechip.pidemo.model.LightLevel;
import com.jipthechip.pidemo.repository.DemoRepository;
import com.jipthechip.pidemo.repository.LightLevelRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LightLevelService {
    @Autowired
    private LightLevelRepository repository;

    public void saveLightLevel(LightLevel lightLevel){
        repository.save(lightLevel);
    }

    public List<LightLevel> findAllLightLevel(){
        return repository.findAll();
    }
}
