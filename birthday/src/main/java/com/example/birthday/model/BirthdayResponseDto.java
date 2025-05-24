package com.example.birthday.model;

import lombok.Data;

@Data
public class BirthdayResponseDto {

    private String id;
    private String name;
    private String dateOfBirth;
    private String emailAddress;
    private String createdAt;
}
