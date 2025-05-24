package com.example.birthday.controller;

import com.example.birthday.model.BirthdayRequestDto;
import com.example.birthday.model.BirthdayResponseDto;
import com.example.birthday.repository.BirthdayRepository;
import com.example.birthday.service.BirthdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/v1/birthday")
public class BirthdayController {

    @Autowired
    private BirthdayRepository birthdayRepository;

    @Autowired
    private BirthdayService birthdayService;

    @GetMapping("/pingDB")
    public ResponseEntity<String> pingDB(@RequestHeader String traceId) throws Exception {
        try {
            //String traceId = UUID.randomUUID().toString(); -> Not needed as traceId is passed in header
            log.info("traceId = {} | BirthdayController | Ping DB endpoint called", traceId);
            final String response = birthdayRepository.pingDB(traceId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error pinging the database: " + e.getLocalizedMessage());
        }
    }

    //TODO
    @GetMapping("/getBirthday")
    public List<BirthdayResponseDto> getBirthday(@RequestHeader String traceId, @RequestBody BirthdayRequestDto birthdayRequestDto) throws Exception {
        log.info("traceId = {} | BirthdayController | Birthday endpoint called", traceId);
        List<BirthdayResponseDto> response = new ArrayList<>();
        if (!ObjectUtils.isEmpty(birthdayRequestDto) && !ObjectUtils.isEmpty(birthdayRequestDto.getBirthdayDate())) {
            response = birthdayService.getBirthday(traceId, birthdayRequestDto.getBirthdayDate());
        } else {
            log.error("traceId = {} | BirthdayController | Invalid request body: {}", traceId, birthdayRequestDto);
        }
        return response;
    }
}
