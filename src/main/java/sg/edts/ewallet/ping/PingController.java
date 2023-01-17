package sg.edts.ewallet.ping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edts.ewallet.ping.res.PingResponse;

import java.util.Date;

@RestController
@RequestMapping(path = "/ping")
public class PingController {

    @RequestMapping
    public PingResponse ping() {
        return new PingResponse("pong", new Date());
    }
}
