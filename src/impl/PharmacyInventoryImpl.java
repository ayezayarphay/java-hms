package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Pharmacy;
import entity.PharmacyType;
import entity.ShowPharmacyData;
import serviceimpl.PharmacyInventoryService;
import utils.Connector;

public class PharmacyInventoryImpl implements PharmacyInventoryService {
	public void addObject(PreparedStatement stmt, Pharmacy pharmacy) throws SQLException {
		stmt.setString(1, pharmacy.getId_code());
		stmt.setString(2, pharmacy.getName());
		stmt.setString(3, pharmacy.getPharmacyType().toString());
		stmt.setDouble(4, pharmacy.getPrice());
		stmt.setInt(5, pharmacy.getTotal_qty());
	}

	@Override
	public void insert(Pharmacy pharmacy) {
		String sql = "INSERT INTO `pharmacy_inventory`( `id_code`, `name`, `category`, `price`, `total_qty`) VALUES(?,?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, pharmacy);
			stmt.executeUpdate();
			System.out.println("Added one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(Pharmacy pharmacy) {
		String sql = "UPDATE `pharmacy_inventory` SET `id_code`=?,`name`=?,`category`=?,`price`=?,`total_qty`=? WHERE pi_id=?";

		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, pharmacy);
			stmt.setInt(6, pharmacy.getPhra_id());
			stmt.executeUpdate();
			System.out.println("Update one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void reduceQty(Pharmacy pharmacy) {
		String sql = "UPDATE `pharmacy_inventory` SET `total_qty`=? WHERE pi_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, pharmacy.getTotal_qty());
			stmt.setInt(2, pharmacy.getPhra_id());
			stmt.executeUpdate();
			System.out.println("Reduced qty");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public int getPharmacyID(Pharmacy pharmacy) {
		String sql = "SELECT pi_id FROM pharmacy_inventory WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, pharmacy.getId_code());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	public Pharmacy getObject(ResultSet rs) throws Exception {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setPhra_id(rs.getInt(1));
		pharmacy.setId_code(rs.getString(2));
		pharmacy.setName(rs.getString(3));
		pharmacy.setPharmacyType(PharmacyType.valueOf(rs.getString(4)));
		pharmacy.setPrice(rs.getDouble(5));
		pharmacy.setTotal_qty(rs.getInt(6));
		return pharmacy;
	}

	@Override
	public List<Pharmacy> find() {
		List<Pharmacy> list = new ArrayList<Pharmacy>();
		String sql = "SELECT * FROM pharmacy_inventory";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public String defindAndORWhere(String sql) {
		if (!sql.contains("WHERE")) {
			sql += " WHERE ";
		} else if (!sql.contains("AND") || sql.contains("WHERE") && sql.contains("AND")) {
			sql += " AND ";
		}
		return sql;
	}

	@Override
	public List<Pharmacy> findAll(Pharmacy pharmacy) {
		List<Pharmacy> list = new ArrayList<Pharmacy>();
		String sql = "SELECT * FROM pharmacy_inventory";
		if (!pharmacy.getId_code().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "id_code LIKE'%" + pharmacy.getId_code() + "%'";
		}
		if (!pharmacy.getName().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "name LIKE'%" + pharmacy.getName() + "%'";
		}
		if (pharmacy.getPharmacyType() != null) {
			sql = defindAndORWhere(sql);
			sql += "category ='" + pharmacy.getPharmacyType() + "'";
		}
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public Pharmacy findByIDCode(String id_code) {
		String sql = "SELECT * FROM pharmacy_inventory WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, id_code);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<ShowPharmacyData> findPharmacy(String name) {
		List<ShowPharmacyData> list = new ArrayList<ShowPharmacyData>();
		String sql = "SELECT * FROM pharmacy_inventory WHERE name LIKE '%" + name + "%'";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				ShowPharmacyData showPharmacyData = new ShowPharmacyData();
				showPharmacyData.setId_code(rs.getString("id_code"));
				showPharmacyData.setName(rs.getString("name"));
				showPharmacyData.setTotal(rs.getInt("total_qty"));
				showPharmacyData.setPrice(rs.getDouble("price"));
				list.add(showPharmacyData);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public int getQty(String idcode) {
		String sql = "SELECT total_qty FROM pharmacy_inventory WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, idcode);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	@Override
	public void updateQty(String cond, String idcode, int qty) {
		String sql = "";
		if (cond.equals("+")) {
			sql = "UPDATE `pharmacy_inventory` SET total_qty=(total_qty+?) WHERE id_code=?";
		} else {
			sql = "UPDATE `pharmacy_inventory` SET total_qty=(total_qty-?) WHERE id_code=?";
		}
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, qty);
			stmt.setString(2, idcode);
			stmt.executeUpdate();
			System.out.println("Update qty");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
