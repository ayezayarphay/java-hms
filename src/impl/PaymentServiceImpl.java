package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import serviceimpl.PaymentService;
import utils.Connector;

public class PaymentServiceImpl implements PaymentService {

	@Override
	public int insert(LocalDate date, double price, int t_id) {
		String sql1 = "INSERT INTO `payment`( `date`, `doctor_price`, `t_id`) VALUES (?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql1)) {
			stmt.setString(1, date.toString());
			stmt.setDouble(2, price);
			stmt.setInt(3, t_id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql2 = "SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1";
		try (ResultSet rs = Connector.getResultSet(sql2)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

}
