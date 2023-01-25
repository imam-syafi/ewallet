package sg.edts.ewallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SendBalanceDto(
        @NotBlank(message = "username must not be blank")
        @Size(min = 3, max = 24, message = "user not found")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "user not found")
        String username,

        @NotBlank(message = "password must not be blank")
        @Size(min = 10, message = "password invalid")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$",
                message = "password invalid"
        )
        String password,

        @NotBlank(message = "username must not be blank")
        @Size(min = 3, max = 24, message = "user not found")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "user not found")
        String destinationUsername,

        @NotNull(message = "amount must not be null")
        Long amount
) {
}
