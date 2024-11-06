package com.example.managersystem.controller;

import com.example.managersystem.model.InstanceModel;
import com.example.managersystem.service.InstanceModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class InstanceController {

    @Autowired
    private InstanceModelService instanceModelService;

    @RequestMapping("/hello")
    public String TestOutput(){
        log.info("/hello");
        return "Hey, superstar! Long time no see! Missed your face!";
    }


    @GetMapping(value = "/getInstanceById/{id}")
    public InstanceModel getInstanceById(@PathVariable final String id) throws Exception {
        try {
            InstanceModel instanceModel = this.instanceModelService.getById(id);
            log.info("实例ID：{}, 实例信息：{}", id, instanceModel.toString());
            return instanceModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
