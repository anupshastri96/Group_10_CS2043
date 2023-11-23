
package cs2043group10;

import java.security.NoSuchAlgorithmException;

import cs2043group10.data.FinancialDocument;
import cs2043group10.data.FinancialQuery;
import cs2043group10.data.LoginClass;
import cs2043group10.data.MedicalDocument;
import cs2043group10.data.MedicalQuery;
import cs2043group10.data.PatientInformation;
import cs2043group10.data.PatientQuery;
import cs2043group10.misc.PasswordHasher;
import cs2043group10.data.IQuery;

// Imports required for connecting to the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.CallableStatement;
import java.math.BigDecimal;
import java.sql.Date;

public class DatabaseManager implements IDatabase {
	private int loggedInId;
	private String loggedInName;
	private LoginClass loginClass;
	private final IReversableManager manager;
	private final PasswordHasher hasher;
	
	public DatabaseManager(IReversableManager manager) throws NoSuchAlgorithmException {
		this.manager = manager;
		loggedInId = -1;
		loginClass = LoginClass.NOT_LOGGED_IN;
		loggedInName = null;
		hasher = new PasswordHasher();
	}
	
	@Override
	public LoginClass tryLogin(int id, String password) throws DatabaseException {
		// Try to login using id and password.
		try
        {   // Connect to the database
            Connection connector = DriverManager.getConnection
                   ("jdbc:mysql://cs1103.cs.unb.ca:3306/iyoung",  // Database URL
                    "iyoung",   // MySQL username
                    "2RMFsZG2");  // MySQL password

			Boolean isValidCredentials = verifyCredentials(id, password);
			if (isValidCredentials) {
				return loginClass;
			}

			return loginClass;
			
		}
		catch(SQLException e)
        {   statusLabel.setText("Database error: " + e.getMessage());
        }
		//throw new RuntimeException("Not yet implemented.");
	}
	
	@Override
	public LoginClass getLoginClass() {
		return loginClass;
	}
	
	@Override
	public int getId() {
		return loggedInId;
	}
	
	@Override
	public String getName() {
		return loggedInName;
	}
	
	@Override
	public void logout() {
		loggedInId = -1;
		loggedInName = null;
		loginClass = LoginClass.NOT_LOGGED_IN;
		connector.close(); // CLoses connection with database
	}
	
	@Override
	public IReversable instantiateHomeView() throws DatabaseException {
		return loginClass.instantiateHomeView(manager, loggedInId);
	}
	
	@Override
	public PatientInformation queryPatientInformation(int patientId) throws DatabaseException {
		// Create the executable SQL statement
		String call = "{CALL queryPatientInformation(?)}";

		// Sets parameter(s) for stored procedure call
		CallableStatement procedureCall = connector.prepareCall(call);
		procedureCall.setInt(1, patientId);

		// Executes stored procedure
		ResultSet resultSet = callableStatement.executeQuery();

		// Declare patient info contents
		String name;
		String address;
		int id;
		Long createTimeStamp;
		Long modifyTimeStamp;
		LocalDate dateOfBirth;
		int doctorId;
		int totalAmountDue;
		int insuranceDeductible;
		int insuranceCostSharePercentage;
		int insuranceOutOfPocketMaximum;

		// Process the result set
		while (resultSet.next()) {
			// Retrieve columns from database
			name = resultSet.getString("name");
			address = resultSet.getString("address");
			id = resultSet.getInt("id");
			createTimeStamp = resultSet.getLong("createTimeStamp");
			modifyTimeStamp = resultSet.getLong("modifyTimeStamp");
			dateOfBirth = resultSet.getLocalDate("dateOfBirth");
			doctorId = resultSet.getInt("doctorId");
			totalAmountDue = resultSet.getInt("totalAmountDue");
			insuranceDeductible = resultSet.getInt("insuranceDeductible");
			insuranceCostSharePercentage = resultSet.getInt("insuranceCostSharePercentage");
			insuranceOutOfPocketMaximum = resultSet.getInt("insuranceOutOfPocketMaximum");
		}

		InsurancePlan insurance = new InsurancePlan(insuranceDeductible, insuranceOutOfPocketMaximum, insuranceCostSharePercentage);

		PatientInformation patientInformation = new PatientInformation(id, name, address, insurance, totalAmountDue, dateOfBirth, createTimeStamp, modifyTimeStamp, doctorId);

		return patientInformation;
	}
	
