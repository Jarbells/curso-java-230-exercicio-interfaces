package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService paymentService;
	
	public void processContract(Contract contract, Integer months, OnlinePaymentService onlinePaymentService) {
		this.paymentService = onlinePaymentService;
		double valuePerMonth = contract.getTotalValue() / months;
		
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= months; i++) {
			cal.setTime(contract.getDate());
			cal.add(Calendar.MONTH, i);
			Date nextMonth = cal.getTime(); // Sempre lembre de fazer o getTime() para usar a nova data.
			
			double interest = paymentService.interest(valuePerMonth, i);
			double fee = paymentService.paymentFee(interest);
			
			contract.addInstallment(new Installment(nextMonth, interest + fee));
		}
	}
}
