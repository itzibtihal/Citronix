package org.youcode.citronix.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.youcode.citronix.domain.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client createClient(String name, String email, String phoneNumber);

    Page<Client> getAllClients(Pageable pageable);

    Client getClientById(UUID clientId);

    Client updateClient(UUID clientId, String name, String email, String phoneNumber);

    void deleteClient(UUID clientId);
}
