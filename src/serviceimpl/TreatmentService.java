package serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import entity.Treatment;
import entity.TreatmentDetails;

public interface TreatmentService {
	public void insert(Treatment treatment);

	public boolean isHospital(Treatment treatment);

	public List<Treatment> getTemporaryData();

	public List<Treatment> findTemporaryData(Treatment treatment);

	public List<Treatment> findHospitalData(Treatment treatment);

	public List<Treatment> findHospitalDatatreatment(Treatment treatment);

	public int getTemporaryID(Treatment treatment);

	public void updateCash(Treatment treatment);

	public boolean isTemprary(Treatment treatment);

	public List<Treatment> getHospitalData();

	public void updateLeaveDate(LocalDate date, int p_id, LocalDate date1);

	public Map<String, Integer> getCureTypeTotal();

	public void insertTreatmentDetails(List<TreatmentDetails> list);

	public int getTreatmentID(String pCode, LocalDate date, String dCode);
}
