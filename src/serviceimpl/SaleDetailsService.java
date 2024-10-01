package serviceimpl;

import java.time.LocalDate;
import java.util.List;

import entity.PharmacySaleHistory;
import entity.SaleDetails;

public interface SaleDetailsService {
	public void insert(List<SaleDetails> details);

	public List<PharmacySaleHistory> getPharmacySaleSummary();

	public List<PharmacySaleHistory> findDate(LocalDate date);
}
