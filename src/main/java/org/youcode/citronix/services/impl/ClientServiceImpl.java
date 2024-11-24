package org.youcode.citronix.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.youcode.citronix.domain.Client;
import org.youcode.citronix.repositories.ClientRepository;
import org.youcode.citronix.services.interfaces.ClientService;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(String name, String email, String phoneNumber) {

        if (clientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("A client with this email already exists.");
        }


        Client client = Client.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        return clientRepository.save(client);
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client getClientById(UUID clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    @Override
    public Client updateClient(UUID clientId, String name, String email, String phoneNumber) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));


        if (!client.getEmail().equals(email) && clientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("A client with this email already exists.");
        }

        client.setName(name);
        client.setEmail(email);
        client.setPhoneNumber(phoneNumber);

        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(UUID clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new IllegalArgumentException("Client not found");
        }
        clientRepository.deleteById(clientId);
    }
}
