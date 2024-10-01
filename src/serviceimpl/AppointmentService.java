package serviceimpl;

import java.time.LocalDate;
import java.util.List;

import entity.Appointment;
import entity.PatientAppointment;
import entity.RoomAppointment;

public interface AppointmentService {
	public void insert(Appointment appointment, RoomAppointment roomAppointment, PatientAppointment patientAppointment,
			String type);

	public void update(Appointment appointment, RoomAppointment roomAppointment, PatientAppointment patientAppointment,
			String type);

	public int getToken(LocalDate date, String idcode);

	public List<PatientAppointment> getPatient(String cond);

	public List<PatientAppointment> findgetPatient(PatientAppointment patientAppointment, String cond);

	public boolean isBoken(String id_code, LocalDate date, String dn_id);

	public void updateStatus(String p_id, LocalDate date, int select, String d_id);

	public List<RoomAppointment> searchAppointmentRoomList(RoomAppointment roomAppointment, boolean select);

	public boolean isAppointment(String id_code);

	public List<Integer> cancelAutoAppointment();

	public void cancelAppointment(String id_code, LocalDate date);

	public List<RoomAppointment> getRoomAppointment();

	public List<RoomAppointment> getRoomAppointment2();
}
