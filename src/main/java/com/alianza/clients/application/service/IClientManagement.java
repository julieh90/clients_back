package com.alianza.clients.application.service;

import com.alianza.clients.commons.domains.ClientDTO;
import org.springframework.core.io.InputStreamResource;

import java.util.List;
import java.util.Optional;

public interface IClientManagement {

    List<ClientDTO> findClientsBySharedKeyContaining(String sharedKey);

    boolean existsClientBySharedKey(String sharedKey);

    List<ClientDTO> findAll();

    ClientDTO saveClient(ClientDTO client);

    Optional<ClientDTO> findClientBySharedKey(String sharedKey);

    boolean deleteClient(String sharedKey);

    ClientDTO updateClient (String sharedKey, ClientDTO clientDTO);

    boolean exportCsv(List<ClientDTO> clientes, String archivo);

    InputStreamResource downloadCsv();


}
