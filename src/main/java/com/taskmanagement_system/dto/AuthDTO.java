package com.taskmanagement_system.dto;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(
        @NotNull String userName,
        @NotNull String Password
) {
}
