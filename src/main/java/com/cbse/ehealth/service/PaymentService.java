package com.cbse.ehealth.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbse.ehealth.model.Invoice;
import com.cbse.ehealth.model.Patient;
import com.cbse.ehealth.model.Payroll;
import com.cbse.ehealth.model.Staff;
import com.cbse.ehealth.repository.InvoiceRepository;
import com.cbse.ehealth.repository.PatientRepository;
import com.cbse.ehealth.repository.PayrollRepository;
import com.cbse.ehealth.repository.StaffRepository;

@Service
public class PaymentService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private PayrollRepository payrollRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private StaffRepository staffRepository;

	public void manageInvoices(Scanner scanner) {
		while (true) {
			System.out.println("\n1 - View Invoices");
			System.out.println("2 - Add Invoice");
			System.out.println("3 - Edit Invoice");
			System.out.println("4 - Delete Invoice");
			System.out.println("5 - Back to Main Menu");
			System.out.print("Choose an option: ");
			int invoiceManagementchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (invoiceManagementchoice == 1) {
				for (Invoice invoice : invoiceRepository.findAll()) {
					System.out.println(invoice.toString());
				}
			} else if (invoiceManagementchoice == 2) {

				Invoice invoice = new Invoice();

				System.out.print("Enter patient id: ");
				long patientId = scanner.nextLong();
				Optional<Patient> patientOpt = patientRepository.findById(patientId);

				if (patientOpt.isPresent()) {
					invoice.setPatient_id(patientId);
				} else {
					System.out.println("Patient with id " + patientId + " not found.");
					continue;
				}

				System.out.print("Enter amount (RM): ");
				invoice.setAmount(scanner.nextFloat());

				scanner.nextLine();

				System.out.print("Enter date issued (YYYY-MM-DD): ");
				invoice.setDate_issued(scanner.nextLine());

				invoice.setStatus(Invoice.STATUS_PENDING);

				invoiceRepository.save(invoice);
				System.out.println("Appointment added successfully!");

			} else if (invoiceManagementchoice == 3) {

				System.out.print("Choose the id of the invoice you want to edit: ");
				long invoiceId = scanner.nextLong();
				Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);

				if (invoiceOpt.isPresent()) {
					Invoice invoice = invoiceOpt.get();
					scanner.nextLine(); // Consume newline left-over

					System.out.print("Enter patient id (" + invoice.getPatient_id() + "): ");
					long patientId = scanner.nextLong();
					Optional<Patient> patientOpt = patientRepository.findById(patientId);

					if (patientOpt.isPresent()) {
						invoice.setPatient_id(patientId);
					} else {
						System.out.println("Patient with id " + patientId + " not found.");
						continue;
					}

					System.out.print("Enter amount (" + invoice.getAmount() + "): ");
					invoice.setAmount(scanner.nextFloat());

					scanner.nextLine();

					System.out.print("Enter date issued (" + invoice.getDate_issued() + "): ");
					invoice.setDate_issued(scanner.nextLine());

					int invoiceStatusOption;

					do {

						System.out.print("Enter appointment status (" + invoice.getStatus() + "): ");
						System.out.print("\n1 - Pending \n");
						System.out.print("2 - Paid \n");
						System.out.print("3 - Unpaid \n");
						System.out.print("Enter your answer (1 - 3): ");
						invoiceStatusOption = scanner.nextInt();

						if (invoiceStatusOption == 1) {
							invoice.setStatus(Invoice.STATUS_PENDING);
						} else if (invoiceStatusOption == 2) {
							invoice.setStatus(Invoice.STATUS_PAID);
						} else if (invoiceStatusOption == 3) {
							invoice.setStatus(Invoice.STATUS_UNPAID);
						} else {
							System.out.print("Invalid options!");
						}

					} while (invoiceStatusOption != 1 && invoiceStatusOption != 2 && invoiceStatusOption != 3);

					invoiceRepository.save(invoice);
					System.out.println("Invoice edited successfully!");

				} else {
					System.out.println("Invoice with id " + invoiceId + " not found.");
				}

			} else if (invoiceManagementchoice == 4) {

				System.out.print("Choose the id of the invoice you want to delete: ");
				long invoiceId = scanner.nextLong();
				Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);

				if (invoiceOpt.isPresent()) {
					invoiceRepository.delete(invoiceOpt.get());
					System.out.println("Invoice deleted successfully!");
				} else {
					System.out.println("Invoice with id " + invoiceId + " not found.");
				}

			} else if (invoiceManagementchoice == 5) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public void managePayrolls(Scanner scanner) {
		while (true) {
			System.out.println("\n1 - View Payrolls");
			System.out.println("2 - Add Payroll");
			System.out.println("3 - Edit Payroll");
			System.out.println("4 - Delete Payroll");
			System.out.println("5 - Back to Main Menu");
			System.out.print("Choose an option: ");
			int payrollManagementchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (payrollManagementchoice == 1) {
				for (Payroll payroll : payrollRepository.findAll()) {
					System.out.println(payroll.toString());
				}
			} else if (payrollManagementchoice == 2) {

				Payroll payroll = new Payroll();

				System.out.print("Enter staff id: ");
				long staffId = scanner.nextLong();
				Optional<Staff> staffOpt = staffRepository.findById(staffId);

				if (staffOpt.isPresent()) {
					payroll.setStaff_id(staffId);
				} else {
					System.out.println("Staff with id " + staffId + " not found.");
					continue;
				}

				System.out.print("Enter salary (RM): ");
				payroll.setSalary(scanner.nextFloat());

				scanner.nextLine();

				System.out.print("Enter bonus (RM): ");
				payroll.setBonus(scanner.nextFloat());

				scanner.nextLine();

				payroll.setStatus(Payroll.STATUS_PENDING);

				payrollRepository.save(payroll);
				System.out.println("Payroll added successfully!");

			} else if (payrollManagementchoice == 3) {

				System.out.print("Choose the id of the payroll you want to edit: ");
				long payrollId = scanner.nextLong();
				Optional<Payroll> payrollOpt = payrollRepository.findById(payrollId);

				if (payrollOpt.isPresent()) {
					Payroll payroll = payrollOpt.get();
					scanner.nextLine(); // Consume newline left-over

					System.out.print("Enter staff id (" + payroll.getStaff_id() + "): ");
					long staffId = scanner.nextLong();
					Optional<Staff> staffOpt = staffRepository.findById(staffId);

					if (staffOpt.isPresent()) {
						payroll.setStaff_id(staffId);
					} else {
						System.out.println("Staff with id " + staffId + " not found.");
						continue;
					}

					System.out.print("Enter salary (" + payroll.getSalary() + "): ");
					payroll.setSalary(scanner.nextFloat());

					scanner.nextLine();

					System.out.print("Enter bonus (" + payroll.getBonus() + "): ");
					payroll.setBonus(scanner.nextFloat());

					scanner.nextLine();

					int payrollStatusOption;

					do {

						System.out.print("Enter appointment status (" + payroll.getStatus() + "): ");
						System.out.print("\n1 - Pending \n");
						System.out.print("2 - Paid \n");
						System.out.print("3 - Unpaid \n");
						System.out.print("Enter your answer (1 - 3): ");
						payrollStatusOption = scanner.nextInt();

						if (payrollStatusOption == 1) {
							payroll.setStatus(Payroll.STATUS_PENDING);
						} else if (payrollStatusOption == 2) {
							payroll.setStatus(Payroll.STATUS_PAID);
						} else if (payrollStatusOption == 3) {
							payroll.setStatus(Payroll.STATUS_UNPAID);
						} else {
							System.out.print("Invalid options!");
						}

					} while (payrollStatusOption != 1 && payrollStatusOption != 2 && payrollStatusOption != 3);

					payrollRepository.save(payroll);
					System.out.println("Payroll edited successfully!");

				} else {
					System.out.println("Payroll with id " + payrollId + " not found.");
				}

			} else if (payrollManagementchoice == 4) {

				System.out.print("Choose the id of the payroll you want to delete: ");
				long payrollId = scanner.nextLong();
				Optional<Payroll> payrollOpt = payrollRepository.findById(payrollId);

				if (payrollOpt.isPresent()) {
					payrollRepository.delete(payrollOpt.get());
					System.out.println("Payroll deleted successfully!");
				} else {
					System.out.println("Payroll with id " + payrollId + " not found.");
				}

			} else if (payrollManagementchoice == 5) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public void viewPayrolls(Scanner scanner, Object loggedInUser) {

		Staff staff = (Staff) loggedInUser;

		while (true) {
			System.out.println("\n1 - View Payrolls");
			System.out.println("2 - Back to Main Menu");
			System.out.print("Choose an option: ");

			int viewPayrollChoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (viewPayrollChoice == 1) {
				for (Payroll payroll : payrollRepository.findByStaffId(staff.getId())) {
					System.out.println(payroll.toString());
				}
			} else if (viewPayrollChoice == 2) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public String processInvoicePayment(Long invoiceId, Float amountPaid) {
		Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);
		if (optionalInvoice.isPresent()) {
			Invoice invoice = optionalInvoice.get();
			if (Invoice.STATUS_PAID.equals(invoice.getStatus())) {
				return "Invoice is already paid.";
			}
			if (amountPaid < invoice.getAmount()) {
				return "Insufficient payment amount. Invoice amount is RM " + invoice.getAmount();
			}
			invoice.setStatus(Invoice.STATUS_PAID);
			invoiceRepository.save(invoice);
			return "Invoice payment processed successfully.";
		} else {
			return "Invoice not found.";
		}
	}

	public String processPayrollPayment(Long payrollId, Float amountPaid) {
		Optional<Payroll> optionalPayroll = payrollRepository.findById(payrollId);
		if (optionalPayroll.isPresent()) {
			Payroll payroll = optionalPayroll.get();
			Float totalAmount = payroll.getSalary() + payroll.getBonus();
			if (Payroll.STATUS_PAID.equals(payroll.getStatus())) {
				return "Payroll is already paid.";
			}
			if (amountPaid < totalAmount) {
				return "Insufficient payment amount. Total payroll amount is RM " + totalAmount;
			}
			payroll.setStatus(Payroll.STATUS_PAID);
			payrollRepository.save(payroll);
			return "Payroll payment processed successfully.";
		} else {
			return "Payroll not found.";
		}
	}
}
