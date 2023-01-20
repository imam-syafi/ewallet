package sg.edts.ewallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationDto(
        @NotBlank(message = "username must not be blank")
        @Size(min = 3, max = 24, message = "username length must be between 3 and 24")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "username must be alphanumeric and can't start with a number")
        String username,

        @NotBlank(message = "password must not be blank")
        @Size(min = 10, message = "password must be 10 characters or more")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$",
                message = "password must be a combination of letters, numbers, and symbols"
        )
        String password
) {
}
