package com.taskmanagement_system.dto;

import jakarta.validation.Valid;

public record AuthAndUpdateDTO(
        @Valid AuthDTO authDTO,
        @Valid UserDTO userDTO
) {
}
