package com.example.birthday.scheduler;

import com.example.birthday.model.BirthdayDto;
import com.example.birthday.service.BirthdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@EnableScheduling
public class BirthdayScheduler {

    @Autowired
    private BirthdayService birthdayService;

    @Scheduled(cron = "0 30 02 * * *", zone = "UTC")
    public void sendBirthdayEmails() {
        try {
            String traceId = UUID.randomUUID().toString();
            List<BirthdayDto> birthdayDtoList = new ArrayList<>();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = today.format(formatter);
            log.info("BirthdayScheduler | sendBirthdayEmails | Scheduled task started to send birthday emails | Current Date: {} | Current Time = {}", formattedDate, Calendar.getInstance().getTime());
            birthdayDtoList = birthdayService.getBirthdayAndSendEmail(traceId, formattedDate);
            log.info("BirthdayScheduler | sendBirthdayEmails | Birthday details = {}", birthdayDtoList);
        } catch (Exception e) {
            log.error("BirthdayScheduler | sendBirthdayEmails | Error occurred while sending birthday emails: {}", e.getMessage());
        }
    }


}
