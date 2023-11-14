package com.pichincha.pichinchaapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDTO extends PersonDTO {
    @NotEmpty
    private String clientId;
    @NotNull
    private Boolean active;
}

