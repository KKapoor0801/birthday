package com.example.birthday.service;

import com.example.birthday.model.BirthdayResponseDto;
import com.example.birthday.repository.BirthdayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BirthdayService {

    @Autowired
    private BirthdayRepository birthdayRepository;

    public List<BirthdayResponseDto> getBirthday(final String traceId, final String birthdayDate) throws Exception {
        log.info("traceid = {} | BirthdayService | Fetching birthday data", traceId);
        List<BirthdayResponseDto> birthdayResponse = new ArrayList<>();
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{2}$"; //To check if a date string is in DD-MM-YY format
        if(birthdayDate.matches(regex)) {
            birthdayResponse = birthdayRepository.getBirthday(traceId, birthdayDate);
        } else {
            log.error("traceId = {} | BirthdayService | Invalid date format: {}", traceId, birthdayDate);
        }
        return birthdayResponse;
    }
}
