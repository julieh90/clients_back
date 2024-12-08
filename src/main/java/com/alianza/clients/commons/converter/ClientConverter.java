package com.alianza.clients.commons.converter;

import com.alianza.clients.commons.domains.ClientDTO;
import com.alianza.clients.domains.model.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {

    public static ClientDTO convertClienToClientDTO(Client client) {
        try {

            return  ClientDTO.builder()
                    .sharedKey(client.getSharedKey())
                    .businessId(client.getBusinessId())
                    .email(client.getEmail())
                    .phone(client.getPhone())
                    .startDate(client.getStartDate())
                    .endDate(client.getEndDate())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Client convertClientDTOToClient(ClientDTO clientDTO) {
        try {

            return  Client.builder()
                    .sharedKey(clientDTO.getSharedKey())
                    .businessId(clientDTO.getBusinessId())
                    .email(clientDTO.getEmail())
                    .phone(clientDTO.getPhone())
                    .startDate(clientDTO.getStartDate())
                    .endDate(clientDTO.getEndDate())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
