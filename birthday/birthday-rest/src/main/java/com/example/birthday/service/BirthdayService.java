package com.example.birthday.service;

import com.example.birthday.model.BirthdayDto;
import com.example.birthday.repository.BirthdayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Slf4j
public class BirthdayService {

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BirthdayRepository birthdayRepository;

    public List<BirthdayDto> getBirthdayAndSendEmail(final String traceId, final String birthdayDate) throws Exception {
        log.info("traceId = {} | BirthdayService | getBirthdayAndSendEmail | Fetching birthday data", traceId);
        List<BirthdayDto> birthdayResponse = new ArrayList<>();
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$"; //To check if a date string is in DD-MM-YY format
        if (birthdayDate.matches(regex)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Set timezone to UTC
            Date date = sdf.parse(birthdayDate);
            String dayOfMonth = new SimpleDateFormat("dd-MM").format(date);
            birthdayResponse = birthdayRepository.getBirthdayAndSendEmail(traceId, dayOfMonth);
            for (BirthdayDto birthdayDto : birthdayResponse) {
                if (!ObjectUtils.isEmpty(birthdayDto) && !ObjectUtils.isEmpty(birthdayDto.getName()) && !ObjectUtils.isEmpty(birthdayDto.getEmailAddress())) {
                    birthdayDto.setDateOfBirth(date);
                    sendEmail(traceId, birthdayDto);
                } else {
                    log.info("traceId = {} | BirthdayService | getBirthdayAndSendEmail | No birthday data found for date: {}", traceId, birthdayDate);
                }
            }

        } else {
            log.error("traceId = {} | BirthdayService | getBirthdayAndSendEmail | Invalid date format: {}", traceId, birthdayDate);
        }
        return birthdayResponse;
    }

    public String insertBirthday(final String traceId, final BirthdayDto birthdayDto) throws Exception {
        log.info("traceId = {} | BirthdayService | insertBirthday | Inserting birthday data", traceId);
        String response = null;
        String validDateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$"; //To check if a date string is in DD-MM-YY format
        String validEmailAddressRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateOfBirthString = sdf.format(birthdayDto.getDateOfBirth());
        if (!dateOfBirthString.matches(validDateRegex)) {
            log.error("traceId = {} | BirthdayService | insertBirthday | Invalid Date Provided: {}", traceId, birthdayDto.getDateOfBirth());
        } else if (!birthdayDto.getEmailAddress().matches(validEmailAddressRegex)) {
            log.error("traceId = {} | BirthdayService | insertBirthday | Invalid Email Address provided: {}", traceId, birthdayDto.getEmailAddress());
        } else {
            response = birthdayRepository.insertBirthday(traceId, birthdayDto, dateOfBirthString);
        }
        return response;
    }

    public void sendEmail(final String traceId, final BirthdayDto birthdayResponse) throws Exception {
        log.info("traceId = {} | BirthdayService | sendEmail | Sending birthday email notifications", traceId);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        log.info("traceId = {} | BirthdayService | sendEmail | Sending email to: {}", traceId, birthdayResponse.getEmailAddress());
        mailMessage.setTo(birthdayResponse.getEmailAddress());
        mailMessage.setSubject("Happy Birthday " + birthdayResponse.getName() + "!");
        mailMessage.setText("Dear " + birthdayResponse.getName() + ",\n\nWishing you a very Happy Birthday!\n\nBest wishes,\nKeshav\n");
        mailMessage.setFrom(fromEmailAddress);
        try {
            mailSender.send(mailMessage);
            log.info("traceId = {} | BirthdayService | sendEmail | Email sent successfully to: {}", traceId, birthdayResponse.getEmailAddress());
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayService | sendEmail | Error sending email to {}: {}", traceId, birthdayResponse.getEmailAddress(), e.getMessage());
        }
    }
}
