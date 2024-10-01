package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.PharmacySaleHistory;
import entity.SaleDetails;
import serviceimpl.SaleDetailsService;
import utils.Connector;

public class SaleDetailsServiceImpl implements SaleDetailsService {

	public void addObject(PreparedStatement stmt, SaleDetails details) throws SQLException {
		stmt.setInt(1, details.getSaleId());
		stmt.setInt(2, details.getPharmacyId());
		stmt.setInt(3, details.getQty());
		stmt.setString(4, details.getDate().toString());

	}

	@Override
	public void insert(List<SaleDetails> details) {
		String sql = "INSERT INTO `sale_details`(`s_id`, `phar_id`, `qty`, `date`) VALUES (?,?,?,?)";

		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			for (SaleDetails saleDetails : details) {
				addObject(stmt, saleDetails);
				stmt.addBatch();
			}
			stmt.executeBatch();
			System.out.println("Added.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public PharmacySaleHistory getObject(ResultSet rs) throws Exception {
		PharmacySaleHistory pharmacySaleHistory = new PharmacySaleHistory();
		pharmacySaleHistory.setStaffID(rs.getString(1));
		pharmacySaleHistory.setPatientID(rs.getString(2));
		pharmacySaleHistory.setPharName(rs.getString(3));
		pharmacySaleHistory.setDate(LocalDate.parse(rs.getString(4)));
		pharmacySaleHistory.setPrice(rs.getDouble(5));
		pharmacySaleHistory.setQty(rs.getInt(6));
		pharmacySaleHistory.setTotal(rs.getDouble(7));
		return pharmacySaleHistory;
	}

	@Override
	public List<PharmacySaleHistory> getPharmacySaleSummary() {
		List<PharmacySaleHistory> list = new ArrayList<PharmacySaleHistory>();
		String sql = "SELECT date, SUM(total) FROM( SELECT sd.date,SUM(phar.price*sd.qty) AS Total FROM sale_details sd,pharmacy_inventory phar,sale sa WHERE sd.s_id=sa.sale_id AND phar.pi_id=sd.phar_id GROUP by sd.date UNION ALL SELECT td.date,SUM(phar.price*td.qty) AS Total FROM treatment_details td,pharmacy_inventory phar,treatment t WHERE td.t_id=t.t_id AND phar.pi_id=td.phar_id GROUP by td.date)details GROUP by date";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				LocalDate date = LocalDate.parse(rs.getString(1));
				double total = rs.getDouble(2);
				PharmacySaleHistory pharmacySaleHistory = new PharmacySaleHistory(date, total);
				list.add(pharmacySaleHistory);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	@Override
	public List<PharmacySaleHistory> findDate(LocalDate date) {
		List<PharmacySaleHistory> list = new ArrayList<PharmacySaleHistory>();
		String sql = "SELECT date, SUM(total) FROM( SELECT sd.date,SUM(phar.price*sd.qty) AS Total FROM sale_details sd,pharmacy_inventory phar,sale sa WHERE sd.s_id=sa.sale_id AND phar.pi_id=sd.phar_id GROUP by sd.date UNION ALL SELECT td.date,SUM(phar.price*td.qty) AS Total FROM treatment_details td,pharmacy_inventory phar,treatment t WHERE td.t_id=t.t_id AND phar.pi_id=td.phar_id GROUP by td.date)details "
				+ " WHERE date='" + date + "'" + " GROUP BY date";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				LocalDate date1 = LocalDate.parse(rs.getString(1));
				double total = rs.getDouble(2);
				PharmacySaleHistory pharmacySaleHistory = new PharmacySaleHistory(date1, total);
				list.add(pharmacySaleHistory);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

}
