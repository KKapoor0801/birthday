package com.example.birthday.repository;

import com.example.birthday.util.QueryManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@Slf4j
public class BirthdayRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public String pingDB() throws IOException {
        return namedParameterJdbcTemplate.queryForObject(QueryManager.getSql(QueryManager.pingDB), new MapSqlParameterSource(), String.class);
    }

}
