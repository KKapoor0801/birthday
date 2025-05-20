package com.example.birthday.controller;

import com.example.birthday.model.BirthdayDto;
import com.example.birthday.repository.BirthdayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/birthday")
public class BirthdayController {

    @Autowired
    private BirthdayRepository birthdayRepository;

    @GetMapping("/pingDB")
    public ResponseEntity<String> pingDB() throws IOException {
        log.info("Ping DB endpoint called");
        String response = birthdayRepository.pingDB();
        return ResponseEntity.ok(response);
    }

    //TODO
    @GetMapping("/getBirthday")
    public List<BirthdayDto> getBirthday() {
        log.info("Birthday endpoint called");
        List<BirthdayDto> response = new ArrayList<>();



        return response;
    }
}
