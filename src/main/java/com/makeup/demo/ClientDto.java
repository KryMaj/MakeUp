package com.makeup.demo;

import java.util.UUID;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    private String name;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime selectedDate;
    private String uniqueCode;

    public ClientDto(String name, String phoneNumber){
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.selectedDate = LocalDateTime.now();
        this.uniqueCode=generateUniqueCode();
    }


    public ClientDto(String name, String phoneNumber, LocalDateTime selectedDate){
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.selectedDate=selectedDate;
        this.uniqueCode=generateUniqueCode();

    }





    private String generateUniqueCode() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String code;
       return code = uuid.substring(0, 5);
    }





}
