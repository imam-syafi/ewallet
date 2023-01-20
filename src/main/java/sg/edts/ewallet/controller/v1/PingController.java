package sg.edts.ewallet.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edts.ewallet.dto.response.ApiBody;
import sg.edts.ewallet.dto.response.PongDto;

@RestController
@RequestMapping(path = "/api/v1/ping")
public class PingController {

    @RequestMapping
    public ResponseEntity<ApiBody<PongDto>> ping() {
        var pong = new PongDto();
        var body = ApiBody.ok(pong);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
