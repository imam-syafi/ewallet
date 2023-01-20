package sg.edts.ewallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record KtpDto(
        @NotBlank(message = "ktp must not be blank")
        @Pattern(
                /* Taken from:
                 * https://www.huzefril.com/posts/regex/regex-ktp
                */
                // regexp = "^(1[1-9]|21|[37][1-6]|5[1-3]|6[1-5]|[89][12])\\d{2}\\d{2}([04][1-9]|[1256][0-9]|[37][01])(0[1-9]|1[0-2])\\d{2}\\d{4}$",

                /*
                 * Take it easy, any 16-digits of number will do
                */
                regexp = "^[0-9]{16}$", // Any 16-digits of number will do
                message = "ktp must be valid"
        )
        String ktp
) {
}
