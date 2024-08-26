package com.javalab.humaninfo.controller;

import com.javalab.humaninfo.entity.Human;
import com.javalab.humaninfo.repository.HumanRepository;
import com.javalab.humaninfo.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HumanController {

    private final HumanService humanService;


    @Autowired
    public HumanController(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping("/generate-and-save")
    public String generateAndSave() {
        try {
            humanService.generateAndSaveToSqlFileAndDatabase(1000); // 1000개의 데이터 생성 및 저장
            return "데이터가 성공적으로 생성되고 저장되었습니다.";
        } catch (IOException e) {
            return "데이터 생성 및 저장 중 오류 발생: " + e.getMessage();
        }
    }
}