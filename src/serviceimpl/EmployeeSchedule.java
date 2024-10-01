package serviceimpl;

import java.util.List;

import entity.ESchedule;
import entity.StaffDNScuhdule;

public interface EmployeeSchedule {
	public void insert(ESchedule employeeSchedule, String employeeType);

	public List<StaffDNScuhdule> getAll(String type);

	public List<StaffDNScuhdule> findAll(String type, StaffDNScuhdule staffDNScuhdule);

	public int getScheduleID(int sordID, String type);

}
