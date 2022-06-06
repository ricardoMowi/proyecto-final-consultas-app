package com.nttdata.consultmanagement.Util;

import com.nttdata.consultmanagement.Dto.ProductDto;
import com.nttdata.consultmanagement.Dto.TransactionDto;
import com.nttdata.consultmanagement.Model.Product;
import com.nttdata.consultmanagement.Model.Transaction;

import org.springframework.beans.BeanUtils;

public class AppUtils {
    public static ProductDto productEntitytoDto(Product product) {
		ProductDto productDto=new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}
	
	public static Product DtoToproductEntity(ProductDto productDto) {
		Product product=new Product();
		BeanUtils.copyProperties(productDto, product);
		return product;
	}
	
	public static TransactionDto transactionEntitytoDto(Transaction trans) {
		TransactionDto transactionDto=new TransactionDto();
		BeanUtils.copyProperties(trans, transactionDto);
		return transactionDto;
	}
	
	public static Transaction DtoTotransactionEntity(TransactionDto transactionDto) {
		Transaction trans =new Transaction();
		BeanUtils.copyProperties(transactionDto, trans);
		return trans;
	}
}
