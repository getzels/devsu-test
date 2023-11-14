package com.pichincha.pichinchaapi.controller;

import com.pichincha.pichinchaapi.dto.StatementOfAccountDTO;
import com.pichincha.pichinchaapi.service.AccountService;
import com.pichincha.pichinchaapi.service.PdfService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final AccountService accountService;
    private final PdfService pdfService; // Service to handle PDF generation

    @GetMapping
    public ResponseEntity<StatementOfAccountDTO> getStatementAccountAsJson(@RequestParam Long clientId,
                                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        StatementOfAccountDTO report = accountService.getStatementOfAccount(clientId, startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping(value = "/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> getStatementAccountAsPdf(@RequestParam Long clientId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        StatementOfAccountDTO report = accountService.getStatementOfAccount(clientId, startDate, endDate);
        byte[] pdfContent = pdfService.createPdf(report); // Convert DTO to PDF bytes
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", "statement.pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}


