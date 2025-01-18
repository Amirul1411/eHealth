package com.cbse.ehealth;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cbse.ehealth.model.Admin;
import com.cbse.ehealth.model.Item;
import com.cbse.ehealth.model.Medicine;
import com.cbse.ehealth.model.Patient;
import com.cbse.ehealth.model.Staff;
import com.cbse.ehealth.repository.AdminRepository;
import com.cbse.ehealth.repository.AppointmentRepository;
import com.cbse.ehealth.repository.InvoiceRepository;
import com.cbse.ehealth.repository.ItemRepository;
import com.cbse.ehealth.repository.MedicineRepository;
import com.cbse.ehealth.repository.PatientRepository;
import com.cbse.ehealth.repository.PayrollRepository;
import com.cbse.ehealth.repository.ShiftRepository;
import com.cbse.ehealth.repository.StaffRepository;
import com.cbse.ehealth.repository.SupplyRequestRepository;
import com.cbse.ehealth.service.PaymentService;
import com.cbse.ehealth.service.ScheduleService;
import com.cbse.ehealth.service.SupplyService;

@SpringBootApplication
public class EhealthApplication {

//	private final PaymentComponent paymentComponent;
//	private final ScheduleComponent scheduleComponent;
//	private final SupplyComponent supplyComponent;
//
//	public EhealthApplication(PaymentComponent paymentComponent, ScheduleComponent scheduleComponent,
//			SupplyComponent supplyComponent) {
//		this.paymentComponent = paymentComponent;
//		this.scheduleComponent = scheduleComponent;
//		this.supplyComponent = supplyComponent;
//	}

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private SupplyService supplyService;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private PayrollRepository payrollRepository;

	@Autowired
	private ShiftRepository shiftRepository;

	@Autowired
	private SupplyRequestRepository supplyRequestRepository;

	public static void main(String[] args) {
		SpringApplication.run(EhealthApplication.class, args);
	}

