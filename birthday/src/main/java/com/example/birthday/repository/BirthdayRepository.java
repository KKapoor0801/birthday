package com.example.birthday.repository;

import com.example.birthday.model.BirthdayResponseDto;
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

@Repository
@Slf4j
public class BirthdayRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public String pingDB(final String traceId) throws Exception {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return namedParameterJdbcTemplate.queryForObject(QueryManager.getSql(QueryManager.pingDB), params, String.class);
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayRepository | Error pinging database: {}", traceId, e.getMessage());
            throw new Exception("Error pinging database", e);
        }
    }

    @Transactional
    public List<BirthdayResponseDto> getBirthday(final String traceId, final String birthdayDate) throws Exception{
        List<BirthdayResponseDto> birthdayResponse;
        try {
            log.info("traceId = {} | BirthdayRepository | Fetching birthday data", traceId);
            RowMapper<BirthdayResponseDto> rowMapper = new BeanPropertyRowMapper<>(BirthdayResponseDto.class);
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("birthdayDate",birthdayDate);
            birthdayResponse = namedParameterJdbcTemplate.query(QueryManager.getSql(QueryManager.getBirthday),params, rowMapper);
            if (ObjectUtils.isEmpty(birthdayResponse)) {
                log.info("traceId = {} | BirthdayRepository | No birthday data found for date: {}", traceId, birthdayDate);
                return new ArrayList<>();
            }
            return birthdayResponse;
        } catch (Exception e) {
            log.error("traceId = {} | BirthdayRepository | Error fetching birthday data: {}", traceId, e.getMessage());
            throw new Exception("Error fetching birthday data", e);
        }
    }



}
