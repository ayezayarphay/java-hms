package serviceimpl;

import java.time.LocalDate;

public interface PaymentService {
	public int insert(LocalDate date, double price, int t_id);
}
