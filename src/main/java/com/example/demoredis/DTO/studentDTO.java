package com.example.demoredis.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class studentDTO implements Serializable{
   private static final Long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String gender;
    private String dob;
}
