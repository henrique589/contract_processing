package services;

import java.time.LocalDate;

import model.Contract;
import model.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for(int i = 0; i < months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i+1);
			
			double interest = onlinePaymentService.interest(basicQuota, i+1);
			double fee = onlinePaymentService.paymentFee(basicQuota + interest);
			double quota = basicQuota + interest + fee;
			
			contract.getInstallments().add(new Installment(dueDate, quota));
		}
	}
	
}
