package com.jipthechip.pidemo.service;

import com.jipthechip.pidemo.model.DemoModel;
import com.jipthechip.pidemo.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {
    @Autowired
    private DemoRepository repository;

    public DemoModel saveDemo(DemoModel demoModel){
        return repository.save(demoModel);
    }

    public List<DemoModel> getAllDemo(){
        return repository.findAll();
    }
}
