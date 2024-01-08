package MediSimHospitalHub;

import java.util.*;

/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to 
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution 
with anybody. I affirm that I have read and understood
the Senate Policy on Academic honesty at 
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a 
result of committing plagiarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name: Bilal Syed Abdali
Student Number:  217286782
Course Section:  Z
*/

/**
 * A class 'Hospital' which is a virtual hospital which contains the Director,
 * physicianAdministrators,physicians,volunteers and patients and has various.
 * methods which simulate everything needed to be run a hospital from assigning.
 * patients to hiring physicians.
 * 
 */
public class Hospital {
	Director director;
	List<PhysicianAdministrator> physicianAdministrator = new ArrayList<>();
	List<Physician> physicians = new ArrayList<>();
	List<Volunteer> volunteers = new ArrayList<>();
	List<Patient> patients = new ArrayList<>();

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with
	 * the. default values and assigns a director to the hospital.
	 * 
	 * @pre can only be one director per hospital
	 * @param director is an object who is in charge of the hospital.
	 */
	public Hospital(Director director) {
		this.director = director;
	}

	/**
	 * This method, returns the director of the hospital.
	 */
	public Director getHospDirector() {
		return this.director;
	}

	/**
	 * This boolean method, adds a PhysicianAdministrator whether the conditions are
	 * met, returns true but if not able to add the Administrator returns false.
	 * 
	 * @param admin is an object of PhysicianAdministrator.
	 * @return true or false depending whether you can addAdministrator.
	 */
	public boolean addAdministrator(PhysicianAdministrator admin) {
		boolean canAssign = false;
		if (this.physicianAdministrator.size() < 3) {
			this.physicianAdministrator.add(admin);
			canAssign = true;
		}
		return canAssign;
	}

	/**
	 * This boolean method, hires a Physician whether the conditions are met then
	 * returns true if not false. Also assigns the Physician to an
	 * physicianAdministrator depending on specialty.
	 * 
	 * @pre Max number of physicians at 70.
	 * @param p is an object of Physician
	 * @return true or false depending whether you can hirePhysician
	 */
	public boolean hirePhysician(Physician p) {
		boolean canAssign = false;
		if (this.physicians.size() < 70 && !physicians.contains(p)) {

			this.physicians.add(p);
			for (int i = 0; i < physicianAdministrator.size(); i++) {
				if (physicianAdministrator.get(i).specialty.equals(p.specialty)) {
					this.physicianAdministrator.get(i).assignPhysician(p);
					canAssign = true;
					break;
				}
			}

		}
		return canAssign;
	}

	/**
	 * This method resigns a Physician from the Hospital.
	 * 
	 * @param physician is an object of Physician
	 * @throws NoSpecialtyException if physician is only Physician of a specialty
	 */
	public void resignPhysician(Physician physician) throws NoSpecialtyException {
		boolean onlySpecality = false;
		List<Physician> newList = new ArrayList<>(physicians);
		newList.remove(physician);
		for (int i = 0; i < newList.size(); i++) {
			if (physician.specialty.equals(newList.get(i).specialty)) {
				onlySpecality = true;
				break;
			}
		}

		if (physicians.contains(physician) && onlySpecality) {
			this.physicians.remove(physician);
			for (int i = 0; i < physician.physicianPatients.size(); i++) {
				patients.remove(physician.physicianPatients.get(i));
				try {
					admitPatient(physician.physicianPatients.get(i));
				} catch (NoSpaceException e) {

				}
			}
			for (int i = 0; i < physician.physicianVolunteers.size(); i++) {
				volunteers.remove(physician.physicianVolunteers.get(i));
				hireVolunteer((Volunteer) physician.physicianVolunteers.get(i));
			}
		} else {
			throw new NoSpecialtyException();
		}
	}

