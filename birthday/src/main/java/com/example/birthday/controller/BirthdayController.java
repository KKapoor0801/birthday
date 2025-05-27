package com.example.birthday.controller;

import com.example.birthday.model.BirthdayRequestDto;
import com.example.birthday.model.BirthdayDto;
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
            log.info("traceId = {} | BirthdayController | pingDB | Ping DB endpoint called", traceId);
            final String response = birthdayRepository.pingDB(traceId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayController | pingDB | Error pinging the database: {}", traceId, e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body("Error pinging the database");
        }
    }


    @GetMapping("/getBirthday")
    public List<BirthdayDto> getBirthday(@RequestHeader String traceId, @RequestBody BirthdayRequestDto birthdayRequestDto) throws Exception {
        log.info("traceId = {} | BirthdayController | getBirthday | Birthday endpoint called", traceId);
        List<BirthdayDto> response = new ArrayList<>();
        if (!ObjectUtils.isEmpty(birthdayRequestDto) && !ObjectUtils.isEmpty(birthdayRequestDto.getBirthdayDate())) {
            response = birthdayService.getBirthday(traceId, birthdayRequestDto.getBirthdayDate());
            log.info("traceId = {} | BirthdayController | getBirthday | Birthday data fetched successfully for date: {}", traceId, response);
        } else {
            log.error("traceId = {} | BirthdayController | getBirthday | Invalid Date Provided: {}", traceId, birthdayRequestDto.getBirthdayDate());
        }
        return response;
    }

    @PostMapping("/insertBirthday")
    public ResponseEntity<String> insertBirthday(@RequestHeader String traceId, @RequestBody BirthdayDto birthdayDto) throws Exception {
        log.info("traceId = {} | BirthdayController | insertBirthday | Insert Birthday endpoint called", traceId);
        String response;
        if (!ObjectUtils.isEmpty(birthdayDto) && !ObjectUtils.isEmpty(birthdayDto.getDateOfBirth())
                && !ObjectUtils.isEmpty(birthdayDto.getName()) && !ObjectUtils.isEmpty(birthdayDto.getEmailAddress())){
            response = birthdayService.insertBirthday(traceId, birthdayDto);
        } else {
            log.error("traceId = {} | BirthdayController | insertBirthday | Invalid request body: {}", traceId, birthdayDto);
            return ResponseEntity.badRequest().body("Invalid request body");
        }
        if(!ObjectUtils.isEmpty(response) && !response.equals("Failure")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body("Failed to insert birthday data");
        }
    }
}
