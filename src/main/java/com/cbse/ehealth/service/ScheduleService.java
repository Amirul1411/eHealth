package com.cbse.ehealth.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbse.ehealth.model.Appointment;
import com.cbse.ehealth.model.Patient;
import com.cbse.ehealth.model.Shift;
import com.cbse.ehealth.model.Staff;
import com.cbse.ehealth.repository.AppointmentRepository;
import com.cbse.ehealth.repository.PatientRepository;
import com.cbse.ehealth.repository.ShiftRepository;
import com.cbse.ehealth.repository.StaffRepository;

@Service
public class ScheduleService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private ShiftRepository shiftRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private StaffRepository staffRepository;

	public void manageAppointments(Scanner scanner) {
		while (true) {
			System.out.println("\n1 - View Appointments");
			System.out.println("2 - Add Appointment");
			System.out.println("3 - Edit Appointment");
			System.out.println("4 - Delete Appointment");
			System.out.println("5 - Back to Main Menu");
			System.out.print("Choose an option: ");
			int appointmentManagementchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (appointmentManagementchoice == 1) {
				for (Appointment appointment : appointmentRepository.findAll()) {
					System.out.println(appointment.toString());
				}
			} else if (appointmentManagementchoice == 2) {

				Appointment appointment = new Appointment();

				System.out.print("Enter patient id: ");
				long patientId = scanner.nextLong();
				Optional<Patient> patientOpt = patientRepository.findById(patientId);

				if (patientOpt.isPresent()) {
					appointment.setPatient_id(patientId);
				} else {
					System.out.println("Patient with id " + patientId + " not found.");
					continue;
				}

				System.out.print("Enter staff id: ");
				long staffId = scanner.nextLong();
				Optional<Staff> staffOpt = staffRepository.findById(staffId);

				if (staffOpt.isPresent()) {
					appointment.setStaff_id(staffId);
				} else {
					System.out.println("Staff with id " + staffId + " not found.");
					continue;
				}

				scanner.nextLine();

				System.out.print("Enter appointment date (YYYY-MM-DD): ");
				appointment.setAppointment_date(scanner.nextLine());

				System.out.print("Enter appointment time (e.g. 0800): ");
				appointment.setAppointment_time(scanner.nextLine());

				System.out.print("Enter appointment duration (hours): ");
				appointment.setAppointment_duration(scanner.nextFloat());

				appointment.setStatus(Appointment.STATUS_PENDING);

				appointmentRepository.save(appointment);
				System.out.println("Appointment added successfully!");

			} else if (appointmentManagementchoice == 3) {

				System.out.print("Choose the id of the appointment you want to edit: ");
				long appointmentId = scanner.nextLong();
				Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);

				if (appointmentOpt.isPresent()) {
					Appointment appointment = appointmentOpt.get();
					scanner.nextLine(); // Consume newline left-over

					System.out.print("Enter patient id (" + appointment.getPatient_id() + "): ");
					long patientId = scanner.nextLong();
					Optional<Patient> patientOpt = patientRepository.findById(patientId);

					if (patientOpt.isPresent()) {
						appointment.setPatient_id(patientId);
					} else {
						System.out.println("Patient with id " + patientId + " not found.");
						continue;
					}

					System.out.print("Enter staff id (" + appointment.getStaff_id() + "): ");
					long staffId = scanner.nextLong();
					Optional<Staff> staffOpt = staffRepository.findById(staffId);

					if (staffOpt.isPresent()) {
						appointment.setStaff_id(staffId);
					} else {
						System.out.println("Staff with id " + staffId + " not found.");
						continue;
					}

					scanner.nextLine();

					System.out.print("Enter appointment date (" + appointment.getAppointment_date() + "): ");
					appointment.setAppointment_date(scanner.nextLine());

					System.out.print("Enter appointment time (" + appointment.getAppointment_time() + "): ");
					appointment.setAppointment_time(scanner.nextLine());

					System.out.print("Enter appointment duration (" + appointment.getAppointment_duration() + "): ");
					appointment.setAppointment_duration(scanner.nextFloat());

					appointment.setStatus(Appointment.STATUS_PENDING);

					appointmentRepository.save(appointment);
					System.out.println("Appointment edited successfully!");

				} else {
					System.out.println("Appointment with id " + appointmentId + " not found.");
				}

			} else if (appointmentManagementchoice == 4) {

				System.out.print("Choose the id of the appointment you want to delete: ");
				long appointmentId = scanner.nextLong();
				Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);

				if (appointmentOpt.isPresent()) {
					appointmentRepository.delete(appointmentOpt.get());
					System.out.println("Appointment deleted successfully!");
				} else {
					System.out.println("Appointment with id " + appointmentId + " not found.");
				}

			} else if (appointmentManagementchoice == 5) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public void manageShifts(Scanner scanner) {
		while (true) {
			System.out.println("\n1 - View Shifts");
			System.out.println("2 - Add Shift");
			System.out.println("3 - Edit Shift");
			System.out.println("4 - Delete Shift");
			System.out.println("5 - Back to Main Menu");
			System.out.print("Choose an option: ");
			int shiftManagementchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (shiftManagementchoice == 1) {
				for (Shift shift : shiftRepository.findAll()) {
					System.out.println(shift.toString());
				}
			} else if (shiftManagementchoice == 2) {

				Shift shift = new Shift();

				System.out.print("Enter staff id: ");
				long staffId = scanner.nextLong();
				Optional<Staff> staffOpt = staffRepository.findById(staffId);

				if (staffOpt.isPresent()) {
					shift.setStaff_id(staffId);
				} else {
					System.out.println("Staff with id " + staffId + " not found.");
					continue;
				}

				scanner.nextLine();

				int shiftTimeOption;

				do {

					System.out.print("Enter shift time: ");
					System.out.print("\n1 - Morning \n");
					System.out.print("2 - Evening \n");
					System.out.print("3 - Night \n");
					System.out.print("Enter your answer (1 - 3): ");
					shiftTimeOption = scanner.nextInt();

					if (shiftTimeOption == 1) {
						shift.setShift_time(Shift.SHIFT_MORNING);
					} else if (shiftTimeOption == 2) {
						shift.setShift_time(Shift.SHIFT_EVENING);
					} else if (shiftTimeOption == 3) {
						shift.setShift_time(Shift.SHIFT_NIGHT);
					} else {
						System.out.print("Invalid options!");
					}

				} while (shiftTimeOption != 1 && shiftTimeOption != 2 && shiftTimeOption != 3);

				scanner.nextLine();

				System.out.print("Enter shift date (YYYY-MM-DD): ");
				shift.setDate(scanner.nextLine());

				shiftRepository.save(shift);
				System.out.println("Shift added successfully!");

			} else if (shiftManagementchoice == 3) {

				System.out.print("Choose the id of the shift you want to edit: ");
				long shiftId = scanner.nextLong();
				Optional<Shift> shiftOpt = shiftRepository.findById(shiftId);

				if (shiftOpt.isPresent()) {
					Shift shift = shiftOpt.get();
					scanner.nextLine(); // Consume newline left-over

					System.out.print("Enter staff id (" + shift.getStaff_id() + "): ");
					long staffId = scanner.nextLong();
					Optional<Staff> staffOpt = staffRepository.findById(staffId);

					if (staffOpt.isPresent()) {
						shift.setStaff_id(staffId);
					} else {
						System.out.println("Staff with id " + staffId + " not found.");
						continue;
					}

					scanner.nextLine();

					int shiftTimeOption;

					do {

						System.out.print("Enter shift time (" + shift.getShift_time() + "): ");
						System.out.print("\n1 - Morning \n");
						System.out.print("2 - Evening \n");
						System.out.print("3 - Night \n");
						System.out.print("Enter your answer (1 - 3): ");
						shiftTimeOption = scanner.nextInt();

						if (shiftTimeOption == 1) {
							shift.setShift_time(Shift.SHIFT_MORNING);
						} else if (shiftTimeOption == 2) {
							shift.setShift_time(Shift.SHIFT_EVENING);
						} else if (shiftTimeOption == 3) {
							shift.setShift_time(Shift.SHIFT_NIGHT);
						} else {
							System.out.print("Invalid options!");
						}

					} while (shiftTimeOption != 1 && shiftTimeOption != 2 && shiftTimeOption != 3);

					scanner.nextLine();

					System.out.print("Enter shift date (" + shift.getDate() + "): ");
					shift.setDate(scanner.nextLine());

					shiftRepository.save(shift);
					System.out.println("Shift edited successfully!");

				} else {
					System.out.println("Shift with id " + shiftId + " not found.");
				}

			} else if (shiftManagementchoice == 4) {

				System.out.print("Choose the id of the shift you want to delete: ");
				long shiftId = scanner.nextLong();
				Optional<Shift> shiftOpt = shiftRepository.findById(shiftId);

				if (shiftOpt.isPresent()) {
					shiftRepository.delete(shiftOpt.get());
					System.out.println("Shift deleted successfully!");
				} else {
					System.out.println("Shift with id " + shiftId + " not found.");
				}

			} else if (shiftManagementchoice == 5) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public void confirmAppointment(Scanner scanner, Object loggedInUser) {

		Staff staff = (Staff) loggedInUser;

		while (true) {
			System.out.println("\n1 - View Appointments");
			System.out.println("2 - Confirm Appointments");
			System.out.println("3 - Back to Main Menu");
			System.out.print("Choose an option: ");
			int confirmAppointmentchoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (confirmAppointmentchoice == 1) {
				for (Appointment appointment : appointmentRepository.findByStaffId(staff.getId())) {
					System.out.println(appointment.toString());
				}
			} else if (confirmAppointmentchoice == 2) {

				System.out.print("\nChoose the id of the appointment you want to confirm: ");
				long appointmentId = scanner.nextLong();
				Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);

				if (appointmentOpt.isPresent()) {

					Appointment appointment = appointmentOpt.get();

					if (appointment.getStaff_id() != staff.getId()) {
						System.out.println("You can only confirm appointments related with your id");
						continue;
					}

					int confirmAppointmentOption;

					do {

						System.out.print("Confirming appointment: ");
						System.out.print("\n1 - Confirmed \n");
						System.out.print("2 - Cancelled \n");
						System.out.print("3 - Finished \n");
						System.out.print("Enter your answer (1 - 3): ");
						confirmAppointmentOption = scanner.nextInt();

						if (confirmAppointmentOption == 1) {
							appointment.setStatus(Appointment.STATUS_CONFIRMED);
						} else if (confirmAppointmentOption == 2) {
							appointment.setStatus(Appointment.STATUS_CANCELLED);
						} else if (confirmAppointmentOption == 3) {
							appointment.setStatus(Appointment.STATUS_FINISHED);
						} else {
							System.out.print("Invalid options!");
						}

					} while (confirmAppointmentOption != 1 && confirmAppointmentOption != 2
							&& confirmAppointmentOption != 3);

					appointmentRepository.save(appointment);
					System.out.println("Appointment updated successfully!");

				} else {
					System.out.println("Appointment with id " + appointmentId + " not found.");
				}

			} else if (confirmAppointmentchoice == 3) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}

	public void viewShifts(Scanner scanner, Object loggedInUser) {

		Staff staff = (Staff) loggedInUser;

		while (true) {
			System.out.println("\n1 - View Shifts");
			System.out.println("2 - Back to Main Menu");
			System.out.print("Choose an option: ");

			int viewShiftChoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			if (viewShiftChoice == 1) {
				for (Shift shift : shiftRepository.findByStaffId(staff.getId())) {
					System.out.println(shift.toString());
				}
			} else if (viewShiftChoice == 2) {
				// Go back to the main menu (just exit the current method to return to main menu
				// logic)
				System.out.println("Returning to Main Menu...");
				return;
			}
		}
	}
}