	@Bean
	public CommandLineRunner startApplication() {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("\nWelcome to the eHealth System!");
				System.out.println("Please choose an option:");
				System.out.println("1 - Register");
				System.out.println("2 - Login");
				System.out.print("Your choice: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline character

				if (choice == 1) {
					while (true) {
						System.out.println("\nRegister Page");
						System.out.println("Who do you want to register as?\nPlease choose an option:");
						System.out.println("1 - Admin");
						System.out.println("2 - Staff");
						System.out.println("3 - Back to front page");
						System.out.print("Your choice: ");

						int registerChoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline character

						if (registerChoice == 1) {
							registerUser("Admin", adminRepository, scanner);
							break;
						} else if (registerChoice == 2) {
							registerUser("Staff", staffRepository, scanner);
							break;
						} else if (registerChoice == 3) {
							break;
						} else {
							System.out.println("Invalid choice. Please enter 1, 2, or 3.");
						}
					}
				} else if (choice == 2) {
					while (true) {
						System.out.println("\nLogin Page");
						System.out.println("Who do you want to log in as?\nPlease choose an option:");
						System.out.println("1 - Admin");
						System.out.println("2 - Staff");
						System.out.println("3 - Back to front page");
						System.out.print("Your choice: ");

						int loginChoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline character

						if (loginChoice == 1) {
							Optional<?> loggedInAdmin = loginUser("Admin", adminRepository);
							if (loggedInAdmin.isPresent()) {
								System.out.println("Welcome, Admin!");
								mainMenu(scanner, adminRepository, loggedInAdmin.get());
							}
						} else if (loginChoice == 2) {
							Optional<?> loggedInStaff = loginUser("Staff", staffRepository);
							if (loggedInStaff.isPresent()) {
								System.out.println("Welcome, Staff!");
								mainMenu(scanner, staffRepository, loggedInStaff.get());
							}
						} else if (loginChoice == 3) {
							break;
						} else {
							System.out.println("Invalid choice. Please enter 1, 2, or 3.");
						}
					}
				} else {
					System.out.println("Invalid choice. Please enter 1 or 2.");
				}
			}
		};
	}

	private void registerUser(String userType, JpaRepository<?, Long> repository, Scanner scanner) {

		if ("Admin".equalsIgnoreCase(userType)) {
			System.out.print("Enter admin username: ");
			String name = scanner.nextLine();

			System.out.print("Enter admin password: ");
			String password = scanner.nextLine();

			Admin admin = new Admin(name, password);

			// Save the admin to the repository
			((AdminRepository) repository).save(admin);
			System.out.println("Admin registered successfully!");
		} else if ("Staff".equalsIgnoreCase(userType)) {

			System.out.print("Enter staff username: ");
			String name = scanner.nextLine();

			System.out.print("Enter staff password: ");
			String password = scanner.nextLine();

			int roleOption;
			String role = "";

			do {
				System.out.print("What is your role? \n");
				System.out.print("1 - Staff \n");
				System.out.print("2 - Doctor \n");
				System.out.print("3 - Nurse \n");
				System.out.print("Enter your answer (1 - 3): ");
				roleOption = scanner.nextInt();

				if (roleOption == 1) {
					role = Staff.ROLE_STAFF;
				} else if (roleOption == 2) {
					role = Staff.ROLE_DOCTOR;
				} else if (roleOption == 3) {
					role = Staff.ROLE_NURSE;
				} else {
					System.out.print("Invalid option!");
				}
			} while (roleOption != 1 && roleOption != 2 && roleOption != 3);

			int departmentOption;
			String department = "";

			do {

				System.out.print("What is your department? ");
				System.out.print("\n1 - Cardiology \n");
				System.out.print("2 - Neurology \n");
				System.out.print("3 - Pediatrics \n");
				System.out.print("Enter your answer (1 - 3): ");
				departmentOption = scanner.nextInt();

				scanner.nextLine();

				if (departmentOption == 1) {
					department = Staff.DEPARTMENT_CARDIOLOGY;
				} else if (departmentOption == 2) {
					department = Staff.DEPARTMENT_NEUROLOGY;
				} else if (departmentOption == 3) {
					department = Staff.DEPARTMENT_PEDIATRICS;
				} else {
					System.out.print("Invalid options!");
				}

			} while (departmentOption != 1 && departmentOption != 2 && departmentOption != 3);

			System.out.print("Enter staff contact: ");
			String contact = scanner.nextLine();

			Staff staff = new Staff(name, password, role, department, contact);

			// Save the staff to the repository
			((StaffRepository) repository).save(staff);
			System.out.println("Staff registered successfully!");

		} else {
			System.out.println("Invalid user type!");
		}
	}

	private Optional<?> loginUser(String userType, JpaRepository<?, Long> repository) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nLogin as " + userType + ":");
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();

		System.out.print("Enter your password: ");
		String password = scanner.nextLine();

		if ("Admin".equalsIgnoreCase(userType)) {
			Optional<Admin> optionalAdmin = ((AdminRepository) repository).findByUsername(username);

			if (optionalAdmin.isPresent() && optionalAdmin.get().getPassword().equals(password)) {
				System.out.println("Admin login successful!");
				return optionalAdmin;
			} else {
				System.out.println("Invalid username or password for Admin. Please try again.");
			}
		} else if ("Staff".equalsIgnoreCase(userType)) {
			Optional<Staff> optionalStaff = ((StaffRepository) repository).findByUsername(username);

			if (optionalStaff.isPresent() && optionalStaff.get().getPassword().equals(password)) {
				System.out.println("Staff login successful!");
				return optionalStaff;
			} else {
				System.out.println("Invalid username or password for Staff. Please try again.");
			}
		} else {
			System.out.println("Invalid user type!");
		}

		return Optional.empty();
	}

	private void mainMenu(Scanner scanner, Object userRepository, Object loggedInUser) {
		while (true) {
			if (loggedInUser instanceof Admin) {

				System.out.println("\nMain Menu:");
				System.out.println("1 - Profile Page");
				System.out.println("2 - Staff Management");
				System.out.println("3 - Item Management");
				System.out.println("4 - Logout");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					profilePage(scanner, userRepository, loggedInUser);
				} else if (choice == 2) {
					staffManagementPage(scanner, loggedInUser);
				} else if (choice == 3) {
					itemManagementPage(scanner, loggedInUser);
				} else if (choice == 4) {
					System.out.println("\nLogging out...");
					return;
				} else {
					System.out.println("Invalid choice. Please try again.");
				}

			} else if (loggedInUser instanceof Staff) {

				Staff staff = (Staff) loggedInUser;

				if (staff.getRole().equals(Staff.ROLE_STAFF)) {
					System.out.println("\nMain Menu:");
					System.out.println("1 - Profile Page");
					System.out.println("2 - Patient Management");
					System.out.println("3 - Item Management");
					System.out.println("4 - Staff Management");
					System.out.println("5 - Logout");
					System.out.print("Choose an option: ");

					int choice = scanner.nextInt();
					scanner.nextLine(); // Consume newline

					if (choice == 1) {
						profilePage(scanner, userRepository, loggedInUser);
					} else if (choice == 2) {
						patientManagementPage(scanner, loggedInUser);
					} else if (choice == 3) {
						itemManagementPage(scanner, loggedInUser);
					} else if (choice == 4) {
						staffManagementPage(scanner, loggedInUser);
					} else if (choice == 5) {
						System.out.println("\nLogging out...");
						return;
					} else {
						System.out.println("Invalid choice. Please try again.");
					}
				} else {
					System.out.println("\nMain Menu:");
					System.out.println("1 - Profile Page");
					System.out.println("2 - Patient Management");
					System.out.println("3 - View shifts");
					System.out.println("4 - Logout");
					System.out.print("Choose an option: ");

					int choice = scanner.nextInt();
					scanner.nextLine(); // Consume newline

					if (choice == 1) {
						profilePage(scanner, userRepository, loggedInUser);
					} else if (choice == 2) {
						patientManagementPage(scanner, loggedInUser);
					} else if (choice == 3) {
						staffManagementPage(scanner, loggedInUser);
					} else if (choice == 4) {
						System.out.println("\nLogging out...");
						return;
					} else {
						System.out.println("Invalid choice. Please try again.");
					}
				}

			}
		}
	}

	private void profilePage(Scanner scanner, Object userRepository, Object loggedInUser) {
		while (true) {
			if (loggedInUser instanceof Admin) {

				Admin admin = (Admin) loggedInUser;
				AdminRepository adminRepository = (AdminRepository) userRepository;

				System.out.println("\nProfile Page");
				System.out.println("1 - View Profile");
				System.out.println("2 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					System.out.println();

					// Display current profile information
					System.out.println("Profile Information:");
					System.out.printf("Username: %s%nPassword: %s%n", admin.getUsername(), admin.getPassword());

					// Provide options to edit the profile or go back to the main menu
					System.out.println("\n1 - Edit Profile");
					System.out.println("2 - Back to Main Menu");
					System.out.println("Choose an option:");
					int adminProfileChoice = scanner.nextInt();
					scanner.nextLine(); // Consume newline left-over

					if (adminProfileChoice == 1) {
						// Handle profile edit
						System.out.print("Enter new username (" + admin.getUsername() + "): ");
						admin.setUsername(scanner.nextLine());

						System.out.print("Enter new password (" + admin.getPassword() + "): ");
						admin.setPassword(scanner.nextLine());

						// Save the updated user profile to the repository
						adminRepository.save(admin);
						System.out.println("Profile updated successfully!");
					} else if (adminProfileChoice == 2) {
						// Go back to the main menu (just exit the current method to return to main menu
						// logic)
						System.out.println("Returning to Main Menu...");
					} else {
						// Handle invalid input
						System.out.println("Invalid choice. Please enter 1 to edit or 2 to go back to the main menu.");
					}

				} else if (choice == 2) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}

			} else if (loggedInUser instanceof Staff) {

				Staff staff = (Staff) loggedInUser;
				StaffRepository staffRepository = (StaffRepository) userRepository;

				System.out.println("\nProfile Page");
				System.out.println("1 - View Profile");
				System.out.println("2 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					System.out.println();

					// Display current profile information
					System.out.println("Profile Information:");
					System.out.printf("Username: %s%nPassword: %s%nRole: %s%nDepartment: %s%nContact: %s%n",
							staff.getUsername(), staff.getPassword(), staff.getRole(), staff.getDepartment(),
							staff.getContact());

					// Provide options to edit the profile or go back to the main menu
					System.out.println("\n1 - Edit Profile");
					System.out.println("2 - Back to Main Menu");
					System.out.println("Choose an option:");
					int staffProfileChoice = scanner.nextInt();
					scanner.nextLine(); // Consume newline left-over

					if (staffProfileChoice == 1) {
						// Handle profile edit
						System.out.print("Enter new username (" + staff.getUsername() + "): ");
						staff.setUsername(scanner.nextLine());

						System.out.print("Enter new password (" + staff.getPassword() + "): ");
						staff.setPassword(scanner.nextLine());

						int roleOption;

						do {
							System.out.print("Enter new role (" + staff.getRole() + ")\n");
							System.out.print("1 - Staff \n");
							System.out.print("2 - Doctor \n");
							System.out.print("3 - Nurse \n");
							System.out.print("Enter your answer (1 - 3): ");
							roleOption = scanner.nextInt();

							if (roleOption == 1) {
								staff.setRole(Staff.ROLE_STAFF);
							} else if (roleOption == 2) {
								staff.setRole(Staff.ROLE_DOCTOR);
							} else if (roleOption == 3) {
								staff.setRole(Staff.ROLE_NURSE);
							} else {
								System.out.print("Invalid option!");
							}
						} while (roleOption != 1 && roleOption != 2 && roleOption != 3);

						int departmentOption;

						do {

							System.out.print("What is your department? (" + staff.getDepartment() + ") \n");
							System.out.print("1 - Cardiology \n");
							System.out.print("2 - Neurology \n");
							System.out.print("3 - Pediatrics \n");
							System.out.print("Enter your answer (1 - 3): ");
							departmentOption = scanner.nextInt();

							if (departmentOption == 1) {
								staff.setDepartment(Staff.DEPARTMENT_CARDIOLOGY);
							} else if (departmentOption == 2) {
								staff.setDepartment(Staff.DEPARTMENT_NEUROLOGY);
							} else if (departmentOption == 3) {
								staff.setDepartment(Staff.DEPARTMENT_PEDIATRICS);
							} else {
								System.out.print("Invalid options!");
							}

						} while (departmentOption != 1 && departmentOption != 2 && departmentOption != 3);

						scanner.nextLine();

						System.out.println("Enter new contact (" + staff.getContact() + "): ");
						staff.setContact(scanner.nextLine());

						// Save the updated user profile to the repository
						staffRepository.save(staff);
						System.out.println("Profile updated successfully!");
					} else if (staffProfileChoice == 2) {
						// Go back to the main menu (just exit the current method to return to main menu
						// logic)
						System.out.println("Returning to Main Menu...");
					} else {
						// Handle invalid input
						System.out.println("Invalid choice. Please enter 1 to edit or 2 to go back to the main menu.");
					}

				} else if (choice == 2) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}

			}

		}
	}

	private void patientManagementPage(Scanner scanner, Object loggedInUser) {

		Staff staff = (Staff) loggedInUser;

		while (true) {
			if (staff.getRole().equals(Staff.ROLE_STAFF)) {
				System.out.println("\nPatient Management Page.");
				System.out.println("1 - Manage Patients");
				System.out.println("2 - Manage Appointments");
				System.out.println("3 - Manage Invoices");
				System.out.println("4 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					while (true) {
						System.out.println("\n1 - View Patients");
						System.out.println("2 - Add Patient");
						System.out.println("3 - Edit Patient");
						System.out.println("4 - Delete Patient");
						System.out.println("5 - Back to Main Menu");
						System.out.print("Choose an option: ");
						int patientManagementchoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline left-over

						if (patientManagementchoice == 1) {
							for (Patient patient : patientRepository.findAll()) {
								System.out.println(patient.toString());
							}
						} else if (patientManagementchoice == 2) {

							Patient patient = new Patient();

							System.out.print("Enter patient name: ");
							patient.setName(scanner.nextLine());

							System.out.print("Enter patient age: ");
							patient.setAge(scanner.nextInt());

							int genderOption;

							do {

								System.out.print("\nEnter patient gender: ");
								System.out.print("\n1 - Male \n");
								System.out.print("2 - Female \n");
								System.out.print("Enter your answer (1 - 2): ");
								genderOption = scanner.nextInt();

								if (genderOption == 1) {
									patient.setGender(Patient.GENDER_MALE);
								} else if (genderOption == 2) {
									patient.setGender(Patient.GENDER_MALE);
								} else {
									System.out.print("Invalid options!");
								}

							} while (genderOption != 1 && genderOption != 2);

							scanner.nextLine();

							System.out.print("Enter patient contact: ");
							patient.setContact(scanner.nextLine());

							System.out.print("Enter patient medical history: ");
							patient.setMedical_history(scanner.nextLine());

							patientRepository.save(patient);
							System.out.println("Patient added successfully!");

						} else if (patientManagementchoice == 3) {

							System.out.print("Choose the id of the patient you want to edit: ");
							long patientId = scanner.nextLong();
							Optional<Patient> patientOpt = patientRepository.findById(patientId);

							if (patientOpt.isPresent()) {
								Patient patient = patientOpt.get();
								scanner.nextLine(); // Consume newline left-over

								System.out.print("Enter patient name (" + patient.getName() + "): ");
								patient.setName(scanner.nextLine());

								System.out.print("Enter patient age (" + patient.getAge() + "): ");
								patient.setAge(scanner.nextInt());

								int genderOption;

								do {

									System.out.print("\nEnter patient gender (" + patient.getGender() + "): ");
									System.out.print("\n1 - Male \n");
									System.out.print("2 - Female \n");
									System.out.print("Enter your answer (1 - 2): ");
									genderOption = scanner.nextInt();

									if (genderOption == 1) {
										patient.setGender(Patient.GENDER_MALE);
									} else if (genderOption == 2) {
										patient.setGender(Patient.GENDER_MALE);
									} else {
										System.out.print("Invalid options!");
									}

								} while (genderOption != 1 && genderOption != 2);

								scanner.nextLine();

								System.out.print("Enter patient contact (" + patient.getContact() + "): ");
								patient.setContact(scanner.nextLine());

								System.out.print(
										"Enter patient medical history (" + patient.getMedical_history() + "): ");
								patient.setMedical_history(scanner.nextLine());

								patientRepository.save(patient);
								System.out.println("Patient edited successfully!");
							} else {
								System.out.println("Patient with id " + patientId + " not found.");
							}

						} else if (patientManagementchoice == 4) {

							System.out.print("Choose the id of the patient you want to delete: ");
							long patientId = scanner.nextLong();
							Optional<Patient> patientOpt = patientRepository.findById(patientId);

							if (patientOpt.isPresent()) {
								patientRepository.delete(patientOpt.get());
								System.out.println("Patient deleted successfully!");
							} else {
								System.out.println("Patient with id " + patientId + " not found.");
							}

						} else if (patientManagementchoice == 5) {
							// Go back to the main menu (just exit the current method to return to main menu
							// logic)
							System.out.println("Returning to Main Menu...");
							return;
						}
					}
				} else if (choice == 2) {
					scheduleService.manageAppointments(scanner);
				} else if (choice == 3) {
					paymentService.manageInvoices(scanner);
				} else if (choice == 4) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}
			} else {
				System.out.println("\nPatient Management Page.");
				System.out.println("1 - Confirm Appointments");
				System.out.println("2 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					scheduleService.confirmAppointment(scanner, loggedInUser);
				} else if (choice == 2) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}
			}

		}
	}

	private void staffManagementPage(Scanner scanner, Object loggedInUser) {
		while (true) {
			if (loggedInUser instanceof Admin) {
				System.out.println("\nStaff Management Page.");
				System.out.println("1 - Manage Staffs");
				System.out.println("2 - Manage Shifts");
				System.out.println("3 - Manage Payrolls");
				System.out.println("4 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					while (true) {
						System.out.println("\n1 - View Staffs");
						System.out.println("2 - Add Staff");
						System.out.println("3 - Edit Staff");
						System.out.println("4 - Delete Staff");
						System.out.println("5 - Back to Main Menu");
						System.out.print("Choose an option: ");
						int staffManagementchoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline left-over

						if (staffManagementchoice == 1) {
							for (Staff staff : staffRepository.findAll()) {
								System.out.println(staff.toString());
							}
						} else if (staffManagementchoice == 2) {

							Staff staff = new Staff();

							System.out.print("Enter staff username: ");
							staff.setUsername(scanner.nextLine());

							System.out.print("Enter staff password: ");
							staff.setPassword(scanner.nextLine());

							int roleOption;

							do {

								System.out.print("\nEnter staff role: ");
								System.out.print("\n1 - Staff \n");
								System.out.print("2 - Doctor \n");
								System.out.print("3 - Nurse \n");
								System.out.print("Enter your answer (1 - 3): ");
								roleOption = scanner.nextInt();

								if (roleOption == 1) {
									staff.setRole(Staff.ROLE_STAFF);
								} else if (roleOption == 2) {
									staff.setRole(Staff.ROLE_DOCTOR);
								} else if (roleOption == 3) {
									staff.setRole(Staff.ROLE_NURSE);
								} else {
									System.out.print("Invalid options!");
								}

							} while (roleOption != 1 && roleOption != 2 && roleOption != 3);

							int departmentOption;

							do {

								System.out.print("\nEnter staff department: ");
								System.out.print("\n1 - Cardiology \n");
								System.out.print("2 - Neurology \n");
								System.out.print("3 - Pediatrics \n");
								System.out.print("Enter your answer (1 - 3): ");
								departmentOption = scanner.nextInt();

								if (departmentOption == 1) {
									staff.setDepartment(Staff.DEPARTMENT_CARDIOLOGY);
								} else if (departmentOption == 2) {
									staff.setDepartment(Staff.DEPARTMENT_NEUROLOGY);
								} else if (departmentOption == 3) {
									staff.setDepartment(Staff.DEPARTMENT_PEDIATRICS);
								} else {
									System.out.print("Invalid options!");
								}

							} while (departmentOption != 1 && departmentOption != 2 && departmentOption != 3);

							scanner.nextLine();

							System.out.print("Enter staff contact: ");
							staff.setContact(scanner.nextLine());

							staffRepository.save(staff);
							System.out.println("Staff added successfully!");

						} else if (staffManagementchoice == 3) {

							System.out.print("Choose the id of the staff you want to edit: ");
							long staffId = scanner.nextLong();
							Optional<Staff> staffOpt = staffRepository.findById(staffId);

							if (staffOpt.isPresent()) {
								Staff staff = staffOpt.get();
								scanner.nextLine(); // Consume newline left-over

								System.out.print("Enter staff username (" + staff.getUsername() + "): ");
								staff.setUsername(scanner.nextLine());

								System.out.print("Enter staff password (" + staff.getPassword() + "): ");
								staff.setPassword(scanner.nextLine());

								int roleOption;

								do {

									System.out.print("\nEnter staff role (" + staff.getRole() + "): ");
									System.out.print("\n1 - Staff \n");
									System.out.print("2 - Doctor \n");
									System.out.print("3 - Nurse \n");
									System.out.print("Enter your answer (1 - 3): ");
									roleOption = scanner.nextInt();

									if (roleOption == 1) {
										staff.setRole(Staff.ROLE_STAFF);
									} else if (roleOption == 2) {
										staff.setRole(Staff.ROLE_DOCTOR);
									} else if (roleOption == 3) {
										staff.setRole(Staff.ROLE_NURSE);
									} else {
										System.out.print("Invalid options!");
									}

								} while (roleOption != 1 && roleOption != 2 && roleOption != 3);

								int departmentOption;

								do {

									System.out.print("\nEnter staff department (" + staff.getDepartment() + "): ");
									System.out.print("\n1 - Cardiology \n");
									System.out.print("2 - Neurology \n");
									System.out.print("3 - Pediatrics \n");
									System.out.print("Enter your answer (1 - 3): ");
									departmentOption = scanner.nextInt();

									if (departmentOption == 1) {
										staff.setDepartment(Staff.DEPARTMENT_CARDIOLOGY);
									} else if (departmentOption == 2) {
										staff.setDepartment(Staff.DEPARTMENT_NEUROLOGY);
									} else if (departmentOption == 3) {
										staff.setDepartment(Staff.DEPARTMENT_PEDIATRICS);
									} else {
										System.out.print("Invalid options!");
									}

								} while (departmentOption != 1 && departmentOption != 2 && departmentOption != 3);

								scanner.nextLine();

								System.out.print("Enter staff contact (" + staff.getContact() + "): ");
								staff.setContact(scanner.nextLine());

								staffRepository.save(staff);
								System.out.println("Staff edited successfully!");
							} else {
								System.out.println("Staff with id " + staffId + " not found.");
							}

						} else if (staffManagementchoice == 4) {

							System.out.print("Choose the id of the staff you want to delete: ");
							long staffId = scanner.nextLong();
							Optional<Staff> staffOpt = staffRepository.findById(staffId);

							if (staffOpt.isPresent()) {
								staffRepository.delete(staffOpt.get());
								System.out.println("Staff deleted successfully!");
							} else {
								System.out.println("Staff with id " + staffId + " not found.");
							}

						} else if (staffManagementchoice == 5) {
							// Go back to the main menu (just exit the current method to return to main menu
							// logic)
							System.out.println("Returning to Main Menu...");
							return;
						}
					}
				} else if (choice == 2) {
					scheduleService.manageShifts(scanner);
				} else if (choice == 3) {
					paymentService.managePayrolls(scanner);
				} else if (choice == 4) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}
			} else if (loggedInUser instanceof Staff) {
				System.out.println("\nStaff Management Page.");
				System.out.println("1 - View Shifts");
				System.out.println("2 - View Payrolls");
				System.out.println("3 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					scheduleService.viewShifts(scanner, loggedInUser);
				} else if (choice == 2) {
					paymentService.viewPayrolls(scanner, loggedInUser);
				} else if (choice == 3) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}
			}
		}
	}

	private void itemManagementPage(Scanner scanner, Object loggedInUser) {
		while (true) {

			if (loggedInUser instanceof Staff) {
				System.out.println("\nItem Management Page.");
				System.out.println("1 - Manage Items");
				System.out.println("2 - Manage Medicines");
				System.out.println("3 - Manage Supply Request");
				System.out.println("4 - Check Expiration");
				System.out.println("5 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					while (true) {
						System.out.println("\n1 - View Items");
						System.out.println("2 - Add Item");
						System.out.println("3 - Edit Item");
						System.out.println("4 - Delete Item");
						System.out.println("5 - Back to Main Menu");
						System.out.print("Choose an option: ");
						int itemManagementchoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline left-over

						if (itemManagementchoice == 1) {
							for (Item item : itemRepository.findAll()) {
								System.out.println(item.toString());
							}
						} else if (itemManagementchoice == 2) {

							Item item = new Item();

							System.out.print("Enter item name: ");
							item.setItem_name(scanner.nextLine());

							System.out.print("Enter item quantity: ");
							item.setQuantity(scanner.nextInt());

							scanner.nextLine();

							System.out.print("Enter item expiration date (YYYY-MM-DD): ");
							item.setExpiry_date(scanner.nextLine());

							itemRepository.save(item);
							System.out.println("Item added successfully!");

						} else if (itemManagementchoice == 3) {

							System.out.print("Choose the id of the item you want to edit: ");
							long itemId = scanner.nextLong();
							Optional<Item> itemOpt = itemRepository.findById(itemId);

							if (itemOpt.isPresent()) {
								Item item = itemOpt.get();
								scanner.nextLine(); // Consume newline left-over

								System.out.print("Enter item name (" + item.getItem_name() + "): ");
								item.setItem_name(scanner.nextLine());

								System.out.print("Enter item quantity (" + item.getQuantity() + "): ");
								item.setQuantity(scanner.nextInt());

								scanner.nextLine();

								System.out.print("Enter item expiration date (" + item.getExpiry_date() + "): ");
								item.setExpiry_date(scanner.nextLine());

								itemRepository.save(item);
								System.out.println("Item edited successfully!");
							} else {
								System.out.println("Item with id " + itemId + " not found.");
							}

						} else if (itemManagementchoice == 4) {

							System.out.print("Choose the id of the item you want to delete: ");
							long itemId = scanner.nextLong();
							Optional<Item> itemOpt = itemRepository.findById(itemId);

							if (itemOpt.isPresent()) {
								itemRepository.delete(itemOpt.get());
								System.out.println("Item deleted successfully!");
							} else {
								System.out.println("Item with id " + itemId + " not found.");
							}

						} else if (itemManagementchoice == 5) {
							// Go back to the main menu (just exit the current method to return to main menu
							// logic)
							System.out.println("Returning to Main Menu...");
							return;
						}
					}
				} else if (choice == 2) {
					while (true) {
						System.out.println("\n1 - View Medicines");
						System.out.println("2 - Add Medicine");
						System.out.println("3 - Edit Medicine");
						System.out.println("4 - Delete Medicine");
						System.out.println("5 - Back to Main Menu");
						System.out.print("Choose an option: ");
						int medicineManagementchoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline left-over

						if (medicineManagementchoice == 1) {
							for (Medicine medicine : medicineRepository.findAll()) {
								System.out.println(medicine.toString());
							}
						} else if (medicineManagementchoice == 2) {

							Medicine medicine = new Medicine();

							System.out.print("Enter medicine name: ");
							medicine.setName(scanner.nextLine());

							System.out.print("Enter medicine dosage (mg): ");
							medicine.setDosage(scanner.nextFloat());

							scanner.nextLine();

							System.out.print("Enter medicine expiration date (YYYY-MM-DD): ");
							medicine.setExpiry_date(scanner.nextLine());

							medicineRepository.save(medicine);
							System.out.println("Medicine added successfully!");

						} else if (medicineManagementchoice == 3) {

							System.out.print("Choose the id of the medicine you want to edit: ");
							long medicineId = scanner.nextLong();
							Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);

							if (medicineOpt.isPresent()) {
								Medicine medicine = medicineOpt.get();
								scanner.nextLine(); // Consume newline left-over

								System.out.print("Enter medicine name (" + medicine.getName() + "): ");
								medicine.setName(scanner.nextLine());

								System.out.print("Enter medicine dosage (" + medicine.getDosage() + "): ");
								medicine.setDosage(scanner.nextFloat());

								scanner.nextLine();

								System.out
										.print("Enter medicine expiration date (" + medicine.getExpiry_date() + "): ");
								medicine.setExpiry_date(scanner.nextLine());

								medicineRepository.save(medicine);
								System.out.println("Medicine edited successfully!");
							} else {
								System.out.println("Medicine with id " + medicineId + " not found.");
							}

						} else if (medicineManagementchoice == 4) {

							System.out.print("Choose the id of the medicine you want to delete: ");
							long medicineId = scanner.nextLong();
							Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);

							if (medicineOpt.isPresent()) {
								medicineRepository.delete(medicineOpt.get());
								System.out.println("Medicine deleted successfully!");
							} else {
								System.out.println("Medicine with id " + medicineId + " not found.");
							}

						} else if (medicineManagementchoice == 5) {
							// Go back to the main menu (just exit the current method to return to main menu
							// logic)
							System.out.println("Returning to Main Menu...");
							return;
						}
					}
				} else if (choice == 3) {
					supplyService.manageSupplyRequests(scanner, loggedInUser);
				} else if (choice == 4) {
					System.out.println("\nChecking for items and medicines nearing their expiration date...");

					// Define the threshold in days (e.g., 30 days before expiry)
					int thresholdDays = 30;

					// Get the current date
					LocalDate currentDate = LocalDate.now();

					// Check items nearing expiration
					System.out.println("\nItems nearing expiration:");
					for (Item item : itemRepository.findAll()) {
						LocalDate expiryDate = LocalDate.parse(item.getExpiry_date());
						if (!expiryDate.isBefore(currentDate)
								&& !expiryDate.isAfter(currentDate.plusDays(thresholdDays))) {
							System.out.println("ID: " + item.getId() + ", Name: " + item.getItem_name() + ", Quantity: "
									+ item.getQuantity() + ", Expiry Date: " + item.getExpiry_date());
						}
					}

					// Check medicines nearing expiration
					System.out.println("\nMedicines nearing expiration:");
					for (Medicine medicine : medicineRepository.findAll()) {
						LocalDate expiryDate = LocalDate.parse(medicine.getExpiry_date());
						if (!expiryDate.isBefore(currentDate)
								&& !expiryDate.isAfter(currentDate.plusDays(thresholdDays))) {
							System.out
									.println("ID: " + medicine.getId() + ", Name: " + medicine.getName() + ", Dosage: "
											+ medicine.getDosage() + ", Expiry Date: " + medicine.getExpiry_date());
						}
					}

					System.out.println("\nExpiration check completed.");
				} else if (choice == 5) {
					return; // Go back to main menu
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}
			} else if (loggedInUser instanceof Admin) {
				System.out.println("\nItem Management Page.");
				System.out.println("1 - Confirm Supply Request");
				System.out.println("2 - Go Back to Main Menu");
				System.out.print("Choose an option: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				if (choice == 1) {
					supplyService.confirmSupplyRequest(scanner, loggedInUser);
				} else if (choice == 2) {
					return;
				} else {
					System.out.println("\nInvalid choice. Please try again.");
				}
			}
		}
	}
}
