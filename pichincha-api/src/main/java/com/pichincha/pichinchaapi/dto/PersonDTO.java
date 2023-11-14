package com.pichincha.pichinchaapi.dto;

import com.pichincha.pichinchaapi.enums.Gender;
import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private String identification;
}