	@Override
	public MedicalDocument queryMedicalDocument(int documentId) throws DatabaseException {
		// Create the executable SQL statement
		String call = "{CALL queryMedicalDocument(?)}";

		// Sets parameter(s) for stored procedure call
		CallableStatement procedureCall = connector.prepareCall(call);
		procedureCall.setInt(1, documentId);

		// Executes stored procedure
		ResultSet resultSet = callableStatement.executeQuery();

		// Declare medical document contents
		// IMPORTANT: CAN WE REMOVE AUTHORID ITS NOT IN DATABASE???
		String title;
		String type;
		String body;
		String auxiliary;
		int patientId;
		int id;
		Long createTimeStamp;
		Long modifyTimeStamp;

		// Process the result set
		while (resultSet.next()) {
			// Retrieve columns from database
			title = resultSet.getString("title");
			type = resultSet.getString("type");
			body = resultSet.getString("body");
			auxiliary = resultSet.getString("auxiliary");
			patientId = resultSet.getInt("patientId");
			id = resultSet.getInt("id");
			createTimeStamp = resultSet.getLong("createTimeStamp");
			modifyTimeStamp = resultSet.getLong("modifyTimeStamp");
		}

		MedicalDocument medicalDocument = new MedicalDocument(documentId, title, type, body, auxiliary, patientId, modifyTimeStamp, createTimeStamp);
		
		return medicalDocument;
	}
	
	@Override
	public FinancialDocument queryFinancialDocument(int documentId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public IQuery<PatientQuery.PatientEntry> queryPatientsUnderDoctor(int doctorId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public IQuery<MedicalQuery.MedicalEntry> queryMedicalDocumentsUnderPatient(int patientId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public IQuery<FinancialQuery.FinancialEntry> queryFinancialDocumentsUnderPatient(int patientId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public int createPatient(PatientInformation information) throws DatabaseException {
		// TODO The timestamp and id fields should be automatically generated by the database.
		// return the id of the new patient.
		return -1;
	}
	
	@Override
	public int createFinancialDocument(FinancialDocument document) throws DatabaseException {
		// TODO
		return -1;
	}
	
	@Override
	public int createMedicalDocument(MedicalDocument document) throws DatabaseException {
		// TODO
		return -1;
	}
	
	@Override
	public void updatePatient(PatientInformation information) throws DatabaseException {
		// TODO
	}
	
	@Override
	public void updateMedicalDocument(MedicalDocument document) throws DatabaseException {
		// TODO
	}
	
	@Override
	public void updateFinancialDocument(FinancialDocument document) throws DatabaseException {
		// TODO
	}

	@Override
	public boolean verifyCredentials(int id, String password) throws DatabaseException {
		// Create the executable SQL statement
		String call = "{CALL verifyCredentials(?,?)}";

		// Sets parameter(s) for stored procedure call
		CallableStatement procedureCall = connector.prepareCall(call);
		procedureCall.setInt(1, id);
		procedureCall.setString(2, password); // MIGHT END UP PRODUCING AN ERROR (MIGHT NEED TO BE setChar())

		// Executes stored procedure
		ResultSet resultSet = callableStatement.executeQuery();

		// Process the result set
		while (resultSet.next()) {
			// Retrieve class from database
			String accountClass = resultSet.getString("class");
		}

		if (accountClass == "patient") {
			loginClass = LoginClass.PATIENT;
			return true;
		}
		else if (accountClass == "doctor") {
			loginClass = LoginClass.DOCTOR;
			return true;
		}

		return false;
	}
}