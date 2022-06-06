package com.nttdata.consultmanagement.Service;

import java.util.Date;
import java.util.List;

import com.nttdata.consultmanagement.Dto.ProductDto;
import com.nttdata.consultmanagement.Model.Product;
import com.nttdata.consultmanagement.Model.Transaction;
import com.nttdata.consultmanagement.Repository.productRepository;
import com.nttdata.consultmanagement.Repository.transactionRepository;
import com.nttdata.consultmanagement.Util.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class consultService {
    @Autowired
    private productRepository productRepository;
    @Autowired
    private transactionRepository transactionRepository; 

    public Flux<Product> productsByCustomer(String id){      
      return productRepository.findByCustomerId(id);
    }
   
    public Flux<Transaction> reportByProductandDateRange(String id, Date dateA, Date dateB){ 
      //Obtener transacciones en la fecha     
		  Flux<Transaction> transactions = transactionRepository.findByRegisterDateBetween(dateA, dateB);	
      Flux<Transaction> reports = transactions.filter(trans -> trans.getIdProduct().equals(id));
      return reports;
    }

    public Flux<Transaction> reportLastTentransactions(String id){ 
      //Obtener transacciones en la fecha
      List<Transaction> trans = transactionRepository.findByIdProduct(id).collectList().block() ;
      return Flux.fromIterable(trans);
    }  

    public Mono<ProductDto> balanceByDebitCard(String id){    
      Mono <Product> product =  productRepository.findById(id);
      Product aux = (Product) product.map(value -> { return value; }).subscribe();
      String idMainAccount = aux.getAssociatedAccounts().get(0);
      return productRepository.findById(idMainAccount).map(AppUtils::productEntitytoDto);
    }
    
}
