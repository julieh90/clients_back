package com.alianza.clients.application.service;

import com.alianza.clients.commons.domains.ClientDTO;
import com.alianza.clients.commons.exception.ErrorException;
import com.alianza.clients.domains.model.entities.Client;
import com.alianza.clients.domains.repostirory.IClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientMagementTest {

    @Mock
    private IClient clientRepository;

    @InjectMocks
    private ClientMagement clientMagement;

    @Test
    void saveClient() {
        ClientDTO clientDTO = ClientDTO.builder()
                .businessId("Pepito Perez")
                .phone(1235678958L)
                .email("pepitoperez@gmail.com")
                .build();

        when(clientRepository.save(any())).thenReturn(Client.builder().sharedKey("pperez").build());
        ClientDTO response = clientMagement.saveClient(clientDTO);

        assertEquals(clientDTO.getBusinessId(), response.getBusinessId());
    }

    @Test
    void saveClientException() {
        ClientDTO clientDTO = ClientDTO.builder()
                .businessId("Pepito Perez")
                .phone(1235678958L)
                .email("pepitoperez@gmail.com")
                .build();
        when(clientRepository.existsClientByBusinessIdAndAndEmailAndAndPhone(anyString(),anyString(),anyLong())).thenReturn(true);

        assertThrows(ErrorException.class, () -> clientMagement.saveClient(clientDTO));
    }

    @Test
    void findAll() {
        when(clientRepository.findAll()).thenReturn(List.of(Client.builder().businessId("Pepito Perez").build()));
        List<ClientDTO> response = clientMagement.findAll();
        assertEquals("Pepito Perez", response.get(0).getBusinessId());
    }


}