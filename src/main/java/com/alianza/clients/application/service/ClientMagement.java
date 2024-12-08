package com.alianza.clients.application.service;

import com.alianza.clients.commons.converter.ClientConverter;
import com.alianza.clients.commons.domains.ClientDTO;
import com.alianza.clients.commons.domains.ErrorCode;
import com.alianza.clients.commons.exception.ErrorException;
import com.alianza.clients.commons.exception.NoContentException;
import com.alianza.clients.domains.model.entities.Client;
import com.alianza.clients.domains.repostirory.IClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.alianza.clients.commons.domains.ErrorCode.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClientMagement implements IClientManagement {

    private final IClient clientRepository;


    @Override
    public List<ClientDTO> findClientsBySharedKeyContaining(String sharedKey) {
        log.info("Call findClientsBySharedKey {}", sharedKey);
        List<Client> clients = clientRepository.findClientsBySharedKeyContaining(sharedKey);

        log.info("Clients found: {}", clients.size());
        return clients.stream()
                .sorted(Comparator.comparing(Client::getBusinessId))
                .map(ClientConverter::convertClienToClientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsClientBySharedKey(String sharedKey) {
        log.info("Call existClient: {}", sharedKey);
        return clientRepository.existsClientBySharedKey(sharedKey);
    }

    public boolean existsClient(ClientDTO client) {

        return clientRepository.existsClientByBusinessIdAndAndEmailAndAndPhone(client.getBusinessId(),
                client.getEmail(), client.getPhone());
    }

    @Override
    public List<ClientDTO> findAll() {
        log.info("Call findAll");
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(ClientConverter::convertClienToClientDTO)
                .collect(Collectors.toList());
    }

    public String generateBaseSharedKey(String businessId) {

        log.info("Generate sharedkey {}", businessId);
        String[] parts = businessId.split(" ");
        String firstLetter = parts[0].substring(0, 1).toLowerCase();
        String restOfName = parts.length > 1 ? parts[1].toLowerCase() : "";

        return firstLetter + restOfName;
    }

    private String generateUniqueSharedKey(String businessId) {
        log.info("Generate unique sharedKey {}", businessId);

        String[] parts = businessId.split(" ");
        String firstName = parts[0].toLowerCase();
        String lastName = parts.length > 1 ? parts[1].toLowerCase() : "";

        String baseKey = firstName.substring(0, 1) + lastName;

        String uniqueKey = baseKey;
        int counter = 1;

        while (existsClientBySharedKey(uniqueKey)) {
            uniqueKey = baseKey + (char) ('a' + counter - 1);
            counter++;
        }

        return uniqueKey;
    }

    public String validateAndGenerateSharedKey(Client client) {

        String baseKey = generateBaseSharedKey(client.getBusinessId());
        log.info("SharedKey generated {}", baseKey);
        String uniqueKey = baseKey;

        while (existsClientBySharedKey(uniqueKey)) {
            uniqueKey = generateUniqueSharedKey(client.getBusinessId());
        }
        return uniqueKey;
    }

    public ClientDTO saveClient(ClientDTO clientDTO) {
        log.info("Validating  client existence...");

        if (!existsClient(clientDTO)) {

            Client client = ClientConverter.convertClientDTOToClient(clientDTO);
            assert client != null;
            String sharedKey = validateAndGenerateSharedKey(client);
            client.setSharedKey(sharedKey);

            clientRepository.save(client);

            log.info("Client saved with sharedKey: " + sharedKey);

            return ClientConverter.convertClienToClientDTO(client);
        }
        log.info("Client already exists, not saved.");

        throw new ErrorException(ERROR_CLIENT);
    }

    @Override
    public Optional<ClientDTO> findClientBySharedKey(String sharedKey) {

        Client client = Optional.ofNullable(clientRepository.findClientBySharedKey(sharedKey))
                .orElseThrow(() -> new NoContentException(ErrorCode.DATA_NOT_FOUND));
        log.info("clients: {}", client);

        return Optional.of(ClientConverter.convertClienToClientDTO(client));
    }

    public boolean deleteClient(String sharedKey) {

        return Optional.ofNullable(clientRepository.findClientBySharedKey(sharedKey))
                .map(client -> {
                    clientRepository.delete(client);
                    return true;
                }).orElse(false);
    }

    @Override
    public ClientDTO updateClient(String sharedKey, ClientDTO clientDTO) {
        log.info("UpdateClient");

        return Optional.ofNullable(clientRepository.findClientBySharedKey(sharedKey))
                .map(client -> ClientConverter.convertClienToClientDTO(
                        clientRepository.save(Objects.requireNonNull(ClientConverter.convertClientDTOToClient(clientDTO)))))
                .orElseThrow(() -> new ErrorException(ERROR_GENERIC));

    }

    public boolean exportCsv(List<ClientDTO> clientes, String archivo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write("Shared key;Business Id;Email;Phone;Start Date;End Date");
            writer.newLine();

            for (ClientDTO cliente : clientes) {
                String endDate = (cliente.getEndDate() != null) ? sdf.format(cliente.getEndDate()) : "";
                String linea = cliente.getSharedKey() + ";" + cliente.getBusinessId() + ";" + cliente.getEmail()
                        + ";" + cliente.getPhone() + ";" + cliente.getStartDate() + ";" + endDate;
                writer.write(linea);
                writer.newLine();
            }

            System.out.println("Los datos se han exportado correctamente a " + archivo);
            return true;

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public InputStreamResource downloadCsv() {
        List<ClientDTO> clientes = findAll();

        String archivo = "clients.csv";

        boolean success = exportCsv(clientes, archivo);

        if (success) {

            File file = new File(archivo);

            if (!file.exists()) {
                throw new NoContentException(FILE_NOT_FOUND);
            }

            try {

                FileInputStream fileInputStream = new FileInputStream(file);
                return new InputStreamResource(fileInputStream);

            } catch (IOException e) {
                e.printStackTrace();
                throw new ErrorException(ERROR_FILE);
            }
        } else {
            throw new ErrorException(ERROR_FILE);
        }
    }
}






