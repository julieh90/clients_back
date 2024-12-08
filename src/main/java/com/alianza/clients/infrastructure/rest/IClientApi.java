package com.alianza.clients.infrastructure.rest;

import com.alianza.clients.commons.domains.ClientDTO;
import com.alianza.clients.domains.model.entities.Client;

import java.util.List;

public interface IClientApi {

    List<ClientDTO> findClientsBySharedKeyContaining(String sharedKey);

    List<ClientDTO> findAll();

    ClientDTO saveClient(Client client);

    boolean deleteClient(String sharedKey);

    void exportCsv();


}
