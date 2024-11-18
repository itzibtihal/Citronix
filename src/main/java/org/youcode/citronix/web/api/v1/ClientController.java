package org.youcode.citronix.web.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Client;
import org.youcode.citronix.services.interfaces.ClientService;
import org.youcode.citronix.web.vm.ClientVm.ClientVM;
import org.youcode.citronix.web.vm.ClientVm.ClientResponseVM;
import org.youcode.citronix.web.vm.mapper.ClientMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new client", description = "Registers a new client with name, email, and phone number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientResponseVM> createClient(@RequestBody @Valid ClientVM clientVM) {
        Client clientEntity = clientMapper.clientVMToClient(clientVM);
        Client savedClient = clientService.createClient(clientEntity.getName(), clientEntity.getEmail(), clientEntity.getPhoneNumber());
        ClientResponseVM response = clientMapper.clientToClientResponseVM(savedClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all clients (paginated)", description = "Retrieves a paginated list of all registered clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ClientResponseVM>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clientsPage = clientService.getAllClients(pageable);

        List<ClientResponseVM> responses = clientsPage.stream()
                .map(clientMapper::clientToClientResponseVM)
                .toList();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @GetMapping("/{clientId}")
    @Operation(summary = "Get a client by ID", description = "Retrieves the details of a client by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientResponseVM> getClientById(@PathVariable UUID clientId) {
        Client client = clientService.getClientById(clientId);
        ClientResponseVM response = clientMapper.clientToClientResponseVM(client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{clientId}")
    @Operation(summary = "Update a client", description = "Updates the details of an existing client by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientResponseVM> updateClient(@PathVariable UUID clientId, @RequestBody @Valid ClientVM clientVM) {
        Client updatedClient = clientService.updateClient(clientId, clientVM.getName(), clientVM.getEmail(), clientVM.getPhoneNumber());
        ClientResponseVM response = clientMapper.clientToClientResponseVM(updatedClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{clientId}")
    @Operation(summary = "Delete a client", description = "Deletes a client by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteClient(@PathVariable UUID clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }
}