	/**
	 * This boolean method, hires a volunteer whether the conditions are met then
	 * returns true. Also assigns the volunteer to a Physician depending on space.
	 * if it is not able to add the volunteer returns false.
	 * 
	 * @pre Max number of volunteers is 150
	 * @param volunteer is an object of Volunteer
	 * @return true or false depending whether you can hireVolunteer
	 */
	public boolean hireVolunteer(Volunteer volunteer) {
		boolean canAssign = false;
		if (this.volunteers.size() < 150 && !(this.volunteers.contains(volunteer))) {
			for (int i = 0; i < physicians.size(); i++) {
				if (!(physicians.get(i).hasMaxVolunteers())) {
					physicians.get(i).assignVolunteer(volunteer);
					canAssign = true;
					this.volunteers.add(volunteer);
					break;
				}
			}
		}
		return canAssign;
	}

	/**
	 * This method resigns a Volunteer from the Hospital.
	 * 
	 * @pre volunteer cannot be the only volunteer assigned to a physcian
	 * @throws NoVolunteersException if only volunteer assigned to a physician.
	 *                               *@param v0 is an object of Volunteer
	 */
	public void resignVolunteer(Volunteer v0) throws NoVolunteersException {
		if (this.volunteers.contains(v0)) {
			if (physicians.get(0).physicianVolunteers.size() == 1) {
				throw new NoVolunteersException();
			}

			for (int i = 0; i < physicians.size(); i++) {
				if (physicians.get(i).physicianVolunteers.contains(v0)) {
					physicians.get(i).physicianVolunteers.remove(v0);
					this.volunteers.remove(v0);
				}
			}

		}
	}

	/**
	 * This method returns a list of All the patients in the hospital and sorts them
	 * 
	 * @pre List cannot be null Alphabetically by first and last name
	 * @return List of Patients
	 */
	public List<Patient> extractAllPatientDetails() {
		Collections.sort(patients);
		return patients;

	}

	/**
	 * This boolean method, admits a patient whether the conditions are met then
	 * returns true. Also assigns the patient to a Physician depending on space. If
	 * it is not able to add the patient returns false.
	 * 
	 * @param patient is an object of Patient.
	 * @throws NoSpaceException if 500 patients in hospital or no physicians that
	 *                          can be assigned to them.
	 * @return true or false depending whether you can admitPatient.
	 */
	public boolean admitPatient(Patient patient) throws NoSpaceException {
		boolean canAssign = false;

		if (this.patients.size() < 500 && !(this.patients.contains(patient))) {
			for (int i = 0; i < physicians.size(); i++) {
				if (!(physicians.get(i).hasMaximumpatient())) {
					physicians.get(i).assignPatient(patient);
					this.patients.add(patient);
					return canAssign = true;

				}

			}
			if (!this.patients.contains(patient)) {
				throw new NoSpaceException();

			}
		}
		return canAssign;

	}

	/**
	 * This method returns a list of All the Physicians in the hospital and sorts
	 * them Alphabetically by first and last name.
	 * 
	 * @pre List cannot be empty.
	 * @return a list of physicians.
	 */
	public List<Physician> extractAllPhysicianDetails() {
		Collections.sort(physicians);

		return physicians;

	}

	/**
	 * This method returns a list of All the Volunteers in the hospital and sorts
	 * them Alphabetically by first and last name.
	 * 
	 * @pre List cannot be empty.
	 * @return a list of Volunteers.
	 */
	public List<Volunteer> extractAllVolunteerDetails() {
		return this.volunteers;
	}

	/**
	 * This method discharges a Volunteer from the Hospital.
	 * 
	 * @param patient is an object of Patient.
	 * @return true or false depending whether you can dischargePatient.
	 */

	public boolean dischargePatient(Patient patient) {
		boolean canDischarge = false;
		if (this.patients.contains(patient)) {
			this.patients.remove(patient);
			for (int i = 0; i < physicians.size(); i++) {
				if ((physicians.get(i).physicianPatients.contains(patient))) {
					physicians.get(i).physicianPatients.remove(patient);
					canDischarge = true;
					break;
				}
			}

		}

		return canDischarge;

	}
}

/**
 * A class 'Person' which holds various specifications about a person and
 * initializes them.
 * 
 */
class Person {
	private String FirstName;
	private String lastName;
	private String gender;
	private String Address;
	private int age;

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender
	 * @param address   is the person's address
	 */
	public Person(String FirstName, String lastName, int age, String gender, String Address) {
		this.FirstName = FirstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.Address = Address;
	}

