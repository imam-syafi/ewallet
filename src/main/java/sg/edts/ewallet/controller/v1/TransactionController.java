package sg.edts.ewallet.controller.v1;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edts.ewallet.dto.request.SendBalanceDto;
import sg.edts.ewallet.dto.request.TopUpDto;
import sg.edts.ewallet.dto.response.ApiBody;
import sg.edts.ewallet.dto.response.BalanceSentDto;
import sg.edts.ewallet.service.TransactionService;

@RestController
@RequestMapping(path = "/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<ApiBody<BalanceSentDto>> create(@Valid @RequestBody SendBalanceDto payload) {
        var data = transactionService.create(payload);
        var body = ApiBody.ok(data);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/topup")
    public ResponseEntity<ApiBody<Void>> topup(@Valid @RequestBody TopUpDto payload) {
        transactionService.topUp(payload);

        return new ResponseEntity<>(ApiBody.ok(), HttpStatus.OK);
    }
}
