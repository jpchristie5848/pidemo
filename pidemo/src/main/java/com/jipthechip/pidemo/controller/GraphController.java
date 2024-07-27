package com.jipthechip.pidemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jipthechip.pidemo.model.LightLevel;
import com.jipthechip.pidemo.service.LightLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GraphController {

    @Autowired
    LightLevelService service;

    @GetMapping("/light-level-graph")
    private String lightLevelGraph(Model model) throws JsonProcessingException {
        List<LightLevel> dataPoints = service.findAllLightLevel();

        // Initialize ObjectMapper and register JavaTimeModule
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Convert dataPoints to JSON string
        String jsonDataPoints = mapper.writeValueAsString(dataPoints);
        model.addAttribute("dataPoints", jsonDataPoints);
        return "graph";
    }
}
