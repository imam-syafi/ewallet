package sg.edts.ewallet.dto.response;

import java.util.Date;

public record PongDto(String message, Date timestamp) {

    public PongDto() {
        this("pong", new Date());
    }
}
