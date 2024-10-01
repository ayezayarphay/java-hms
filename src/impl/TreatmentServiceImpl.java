package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXDatePicker;

import entity.Treatment;
import entity.TreatmentDetails;
import javafx.scene.paint.Color;
import serviceimpl.TreatmentService;
import utils.Connector;

public class TreatmentServiceImpl implements TreatmentService {

	@Override
	public void insert(Treatment treatment) {
		String sql = "INSERT INTO `treatment`(`cureType`, `pcase`, `date`, `leave_date`, `iscash`, `r_id`, `p_id`, `dn_id`, `staff_id`) VALUES (?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, treatment.getCureType().toString());
			stmt.setString(2, treatment.getPatient_case());
			stmt.setString(3, treatment.getDate().toString());
			stmt.setString(4, treatment.getLeaveDate());
			stmt.setBoolean(5, treatment.isIscash());
			stmt.setInt(6, treatment.getR_id());
			stmt.setInt(7, treatment.getP_id());
			stmt.setInt(8, treatment.getDn_id());
			stmt.setInt(9, treatment.getStaff_id());
			stmt.executeUpdate();
			System.out.println("Added one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean isHospital(Treatment treatment) {
		LocalDate date = null;
		String sql = "SELECT t.leave_date FROM treatment t,patient p WHERE t.p_id=p.p_id AND p.p_code=? AND t.iscash=0 ORDER BY t.t_id DESC LIMIT 1";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, treatment.getP_idcode());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				date = LocalDate.parse(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (date != null) {
			if (date.getMonthValue() > LocalDate.now().getMonthValue()) {
				return true;
			}
		}
		return false;
	}

	public Treatment getObject(ResultSet rs) throws Exception {
		Treatment treatment = new Treatment();
		treatment.setP_idcode(rs.getString(1));
		treatment.setP_name(rs.getString(2));
		treatment.setDn_idcode(rs.getString(3));
		treatment.setDn_name(rs.getString(4));
		treatment.setDate(LocalDate.parse(rs.getString(5)));
		treatment.setPatient_case(rs.getString(6));
		treatment.setR_id(rs.getInt(7));
		return treatment;
	}

	public Treatment getObject2(ResultSet rs) throws Exception {
		JFXDatePicker date = new JFXDatePicker();
		date.setDefaultColor(Color.rgb(64, 89, 169));
		date.setEditable(false);
		String pcode = rs.getString(1);
		String pname = rs.getString(2);
		String dncode = rs.getString(3);
		String dnname = rs.getString(4);
		LocalDate dateT = LocalDate.parse(rs.getString(5));
		String leaveDate = rs.getString(6);
		String pcase = rs.getString(7);
		int rid = rs.getInt(8);
		date.setValue(LocalDate.parse(leaveDate));
		Treatment treatment = new Treatment(pcase, dateT, leaveDate, rid, pcode, dncode, pname, dnname, date);
		return treatment;

	}

	@Override
	public List<Treatment> getTemporaryData() {
		List<Treatment> list = new ArrayList<Treatment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,t.date,t.pcase,t.r_id FROM patient p,doctors_nurses dn,treatment t WHERE p.p_id=t.p_id AND dn.dn_id=t.dn_id AND t.iscash=0 AND t.cureType='Tempory_Patient' ORDER BY t.t_id";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<Treatment> findTemporaryData(Treatment treatment) {
		List<Treatment> list = new ArrayList<Treatment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,t.date,t.pcase,t.r_id FROM patient p,doctors_nurses dn,treatment t WHERE p.p_id=t.p_id AND dn.dn_id=t.dn_id AND t.iscash=0 AND t.cureType='Tempory_Patient'";
		if (!treatment.getDn_name().isEmpty()) {
			sql += " AND dn.name LIKE '%" + treatment.getDn_name() + "%'";
		}
		if (!treatment.getP_name().isEmpty()) {
			sql += " AND p.name LIKE '%" + treatment.getP_name() + "%'";
		}
		if (treatment.getDate() != null) {
			sql += " AND t.date='" + treatment.getDate() + "'";
		}
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public int getTemporaryID(Treatment treatment) {
		String sql = "SELECT t.t_id FROM treatment t,patient p WHERE t.p_id=p.p_id AND p.p_code=? AND t.date=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, treatment.getP_idcode());
			stmt.setString(2, LocalDate.now().toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

	@Override
	public void updateCash(Treatment treatment) {
		String sql = "UPDATE treatment t,patient p,doctors_nurses dn SET t.iscash=1 WHERE t.p_id=p.p_id AND t.date=? AND p.p_code=? AND dn.dn_id=t.dn_id AND dn.id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, treatment.getDate().toString());
			stmt.setString(2, treatment.getP_idcode());
			stmt.setString(3, treatment.getDn_idcode());
			stmt.executeUpdate();
			System.out.println("Update Cash");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public boolean isTemprary(Treatment treatment) {
		String sql = "SELECT t.t_id FROM treatment t,patient p,doctors_nurses dn WHERE t.p_id=p.p_id AND t.dn_id=dn.dn_id AND p.p_code=? AND dn.id_code=? AND t.date=?";

		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, treatment.getP_idcode());
			stmt.setString(2, treatment.getDn_idcode());
			stmt.setString(3, treatment.getDate().toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public List<Treatment> getHospitalData() {
		List<Treatment> list = new ArrayList<Treatment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,t.date,t.leave_date,t.pcase,t.r_id FROM treatment t,patient p,doctors_nurses dn WHERE t.p_id=p.p_id AND dn.dn_id=t.dn_id AND t.cureType='Hospital_Patient' AND t.iscash=0 ORDER BY t.t_id";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {

				list.add(getObject2(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public static void main(String[] args) {
		System.out.println(new TreatmentServiceImpl().getTreatmentID("P-1001", LocalDate.now(), "DN-1002"));
	}

	@Override
	public void updateLeaveDate(LocalDate date, int p_id, LocalDate date1) {
		String sql = "UPDATE treatment t,patient p SET t.leave_date=? WHERE t.p_id=p.p_id AND t.p_id=? AND t.date=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, date.toString());
			stmt.setInt(2, p_id);
			stmt.setString(3, date1.toString());
			stmt.executeUpdate();
			System.out.println("Update Leave Date");
		} catch (Exception e) {

		}

	}

	@Override
	public List<Treatment> findHospitalData(Treatment treatment) {
		List<Treatment> list = new ArrayList<Treatment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,t.date,t.leave_date,t.pcase,t.r_id FROM treatment t,patient p,doctors_nurses dn WHERE t.p_id=p.p_id AND dn.dn_id=t.dn_id AND t.cureType='Hospital_Patient' AND t.iscash=0";
		if (!treatment.getP_name().isEmpty()) {
			sql += " AND p.name LIKE '%" + treatment.getP_name() + "%'";
		}
		if (!treatment.getDn_name().isEmpty()) {
			sql += " AND dn.name LIKE '%" + treatment.getDn_name() + "%'";
		}
		if (!treatment.getPatient_case().isEmpty()) {
			sql += " AND t.pcase LIKE '%" + treatment.getPatient_case() + "%'";
		}
		sql += " ORDER BY t.t_id";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject2(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public Map<String, Integer> getCureTypeTotal() {
		Map<String, Integer> list = new LinkedHashMap<String, Integer>();
		String sql = "SELECT t.cureType,COUNT(t.cureType)as Total FROM treatment t GROUP BY t.cureType";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.put(rs.getString(1), rs.getInt(2));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public void insertTreatmentDetails(List<TreatmentDetails> list) {
		String sql = "INSERT INTO `treatment_details`(`t_id`, `phar_id`, `qty`, `date`) VALUES (?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			for (TreatmentDetails treatmentDetails : list) {
				stmt.setInt(1, treatmentDetails.getT_id());
				stmt.setInt(2, treatmentDetails.getPhar_id());
				stmt.setInt(3, treatmentDetails.getQty());
				stmt.setString(4, treatmentDetails.getDate().toString());
				stmt.addBatch();
			}
			stmt.executeBatch();
			System.out.println("Added");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	@Override
	public int getTreatmentID(String pCode, LocalDate date, String dCode) {
		String sql1 = "SELECT t.t_id FROM treatment t,patient p, doctors_nurses dn WHERE t.p_id=p.p_id AND dn.dn_id=t.dn_id AND p.p_code=? and t.date=? AND dn.id_code=? ORDER BY t.t_id DESC LIMIT 1";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql1)) {
			stmt.setString(1, pCode);
			stmt.setString(2, date.toString());
			stmt.setString(3, dCode);
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
	public List<Treatment> findHospitalDatatreatment(Treatment treatment) {
		List<Treatment> list = new ArrayList<Treatment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,t.date,t.leave_date,t.pcase,t.r_id FROM treatment t,patient p,doctors_nurses dn WHERE t.p_id=p.p_id AND dn.dn_id=t.dn_id AND t.cureType='Hospital_Patient' AND t.iscash=0";
		if (!treatment.getP_name().isEmpty()) {
			sql += " AND p.name LIKE '%" + treatment.getP_name() + "%'";
		}
		if (!treatment.getDn_name().isEmpty()) {
			sql += " AND dn.name LIKE '%" + treatment.getDn_name() + "%'";
		}
		if (treatment.getDate() != null) {
			sql += " AND t.date='" + treatment.getDate() + "'";
		}
		sql += " ORDER BY t.t_id";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject2(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}
