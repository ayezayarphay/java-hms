package serviceimpl;

import java.util.List;

import entity.Patient;

public interface PatientService {
	public void insert(Patient patient);

	public void update(Patient patient);

	public List<Patient> getAllData();

	public void delete(int id);

	public int getID(Patient patient);

	public Patient findByID(int id);

	public Patient findByIdCodeBoolean(String id_code);

	public List<Patient> findAll(Patient patient);

	public List<String> findPatientInfo(String name);
}
