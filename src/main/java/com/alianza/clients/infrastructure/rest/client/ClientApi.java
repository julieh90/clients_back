package com.alianza.clients.infrastructure.rest.client;

import com.alianza.clients.application.service.IClientManagement;
import com.alianza.clients.commons.domains.ClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/client/v1")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@Log4j2
public class ClientApi {

    private final IClientManagement clientManagement;


    @GetMapping(value = "/{sharedKey}", headers = "Accept=application/json")
    public ResponseEntity<?> searchBySharedKey(@PathVariable("sharedKey") String sharedkey) throws Exception {

        return new ResponseEntity<>(clientManagement.findClientsBySharedKeyContaining(sharedkey), HttpStatus.OK);
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    public ResponseEntity<?> searchClients() throws Exception {

        return new ResponseEntity<>(clientManagement.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/", headers = "Accept=application/json")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) throws Exception {
        log.info("Calling saveClient....");
        ClientDTO savedClient = clientManagement.saveClient(clientDTO);

        if (savedClient == null) {
            return new ResponseEntity<>("Client already exists.", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(savedClient, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{sharedKey}", headers = "Accept=application/json")
    public ResponseEntity<?> deletelient(@PathVariable String sharedKey) throws Exception {

        return new ResponseEntity<>(clientManagement.deleteClient(sharedKey), HttpStatus.OK);
    }

    @PutMapping(value = "/{sharedKey}", headers = "Accept=application/json")
    public ResponseEntity<?> updatelient(@PathVariable String sharedKey, @RequestBody ClientDTO clientDTO) throws Exception {

        return new ResponseEntity<>(clientManagement.updateClient(sharedKey, clientDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/export", headers = "Accept=application/json")
    public ResponseEntity<?> exportClients() throws Exception {

        try {
            boolean export = clientManagement.exportCsv(clientManagement.findAll(), "clients.csv");

            if (export) {
                return new ResponseEntity<>("Export succesfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("An error occurred during the export", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error while exporting: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/csv")
    public ResponseEntity<?> downloadCsv() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "clients.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/csv");


        return new ResponseEntity<>(clientManagement.downloadCsv(), headers, HttpStatus.OK);
    }
}



