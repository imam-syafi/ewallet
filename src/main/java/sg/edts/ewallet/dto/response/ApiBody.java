package sg.edts.ewallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiBody<T>(Status status, String message, T data) {

    public static <T> ApiBody<T> ok(T data) {
        return new ApiBody<>(Status.ok, null, data);
    }

    public static ApiBody<Void> ok() {
        return new ApiBody<>(Status.ok, null, null);
    }

    public static ApiBody<Void> error(String message) {
        return new ApiBody<>(Status.error, message, null);
    }
}
