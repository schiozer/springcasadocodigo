package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

public class PaymentData {

	private BigDecimal value;

	public PaymentData() {
		super();
	}
	
	public PaymentData(BigDecimal value) {
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
}
