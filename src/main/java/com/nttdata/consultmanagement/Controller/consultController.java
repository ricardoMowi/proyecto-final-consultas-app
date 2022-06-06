package com.nttdata.consultmanagement.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import com.nttdata.consultmanagement.Dto.ProductDto;
import com.nttdata.consultmanagement.Model.Product;
import com.nttdata.consultmanagement.Model.Transaction;
import com.nttdata.consultmanagement.Service.consultService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/consults")
public class consultController {

    @Autowired
	private consultService service;

	//Formato de fecha
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH); 

    //Permitir elaborar un resumen consolidado de un cliente con todos los productos que pueda tener en el banco.
    @GetMapping("/reportProductsByCustomer/{id}")
	public Flux<Product> reportProductsByCustomer(@PathVariable("id") String id){
		return service.productsByCustomer(id);
	}


	//Generar un reporte completo y general por producto del banco en intervalo de tiempo especificado por el usuario.
	@GetMapping("/reportByProductandDateRange")
	public Flux<Transaction> reportByProductandDateRange(@RequestParam String id,@RequestParam String startDate, @RequestParam String endDate ){
		//Fechas en string a Date        
        LocalDate date = LocalDate.parse(startDate, formatter);
        Date dateA = java.sql.Date.valueOf(date);
        LocalDate date1 = LocalDate.parse(endDate, formatter);
        Date dateB = java.sql.Date.valueOf(date1);
		//Consultar servicio	
		return service.reportByProductandDateRange(id, dateA, dateB);
	}

	//Implementar un reporte con los últimos 10 movimientos de la tarjeta de débito y de crédito
	@GetMapping("/reportLastTentransactions/{id}")
	public Flux<Transaction> reportLastTentransactions(@PathVariable("id") String id ){
		//Consultar servicio	
		return service.reportLastTentransactions(id);
	}	

	//Consultar el saldo de la cuenta principal asociada a la tarjeta de débito
	@GetMapping("/balanceByDebitCard/{id}")
	public Mono<ProductDto> balanceByDebitCard(@PathVariable("id") String id ){
		//Consultar servicio	
		return service.balanceByDebitCard(id);
	}	
    
}
