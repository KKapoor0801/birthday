package com.example.birthday.model;

import lombok.Data;

@Data
public class BirthdayDto {

    private String id;
    private String name;
    private String dateOfBirth;
    private String emailAddress;
    private String createdAt;
}
