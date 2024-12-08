package com.alianza.clients.domains.repostirory;

import com.alianza.clients.domains.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IClient extends JpaRepository<Client, String> {

    List<Client> findClientsBySharedKeyContaining(String sharedKey);

    boolean existsClientBySharedKey(String sharedKey);

    List<Client> findAll();


    boolean existsClientByBusinessIdAndAndEmailAndAndPhone(String businessId, String email, Long phone);

    Client findClientBySharedKey(String sharedKey);

}

