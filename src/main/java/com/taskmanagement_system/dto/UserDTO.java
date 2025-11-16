package com.taskmanagement_system.dto;

import com.taskmanagement_system.model.Role;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull(message = "Id should be mandatory")Integer id,
        @NotNull(message = "UserName should be mandatory")String userName,
        @NotNull(message = "Email is mandatory")String email,
        @NotNull(message = "Number is mandatory")String number,
        String alterNativeNumber,
        String address,
            @NotNull(message = "Role is must") Role role,
        @NotNull(message = "IsActive either true or false") boolean isActive
) {
}
