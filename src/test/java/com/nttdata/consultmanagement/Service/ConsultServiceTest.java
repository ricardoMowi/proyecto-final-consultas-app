package com.nttdata.consultmanagement.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.nttdata.consultmanagement.Model.Product;
import com.nttdata.consultmanagement.Model.Transaction;
import com.nttdata.consultmanagement.Repository.productRepository;
import com.nttdata.consultmanagement.Repository.transactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ConsultServiceTest {
    @Mock
	private transactionRepository transRepo;
    @Mock
	private productRepository proRepo;
	@InjectMocks
	private consultService conService;

	private List<Transaction> transList;
    private List<Product> productList;
	private Product productExample = new Product();
    private Product debitCard = new Product();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH); 
    LocalDate date = LocalDate.parse("22-05-2022", formatter);
    Date dateA = java.sql.Date.valueOf(date);
    LocalDate date2 = LocalDate.parse("10-05-2022", formatter);
    Date dateB = java.sql.Date.valueOf(date2);

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        transList = new ArrayList<>();
        Transaction trans1=Transaction.builder()
			.id("628296562738d249c56a5f01")
			.idProduct("1")
			.registerDate(dateA)
			.amount(229.00)
			.transactionCommission(5.00)
			.flagWithCommission(true)
			.transactionType("DEPOSIT")
			.status("ACTIVE")
			.newDailyBalance(null)
			.idDestinationAccount(null)
			.idCustomer(null)
			.build();
			
		Transaction trans2=Transaction.builder()
			.id("628296562738d249c56a5f02")
			.idProduct("1")
			.registerDate(dateA)
			.amount(20.00)
			.transactionCommission(0.00)
			.flagWithCommission(false)
			.transactionType("BANK_WHITDRAWALL")
			.status("ACTIVE")
			.newDailyBalance(null)
			.idDestinationAccount(null)
			.idCustomer(null)
			.build();

		Transaction trans3=Transaction.builder()
			.id("628296562738d249c56a5f03")
			.idProduct("1")
			.registerDate(dateA)
			.amount(300.00)
			.transactionCommission(0.00)
			.flagWithCommission(false)
			.transactionType("DEPOSIT")
			.status("ACTIVE")
			.newDailyBalance(null)
			.idDestinationAccount(null)
			.idCustomer(null)
			.build();

		transList.add(trans1);
		transList.add(trans2);
		transList.add(trans3);

		Product pro1 = Product.builder()
            .id("1")
            .customerId("628fcd90674477ba7343f335")
            .creationDate(dateB)
            .transactionDate(null)
            .maximumTransactionLimit(3)
            .numberOfFreeTransactions(1)
            .maintenanceCommission(5.00)
            .amount(1000.00)
            .productType("SAVING_ACCOUNT")
            .status("ACTIVE")
            .owners(null)
            .authorizedSigner(null)
            .build();

        productExample = pro1;

        Product pro2 = Product.builder()
            .id("2")
            .customerId("628fcd90674477ba7343f335")
            .creationDate(dateB)
            .transactionDate(null)
            .maximumTransactionLimit(10)
            .numberOfFreeTransactions(10)
            .maintenanceCommission(5.00)
            .amount(1000.00)
            .productType("CURRENT_ACCOUNT")
            .status("ACTIVE")
            .owners(null)
            .authorizedSigner(null)
            .build();

        List <String> accountsId = new  ArrayList<>();
        accountsId.add("2");

        Product pro3 = Product.builder()
            .id("3")
            .customerId("628fcd90674477ba7343f335")
            .creationDate(dateB)
            .transactionDate(null)
            .maximumTransactionLimit(0)
            .numberOfFreeTransactions(0)
            .maintenanceCommission(0.00)
            .amount(1000.00)
            .productType("DEBIT_CARD")
            .status("ACTIVE")
            .owners(null)
            .authorizedSigner(null)
            .associatedAccounts(accountsId)
            .build();
        debitCard = pro3;

        productList.add(pro1);
        productList.add(pro2);
        productList.add(pro3);
    }


			
	@Test
	void productsByCustomerTest() {
        String id="628fcd90674477ba7343f335";
		when(proRepo.findByCustomerId(id)).thenReturn(Flux.fromIterable(productList));
		assertNotNull(conService.productsByCustomer(id));
	}

	@Test
    void reportByProductandDateRangeTest() {
        String id ="1";
        LocalDate date_ = LocalDate.parse("10-05-2022", formatter);
        Date dateA_ = java.sql.Date.valueOf(date_);
        LocalDate date1_ = LocalDate.parse("25-05-2022", formatter);
        Date dateB_ = java.sql.Date.valueOf(date1_);
        when(transRepo.findByRegisterDateBetween(dateA_, dateB_)).thenReturn(Flux.fromIterable(transList));
        assertNotNull(conService.reportByProductandDateRange(id, dateA_, dateB_));
    }

	@Test
	void reportLastTentransactionsTest() {
		String id = "628296562738d249c56a5fe0";
		when(transRepo.findByIdProduct(id)).thenReturn((Flux.fromIterable(transList)));
		assertNotNull(conService.reportLastTentransactions(id));
	}

    @Test
	void balanceByDebitCardTest() {
		String id = "3";
        String idMainAcount = "2";
		when(proRepo.findById(id)).thenReturn(Mono.just(debitCard));
        when(proRepo.findById(idMainAcount)).thenReturn(Mono.just(productExample));
		assertNotNull(conService.balanceByDebitCard(id));
	}
}
