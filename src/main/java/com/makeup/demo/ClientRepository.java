package com.makeup.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {


    ClientEntity findClientByUniqueCode(String uniqueCode);

    void deleteClientByUniqueCode(String code);

}
