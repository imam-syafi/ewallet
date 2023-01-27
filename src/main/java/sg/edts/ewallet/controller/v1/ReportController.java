package sg.edts.ewallet.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edts.ewallet.dto.response.ApiBody;
import sg.edts.ewallet.dto.response.ReportGetDto;
import sg.edts.ewallet.service.ReportService;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/v1/report")
public class ReportController {

    final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/getreport/{date}")
    public ResponseEntity<ApiBody<ReportGetDto>> getReport(@PathVariable LocalDate date) {
        var body = reportService.generateReport(date);

        return new ResponseEntity<>(ApiBody.ok(body), HttpStatus.OK);
    }
}
