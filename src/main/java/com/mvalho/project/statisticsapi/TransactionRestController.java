package com.mvalho.project.statisticsapi;

import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionRestController {
    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
    }

}
