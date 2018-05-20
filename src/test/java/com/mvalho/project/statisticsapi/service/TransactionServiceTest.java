package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import com.mvalho.project.statisticsapi.service.impl.TransactionServiceImpl;
import com.mvalho.project.statisticsapi.util.TransactionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDAO transactionDAO;

    @Before
    public void setUp() {
        this.transactionService = new TransactionServiceImpl(transactionRepository);
        this.transactionDAO.resetTransactionList();
    }

    @Test
    public void saveShouldRespondWithCode201WhenTheTransactionIsWithinTheLast60Seconds() {
        Transaction transaction = TransactionBuilder.create().withAmount(50.0).withCreatedDate(LocalDateTime.now().minusSeconds(10)).build();
        int expectedCode = 201;

        assertThat(this.transactionService.save(transaction)).hasFieldOrPropertyWithValue("responseCode", expectedCode);
    }

    @Test
    public void saveShouldRespondWithCode204WhenTheTransactionIsOutsideTheLast60Seconds() {
        Transaction transaction = TransactionBuilder.create().withAmount(50.0).withCreatedDate(LocalDateTime.now().minusSeconds(61)).build();
        int expectedCode = 204;

        assertThat(this.transactionService.save(transaction)).hasFieldOrPropertyWithValue("responseCode", expectedCode);
    }

    @Test
    public void saveShouldRespondWithCode201AndTheAddedTransactionWhenATransactionIsWithinTheLast60Second() {
        Transaction transaction = TransactionBuilder.create().withAmount(50.0).withCreatedDate(LocalDateTime.now().minusSeconds(10)).build();
        int expectedCode = 201;

        TransactionDTO expected = new TransactionDTO(expectedCode, transaction);

        assertThat(this.transactionService.save(transaction)).isEqualToComparingFieldByField(expected);

    }

    @Test
    public void saveShouldRespondWithCode204AndTheAddedTransactionWhenATransactionIsOutsideTheLast60Second() {
        Transaction transaction = TransactionBuilder.create().withAmount(50.0).withCreatedDate(LocalDateTime.now().minusSeconds(61)).build();
        int expectedCode = 204;

        TransactionDTO expected = new TransactionDTO(expectedCode, transaction);

        assertThat(this.transactionService.save(transaction)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void saveShouldIncrementTheListOfTransactionsWhenANewTransactionIsPassedForAnEmptyTransactionList() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = TransactionBuilder.create().withAmount(50.0).withCreatedDate(now.minusSeconds(10)).build();
        this.transactionService.save(transaction);

        List<Transaction> expected = Arrays.asList(transaction);

        assertThat(this.transactionRepository.getLastTransactions(now))
                .hasSize(1)
                .hasSameElementsAs(expected);

    }
}
