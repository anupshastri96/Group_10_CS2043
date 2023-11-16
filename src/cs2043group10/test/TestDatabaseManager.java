
package cs2043group10.test;

import cs2043group10.DatabaseException;
import cs2043group10.IDatabase;
import cs2043group10.IReversable;
import cs2043group10.IReversableManager;
import cs2043group10.data.FinancialDocument;
import cs2043group10.data.FinancialQuery;
import cs2043group10.data.LoginClass;
import cs2043group10.data.MedicalDocument;
import cs2043group10.data.MedicalQuery;
import cs2043group10.data.PatientInformation;
import cs2043group10.data.PatientQuery;
import cs2043group10.data.IQuery;

public class TestDatabaseManager implements IDatabase {
	private int loggedInId;
	private String loggedInName;
	private LoginClass loginClass;
	private final IReversableManager manager;
	
	public TestDatabaseManager(IReversableManager manager) {
		this.manager = manager;
		loggedInId = -1;
		loginClass = LoginClass.NOT_LOGGED_IN;
		loggedInName = null;
	}
	
	@Override
	public LoginClass tryLogin(int id, String password) throws DatabaseException {
		// Try to login using id and password.
		/**throw new RuntimeException("Not yet implemented.");*/
		this.loggedInId = id;
		this.loggedInName = "Newling, Ben";
		this.loginClass = LoginClass.DOCTOR;
		return loginClass;
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
	}
	
	@Override
	public IReversable instantiateHomeView() throws DatabaseException {
		return loginClass.instantiateHomeView(manager, loggedInId);
	}
	
	@Override
	public PatientInformation queryPatientInformation(int patientId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public MedicalDocument queryMedicalDocument(int documentId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public FinancialDocument queryFinancialDocument(int documentId) throws DatabaseException {
		// TODO
		return null;
	}
	
	@Override
	public IQuery<PatientQuery.PatientEntry> queryPatientsUnderDoctor(int doctorId) throws DatabaseException {
		// TODO
		return new PatientQuery(new PatientQuery.PatientEntry[] {
			new PatientQuery.PatientEntry("Petersen, James", 1),
			new PatientQuery.PatientEntry("Young, James", 2),
			new PatientQuery.PatientEntry("Oduntan, James", 3),
			new PatientQuery.PatientEntry("Young, Ian", 4),
			new PatientQuery.PatientEntry("Petersen, Ian", 5),
			new PatientQuery.PatientEntry("Oduntan, Ian", 6),
			new PatientQuery.PatientEntry("Oduntan, Boluwatife", 7),
			new PatientQuery.PatientEntry("Young, Boluwatife", 8),
			new PatientQuery.PatientEntry("Petersen, Boluwatife", 9)
		});
	}
	
	@Override
	public IQuery<MedicalQuery.MedicalEntry> queryMedicalDocumentsUnderPatient(int patientId) throws DatabaseException {
		// TODO
		return new MedicalQuery(patientId, new MedicalQuery.MedicalEntry[] {
			new MedicalQuery.MedicalEntry(1697489009, 1697489009, "Cold Prescription", "Prescription", 1),
			new MedicalQuery.MedicalEntry(1597489009, 1627489009, "Foot Brace", "Device", 2),
			new MedicalQuery.MedicalEntry(1607489009, 1647489009, "Open Heart Surgery", "Surgery", 3)
		});
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
}