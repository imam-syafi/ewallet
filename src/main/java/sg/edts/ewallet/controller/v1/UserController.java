package sg.edts.ewallet.controller.v1;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edts.ewallet.dto.request.KtpDto;
import sg.edts.ewallet.dto.request.PasswordDto;
import sg.edts.ewallet.dto.request.RegistrationDto;
import sg.edts.ewallet.dto.response.ApiBody;
import sg.edts.ewallet.dto.response.UserBalanceDto;
import sg.edts.ewallet.dto.response.UserInfoDto;
import sg.edts.ewallet.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<ApiBody<Void>> registration(@Valid @RequestBody RegistrationDto payload) {
        userService.register(payload.username(), payload.password());

        return new ResponseEntity<>(ApiBody.ok(), HttpStatus.OK);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<ApiBody<Void>> changePassword(@Valid @RequestBody PasswordDto payload) {
        userService.changePassword(payload.username(), payload.oldPassword(), payload.password());

        return new ResponseEntity<>(ApiBody.ok(), HttpStatus.OK);
    }

    @GetMapping("/{username}/getinfo")
    public ResponseEntity<ApiBody<UserInfoDto>> getInfo(@PathVariable("username") String username) {
        var data = userService.getInfo(username);
        var body = ApiBody.ok(data);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{username}/getbalance")
    public ResponseEntity<ApiBody<UserBalanceDto>> getBalance(@PathVariable("username") String username) {
        var data = userService.getBalance(username);
        var body = ApiBody.ok(data);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/{username}/addktp")
    public ResponseEntity<ApiBody<Void>> addKtp(@PathVariable("username") String username,
                                                @Valid @RequestBody KtpDto payload) {
        userService.addKtp(username, payload.ktp());

        return new ResponseEntity<>(ApiBody.ok(), HttpStatus.OK);
    }
}
