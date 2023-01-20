package sg.edts.ewallet.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edts.ewallet.service.TransactionService;

@RestController
@RequestMapping(path = "/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

//    @PostMapping("/create")
//    public ResponseEntity<ApiBody<Void>> create(@Valid @RequestBody RegistrationDto payload) {
//    }
}
