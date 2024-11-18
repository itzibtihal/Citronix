package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.citronix.domain.Client;
import org.youcode.citronix.web.vm.ClientVm.ClientResponseVM;
import org.youcode.citronix.web.vm.ClientVm.ClientVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponseVM clientToClientResponseVM(Client client);

    @Mapping(target = "id", ignore = true)
    Client clientVMToClient(ClientVM clientVM);

    List<ClientResponseVM> clientsToClientResponseVMs(List<Client> clients);

}