	/**
	 * This method sets the FirstName to parameter firstName.
	 * 
	 * @param firstName is a person's first name.
	 */
	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	/**
	 * @return This method returns the person's FirstName.
	 */
	public String getFirstName() {
		return this.FirstName;
	}

	/**
	 * This method sets the lastName to parameter lastName.
	 * 
	 * @param LastName is a person's last name.
	 */
	public void setLastName(String LastName) {
		this.lastName = LastName;
	}

	/**
	 * @return This method returns the person's lastName.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return This method returns the person's age.
	 */
	public Integer getAge() {
		return this.age;
	}

	/**
	 * This method sets the person's age to parameter age.
	 * 
	 * @param age is a person's age.
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * This method returns the person's gender.
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * This method sets the person's gender to parameter gender.
	 * 
	 * @param gender is a person's gender.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return this.Address;
	}

	/**
	 * This method sets the person's address to parameter Address.
	 * 
	 * @param Address is a person's address.
	 */
	public void setAddress(String Address) {
		this.Address = Address;
	}

	/**
	 * This method returns the concatenation of the first and last name of a person.
	 */
	public String getName() {
		return (this.FirstName + ", " + this.lastName);
	}
}

/**
 * A class 'patient' which holds various features about a patient and their own
 * individual id and assigned to a patient.
 * 
 */
class Patient extends Person implements Comparable<Patient> {
	protected static int patientID = 999;
	Physician physician;

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public Patient(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
		patientID++;
	}

	/**
	 * This method returns the patient's id.
	 */
	public int getPatientID() {
		return Patient.patientID;
	}

	/**
	 * This method compares the concatenation of the first and last name of this
	 * patient and another patient.
	 * 
	 * @param p is a object of Patient.
	 */
	@Override
	public int compareTo(Patient p) {
		return this.getName().compareTo(p.getName());

	}

	/**
	 * This method changes to the toString method to specify all the features of the
	 * patient and its id as well.
	 */
	@Override
	public String toString() {
		return "Patient [" + Patient.patientID + ", [" + this.getName() + ", " + this.getAge() + "," + " "
				+ this.getGender() + ", " + this.getAddress() + "]]";
	}

	/**
	 * This method sets this patient's physician to the parameter physician.
	 * 
	 * @param physician is a object of Physician.
	 */
	public void setAssignedPhysician(Physician physician) {
		this.physician = physician;
		this.physician.assignPatient(this);
	}

	/**
	 * This method returns this patient's physician.
	 */
	public Physician getAssignedPhysician() {
		return this.physician;
	}

	/**
	 * This boolean method checks to see whether this patient has been cleared from
	 * the hospital by checking whether this physician is no longer assigned to the
	 * patient.
	 */
	public Boolean clearPatientRecord() {
		if (this.physician == null) {
			return true;
		} else {
			return false;
		}
	}

}

/**
 * The class 'Employee' which holds various features about a employee and their
 * own individual id.
 * 
 */
class Employee extends Person {
	protected static int employeeID = 99;

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public Employee(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
		employeeID++;
	}

	/**
	 * This method returns this employee's ID.
	 */
	public int getEmployeeID() {
		return employeeID;
	}
}

/**
 * The class 'Volunteer' which holds various features about a Volunteer and
 * extends Employee.
 * 
 */
class Volunteer extends Employee {
	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public Volunteer(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
	}

	/**
	 * This method changes to the toString method to specify all the features of the
	 * Volunteer and its ID as well.
	 */
	@Override
	public String toString() {
		return "Volunteer [[" + this.getEmployeeID() + ",[" + this.getName() + ", " + this.getAge() + "," + " "
				+ this.getGender() + ", " + this.getAddress() + "]]]";

	}

}

/**
 * The class 'SalariedEmployee' which holds various features about a
 * SalariedEmployee and extends Employee.
 * 
 */
class SalariedEmployee extends Employee {
	double salary;

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public SalariedEmployee(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
	}

