package com.nttdata.consultmanagement.Model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Document(collection="customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Transaction {
    @Id
    private String id;
    private Date registerDate;
    private String idProduct;
    private Double amount;
    private Double transactionCommission;
    private boolean flagWithCommission;
    private String idDestinationAccount;
    private String transactionType;
    private String status;
    private Double newDailyBalance;
    private String idCustomer;
}
