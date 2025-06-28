package com.example.birthday.repository;

import com.example.birthday.model.BirthdayDto;
import com.example.birthday.util.QueryManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class BirthdayRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public String pingDB(final String traceId) throws Exception {
        log.info("traceId = {} | BirthdayRepository | pingDB | Pinging database", traceId);
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return namedParameterJdbcTemplate.queryForObject(QueryManager.getSql(QueryManager.pingDB), params, String.class);
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayRepository | pingDB | Error pinging database: {}", traceId, e.getMessage());
            throw new Exception("Error pinging database", e);
        }
    }

    @Transactional
    public List<BirthdayDto> getBirthdayAndSendEmail(final String traceId, final String dayOfMonth) throws Exception{
        List<BirthdayDto> birthdayResponse;
        try {
            log.info("traceId = {} | BirthdayRepository | getBirthdayAndSendEmail | Fetching birthday data", traceId);
            RowMapper<BirthdayDto> rowMapper = new BeanPropertyRowMapper<>(BirthdayDto.class);
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("dayOfMonth",dayOfMonth);
            birthdayResponse = namedParameterJdbcTemplate.query(QueryManager.getSql(QueryManager.getBirthdayAndSendEmail),params, rowMapper);
            if (ObjectUtils.isEmpty(birthdayResponse)) {
                log.info("traceId = {} | BirthdayRepository | getBirthdayAndSendEmail | No birthday data found for date: {}", traceId, dayOfMonth);
                return new ArrayList<>();
            }
            return birthdayResponse;
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayRepository | getBirthdayAndSendEmail | Error fetching birthday data: {}", traceId, e.getMessage());
            throw new Exception("Error fetching birthday data", e);
        }
    }

    @Transactional
    public String insertBirthday(final String traceId, final BirthdayDto birthdayDto, final String dateOfBirthString) throws Exception {
        String response = "Failure";
        try {
            log.info("traceId = {} | BirthdayRepository | insertBirthday | Inserting birthday data", traceId);
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", UUID.randomUUID().toString());
            params.addValue("name", birthdayDto.getName());
            params.addValue("birthdayDate", dateOfBirthString);
            params.addValue("emailAddress", birthdayDto.getEmailAddress());
            int queryResult = namedParameterJdbcTemplate.update(QueryManager.getSql(QueryManager.insertBirthday), params);
            if(queryResult > 0) {
                response = "Successfully Inserted Birthday Data";
                log.info("traceId = {} | BirthdayRepository | insertBirthday | Birthday data inserted successfully",traceId);
                return response;
            }
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayRepository | insertBirthday | Error inserting birthday data: {}", traceId, e.getLocalizedMessage());
        }
        return response;
    }

}
