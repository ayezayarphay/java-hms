package impl;

import java.sql.PreparedStatement;

import serviceimpl.PaymentDetailsService;
import utils.Connector;

public class PaymentDetailsServiceImpl implements PaymentDetailsService {

	@Override
	public void insert(int pay_id, String serviceName, double servicePrice) {
		String sql = "INSERT INTO `payment_details`(`service_name`, `service_price`,`pay_id`) VALUES (?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {

			stmt.setString(1, serviceName);
			stmt.setDouble(2, servicePrice);
			stmt.setInt(3, pay_id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
