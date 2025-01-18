package com.cbse.ehealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbse.ehealth.model.Invoice;
import com.cbse.ehealth.model.Payroll;
import com.cbse.ehealth.repository.InvoiceRepository;
import com.cbse.ehealth.repository.PayrollRepository;
import com.cbse.ehealth.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private PayrollRepository payrollRepository;

	// List all invoices
	@GetMapping("/invoices")
	public ResponseEntity<List<Invoice>> listAllInvoices() {
		List<Invoice> invoices = invoiceRepository.findAll();
		return ResponseEntity.ok(invoices);
	}

	// List all payrolls
	@GetMapping("/payrolls")
	public ResponseEntity<List<Payroll>> listAllPayrolls() {
		List<Payroll> payrolls = payrollRepository.findAll();
		return ResponseEntity.ok(payrolls);
	}

	@PostMapping("/invoice/{id}")
	public ResponseEntity<String> processInvoicePayment(@PathVariable("id") Long invoiceId,
			@RequestParam("amountPaid") Float amountPaid) {
		String result = paymentService.processInvoicePayment(invoiceId, amountPaid);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/payroll/{id}")
	public ResponseEntity<String> processPayrollPayment(@PathVariable("id") Long payrollId,
			@RequestParam("amountPaid") Float amountPaid) {
		String result = paymentService.processPayrollPayment(payrollId, amountPaid);
		return ResponseEntity.ok(result);
	}
}
