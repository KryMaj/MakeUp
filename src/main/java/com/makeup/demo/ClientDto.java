package com.makeup.demo;

import java.util.UUID;

import lombok.*;


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
    private LocalDateTime selectedDate;
    private String uniqueCode;


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