package com.example.birthday.service;

import com.example.birthday.model.BirthdayDto;
import com.example.birthday.repository.BirthdayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BirthdayService {

    @Autowired
    private BirthdayRepository birthdayRepository;

    public List<BirthdayDto> getBirthday(final String traceId, final String birthdayDate) throws Exception {
        log.info("traceId = {} | BirthdayService | getBirthday | Fetching birthday data", traceId);
        List<BirthdayDto> birthdayResponse = new ArrayList<>();
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$"; //To check if a date string is in DD-MM-YY format
        if(birthdayDate.matches(regex)) {
            birthdayResponse = birthdayRepository.getBirthday(traceId, birthdayDate);
        } else {
            log.error("traceId = {} | BirthdayService | getBirthday | Invalid date format: {}", traceId, birthdayDate);
        }
        return birthdayResponse;
    }

    public String insertBirthday(final String traceId, final BirthdayDto birthdayDto) throws Exception {
        log.info("traceId = {} | BirthdayService | insertBirthday | Inserting birthday data", traceId);
        String response = null;
        String validDateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$"; //To check if a date string is in DD-MM-YY format
        String validEmailAddressRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String dateOfBirthString = new SimpleDateFormat("dd-MM-yyyy").format(birthdayDto.getDateOfBirth());
        if (!dateOfBirthString.matches(validDateRegex)) {
            log.error("traceId = {} | BirthdayService | insertBirthday | Invalid Date Provided: {}", traceId, birthdayDto.getDateOfBirth());
        } else if (!birthdayDto.getEmailAddress().matches(validEmailAddressRegex)) {
            log.error("traceId = {} | BirthdayService | insertBirthday | Invalid Email Address provided: {}", traceId, birthdayDto.getEmailAddress());
        } else {
            response = birthdayRepository.insertBirthday(traceId, birthdayDto, dateOfBirthString);
        }
        return response;
    }
}