	/**
	 * This method sets this SalariedEmployee's salary to the parameter salary.
	 * 
	 * @param salary is a double.
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * This method returns this SalariedEmployee's salary.
	 */
	public double getSalary() {
		return this.salary;
	}
}

/**
 * The class 'Administrator' which holds various features about a Administrator
 * and extends SalariedEmployee.
 * 
 */
class Administrator extends SalariedEmployee {
	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public Administrator(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);

	}

}

/**
 * The class 'PhysicianAdministrator' which holds various features about a
 * PhysicianAdministrator and extends Administrator. Contains a list of all the
 * Physicians it manages under adminsPhysicians.
 * 
 */
class PhysicianAdministrator extends Administrator {
	String specialty;
	List<Physician> adminsPhysicians = new ArrayList<>();

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public PhysicianAdministrator(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
	}

	/**
	 * This method sets this PhysicianAdministrator specialty to the parameter
	 * specialty.
	 * 
	 * @param specialty is a String of the PhysicianAdministrator type of
	 *                  specialty..
	 */
	public void setAdminSpecialtyType(String specialty) {

		if (specialty.equals("Dermatology") || specialty.equals("Neurology") || specialty.equals("Immunology")) {
			this.specialty = specialty;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * This method returns this PhysicianAdministrator specialty.
	 */
	public String getAdminSpecialtyType() {
		return this.specialty;
	}

	/**
	 * This method changes to the toString method to specify all the features of the
	 * PysicianAdministrator, ID and specialty as well.
	 */
	@Override
	public String toString() {
		return "PysicianAdministrator [[[" + this.getEmployeeID() + ",[" + this.getName() + ", " + this.getAge() + ","
				+ " " + this.getGender() + ", " + this.getAddress() + "]], " + this.getSalary() + "], "
				+ this.getAdminSpecialtyType() + "]";
	}

	/**
	 * This method adds to the adminsPhysicians list of Physicians that the
	 * PhysicianAdministrator manages.
	 * 
	 * @pre only 25 physicians can be assigned to an Administrator.
	 * @param p is an object of type Physician.
	 */
	public void assignPhysician(Physician p) {
		if (adminsPhysicians.size() < 25) {
			this.adminsPhysicians.add(p);
		}
	}

	/**
	 * @pre List cannot be empty
	 * @return This method returns the list of Physicians that the
	 *         PhysicianAdministrator manages.
	 */
	public List<Physician> extractPhysician() {
		return adminsPhysicians;

	}

}

/**
 * The class 'Director' which holds various features about a Director and
 * extends Administrator. Contains a list of all the PhysicianAdministrators, it
 * manages under physicianAdministratorList.
 * 
 */
class Director extends Administrator {
	List<PhysicianAdministrator> physicianAdministratorList = new ArrayList<>();

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public Director(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
	}

	/**
	 * This boolean method determines if being able to add to the
	 * physicianAdministratorList which is list of PhysicianAdministrators that the
	 * Director manages.
	 * 
	 * @param admin is an object of type PhysicianAdministrator.
	 */
	public boolean assignAdministrator(PhysicianAdministrator admin) {
		boolean canAssign = false;
		if (this.physicianAdministratorList.size() < 3) {
			this.physicianAdministratorList.add(admin);
			canAssign = true;
		}
		return canAssign;
	}

	/**
	 * @pre List cannot be empty
	 * @return This method returns the list of PhysicianAdministrators that the
	 *         director manages.
	 */
	public List<PhysicianAdministrator> extractPhysicianAdmins() {

		return physicianAdministratorList;

	}

}

/**
 * The class 'Director' which holds various features about a Physician and
 * extends SalariedEmployee. Contains a list of all the Volunteers, it manages
 * under physicianVolunteers. Contains a list of all the Patient, it manages
 * under physicianPatients.
 * 
 */
class Physician extends SalariedEmployee implements Comparable<Physician> {
	String specialty;
	List<Employee> physicianVolunteers = new ArrayList<>();
	List<Patient> physicianPatients = new ArrayList<>();

	/**
	 * Using the overloaded Constructor. Initializes the instance variables with the
	 * set values and sets the Patients id.
	 * 
	 * @param FirstName is the person's first name.
	 * @param LastName  is the person's last name.
	 * @param age       is the person's age.
	 * @param gender    is the person's gender.
	 * @param address   is the person's address.
	 */
	public Physician(String FirstName, String lastName, int age, String gender, String Address) {
		super(FirstName, lastName, age, gender, Address);
	}

	/**
	 * This method compares the concatenation of the first and last name of this
	 * Physician and another Physician.
	 * 
	 * @param p2 is a object of Physician.
	 */
	@Override
	public int compareTo(Physician p2) {
		return this.getName().compareTo(p2.getName());
	}

	/**
	 * This method sets this Physician's specialty to the parameter specialty.
	 * 
	 * @param specialty is a String which is Physician type of specialty.
	 */
	public void setSpecialty(String specialty) {
		if (specialty.equals("Dermatology") || specialty.equals("Neurology") || specialty.equals("Immunology")) {
			this.specialty = specialty;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return This method returns this Physician's specialty.
	 */
	public String getSpecialty() {
		return this.specialty;
	}

	/**
	 * @return This method changes to the toString method to specify all the
	 *         features of the Physician, ID , specialty and salary as well.
	 */
	@Override
	public String toString() {
		return "Physician [[[" + this.getEmployeeID() + ",[" + this.getName() + ", " + this.getAge() + "," + " "
				+ this.getGender() + ", " + this.getAddress() + "]], " + this.getSalary() + "]]";
	}

	/**
	 * This boolean method determines if being able to add to the
	 * physicianVolunteers which is list of Volunteers that the physician manages.
	 * 
	 * @param volunteer is an object of type Employee.
	 */
	public Boolean assignVolunteer(Employee volunteer) {
		boolean canAssign = false;
		if (physicianVolunteers.size() < 5) {
			physicianVolunteers.add(volunteer);
			canAssign = true;
		}
		return canAssign;
	}

	/**
	 * @pre List cannot be empty.
	 * @return This method returns the list of volunteers that the physician
	 *         manages.
	 */
	public List<Employee> extractValunterDetail() {
		return physicianVolunteers;
	}

	/**
	 * This boolean method determines if physicianVolunteers at max capacity and
	 * cannot hold any more volunteers.
	 * 
	 * @return If at max return true else false.
	 */
	public Boolean hasMaxVolunteers() {
		boolean isMax = false;
		if (physicianVolunteers.size() >= 5) {
			isMax = true;
		}
		return isMax;
	}

	/**
	 * This boolean method adds to the physicianPatients which is list of Patients
	 * that the physician manages.
	 * 
	 * @param p is an object of type Patient.
	 */
	public void assignPatient(Patient p) {
		this.physicianPatients.add(p);
	}

	/**
	 * @pre List cannot be empty.
	 * @return This method returns the list of Patients that the physician manages.
	 */
	public List<Patient> extractPatientDetail() {
		return this.physicianPatients;
	}

	/**
	 * This boolean method determines if physicianPatients is at max capacity then
	 * true and cannot hold any more patients else false.
	 * 
	 * @return True or false whether MaxPatient is true.
	 */
	public Boolean hasMaximumpatient() {
		boolean isMax = false;
		if (physicianPatients.size() >= 8) {
			isMax = true;
		}
		return isMax;
	}
}

/**
 * The class 'NoSpaceException' extends Exception and throws when there is a max
 * capacity.
 */

class NoSpaceException extends Exception {
	NoSpaceException() {
		super();
	}
}

/**
 * The class 'NoVolunteersException' extends Exception . throws when a volunteer
 * is resigning from the hospital, but no other volunteer is available under
 * supervision/work with a particular physician.
 */
class NoVolunteersException extends Exception {
	NoVolunteersException() {
		super();
	}
}

/**
 * The class 'NoSpecialtyException' extends Exception . throws when a volunteer
 * is if a physician is resigning from the hospital, but no other physician is
 * available with the same specialty.
 */
class NoSpecialtyException extends Exception {
	NoSpecialtyException() {
		super();
	}
}

