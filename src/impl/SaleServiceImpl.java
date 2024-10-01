package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import serviceimpl.SaleService;
import utils.Connector;

public class SaleServiceImpl implements SaleService {

	@Override
	public int insert(int staff, int patient) {
		int id = 0;
		String sql = "INSERT INTO `sale`(`staff_id`, `p_id`) VALUES (?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, staff);
			stmt.setInt(2, patient);
			stmt.executeUpdate();
			System.out.println("Added");

		} catch (Exception e) {
			e.getStackTrace();
		}
		String sql2 = "SELECT sale_id from sale ORDER BY sale_id DESC LIMIT 1";
		try (ResultSet rs = Connector.getResultSet(sql2)) {
			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return id;
	}

}
