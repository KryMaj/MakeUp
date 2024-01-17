package com.makeup.demo;


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



}
