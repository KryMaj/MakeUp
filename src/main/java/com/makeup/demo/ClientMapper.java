package com.makeup.demo;

public interface ClientMapper {

    static ClientDto toDto(ClientEntity client){
        return ClientDto.builder()
                .name(client.getName())
                .phoneNumber(client.getPhoneNumber())
                .selectedDate(client.getSelectedDate())
                .build();
    }

    static ClientEntity toEntity(ClientDto clientDto){
        return ClientEntity.builder()
                .name(clientDto.getName())
                .phoneNumber(clientDto.getPhoneNumber())
                .selectedDate(clientDto.getSelectedDate())
                .build();
    }
}
