package com.cbse.ehealth.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbse.ehealth.model.Admin;
import com.cbse.ehealth.model.Item;
import com.cbse.ehealth.model.Medicine;
import com.cbse.ehealth.model.Staff;
import com.cbse.ehealth.model.SupplyRequest;
import com.cbse.ehealth.repository.ItemRepository;
import com.cbse.ehealth.repository.MedicineRepository;
import com.cbse.ehealth.repository.SupplyRequestRepository;

@Service
public class SupplyService {

	@Autowired
	private SupplyRequestRepository supplyRequestRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	public void manageSupplyRequests(Scanner scanner, Object loggedInUser) {
		while (true) {
			System.out.println("\n1 - View Supply Requests");
			System.out.println("2 - Add Supply Request");
			System.out.println("3 - Edit Supply Request");
			System.out.println("4 - Delete Supply Request");
			System.out.println("5 - Back to Main Menu");
			System.out.print("Choose an option: ");
			int supplyRequestManagementchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (supplyRequestManagementchoice == 1) {
				for (SupplyRequest supplyRequest : supplyRequestRepository.findAll()) {
					System.out.println(supplyRequest.toString());
				}
			} else if (supplyRequestManagementchoice == 2) {

				SupplyRequest supplyRequest = new SupplyRequest();

				Staff staff = (Staff) loggedInUser;

				supplyRequest.setStaff_id(staff.getId());

				// Process item_id
				System.out.print("Enter item id: ");
				String itemInput = scanner.nextLine().trim();

				if (!itemInput.isEmpty()) {
					try {
						long itemId = Long.parseLong(itemInput);
						Optional<Item> itemOpt = itemRepository.findById(itemId);

						if (itemOpt.isPresent()) {
							supplyRequest.setItem_id(itemId);
						} else {
							System.out.println("Item with id " + itemId + " not found.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Item id must be a number.");
					}
				} else {
					supplyRequest.setItem_id(null); // Set to null if input is empty
				}

				// Process medicine_id
				System.out.print("Enter medicine id: ");
				String medicineInput = scanner.nextLine().trim();

				if (!medicineInput.isEmpty()) {
					try {
						long medicineId = Long.parseLong(medicineInput);
						Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);

						if (medicineOpt.isPresent()) {
							supplyRequest.setMedicine_id(medicineId);
						} else {
							System.out.println("Medicine with id " + medicineId + " not found.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Medicine id must be a number.");
					}
				} else {
					supplyRequest.setMedicine_id(null); // Set to null if input is empty
				}

				System.out.print("Enter quantity requested: ");
				supplyRequest.setQuantity_requested(scanner.nextLine());

				System.out.print("Enter request date (YYYY-MM-DD): ");
				supplyRequest.setRequest_date(scanner.nextLine());

				supplyRequest.setStatus(SupplyRequest.STATUS_PENDING);

				supplyRequestRepository.save(supplyRequest);
				System.out.println("Supply Request added successfully!");

			} else if (supplyRequestManagementchoice == 3) {

				System.out.print("Choose the id of the supply request you want to edit: ");
				long supplyRequestId = scanner.nextLong();
				Optional<SupplyRequest> supplyRequestOpt = supplyRequestRepository.findById(supplyRequestId);

				if (supplyRequestOpt.isPresent()) {
					SupplyRequest supplyRequest = supplyRequestOpt.get();
					scanner.nextLine(); // Consume newline left-over

					Staff staff = (Staff) loggedInUser;

					supplyRequest.setStaff_id(staff.getId());

					// Process item_id
					System.out.print("Enter item id (" + supplyRequest.getItem_id() + "): ");
					String itemInput = scanner.nextLine().trim();

					if (!itemInput.isEmpty()) {
						try {
							long itemId = Long.parseLong(itemInput);
							Optional<Item> itemOpt = itemRepository.findById(itemId);

							if (itemOpt.isPresent()) {
								supplyRequest.setItem_id(itemId);
							} else {
								System.out.println("Item with id " + itemId + " not found.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Item id must be a number.");
						}
					} else {
						supplyRequest.setItem_id(null); // Set to null if input is empty
					}

					// Process medicine_id
					System.out.print("Enter medicine id (" + supplyRequest.getMedicine_id() + "): ");
					String medicineInput = scanner.nextLine().trim();

					if (!medicineInput.isEmpty()) {
						try {
							long medicineId = Long.parseLong(medicineInput);
							Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);

							if (medicineOpt.isPresent()) {
								supplyRequest.setMedicine_id(medicineId);
							} else {
								System.out.println("Medicine with id " + medicineId + " not found.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Medicine id must be a number.");
						}
					} else {
						supplyRequest.setMedicine_id(null); // Set to null if input is empty
					}

					System.out.print("Enter quantity requested (" + supplyRequest.getQuantity_requested() + "): ");
					supplyRequest.setQuantity_requested(scanner.nextLine());

					System.out.print("Enter request date (" + supplyRequest.getRequest_date() + "): ");
					supplyRequest.setRequest_date(scanner.nextLine());

					supplyRequest.setStatus(SupplyRequest.STATUS_PENDING);

					supplyRequestRepository.save(supplyRequest);
					System.out.println("Supply Request edited successfully!");

				} else {
					System.out.println("Supply request with id " + supplyRequestId + " not found.");
				}

			} else if (supplyRequestManagementchoice == 4) {

				System.out.print("Choose the id of the supply request you want to delete: ");
				long supplyRequestId = scanner.nextLong();
				Optional<SupplyRequest> supplyRequestOpt = supplyRequestRepository.findById(supplyRequestId);

				if (supplyRequestOpt.isPresent()) {
					supplyRequestRepository.delete(supplyRequestOpt.get());
					System.out.println("Supply request deleted successfully!");
				} else {
					System.out.println("Supply request with id " + supplyRequestId + " not found.");
				}

			} else if (supplyRequestManagementchoice == 5) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public void confirmSupplyRequest(Scanner scanner, Object loggedInUser) {

		Admin admin = (Admin) loggedInUser;

		while (true) {

			System.out.println("\n1 - View Supply Requests");
			System.out.println("2 - Confirm Supply Request");
			System.out.println("3 - Back to Main Menu");
			System.out.print("Choose an option: ");

			int confirmSupplyRequestchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (confirmSupplyRequestchoice == 1) {
				for (SupplyRequest supplyRequest : supplyRequestRepository.findAll()) {
					System.out.println(supplyRequest.toString());
				}
			} else if (confirmSupplyRequestchoice == 2) {

				System.out.print("\nChoose the id of the supply request you want to confirm: ");
				long supplyRequestId = scanner.nextLong();
				Optional<SupplyRequest> supplyRequestOpt = supplyRequestRepository.findById(supplyRequestId);

				if (supplyRequestOpt.isPresent()) {

					SupplyRequest supplyRequest = supplyRequestOpt.get();

					int confirmSupplyRequestOption;

					do {

						System.out.print("Confirming appointment: ");
						System.out.print("\n1 - Approved \n");
						System.out.print("2 - Rejected \n");
						System.out.print("Enter your answer (1 - 2): ");
						confirmSupplyRequestOption = scanner.nextInt();

						if (confirmSupplyRequestOption == 1) {
							supplyRequest.setStatus(SupplyRequest.STATUS_APPROVED);
						} else if (confirmSupplyRequestOption == 2) {
							supplyRequest.setStatus(SupplyRequest.STATUS_REJECTED);
						} else {
							System.out.print("Invalid options!");
						}

					} while (confirmSupplyRequestOption != 1 && confirmSupplyRequestOption != 2);

					supplyRequest.setAdmin_id(admin.getId());

					supplyRequestRepository.save(supplyRequest);
					System.out.println("Supply Request updated successfully!");

				} else {
					System.out.println("Supply request with id " + supplyRequestId + " not found.");
				}

			} else if (confirmSupplyRequestchoice == 3) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}
}
