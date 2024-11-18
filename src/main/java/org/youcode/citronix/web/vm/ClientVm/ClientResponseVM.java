package org.youcode.citronix.web.vm.ClientVm;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientResponseVM {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
}
