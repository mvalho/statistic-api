package com.mvalho.project.statisticsapi;

import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
public class TransactionRestController {
    private TransactionService transactionService;

    @Autowired
    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        LocalDateTime created = LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionDTO.getTimestamp()), ZoneId.of("+0"));
        TransactionDTO saved = this.transactionService.save(new Transaction(new BigDecimal(transactionDTO.getAmount()), created));

        return new ResponseEntity<>(saved, HttpStatus.valueOf(saved.getResponseCode()));
    }

}
