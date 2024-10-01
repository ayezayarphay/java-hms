package serviceimpl;

import entity.StaffDNScuhdule;

public interface ScheduleService {
	public int insert(StaffDNScuhdule schedule);

	public void update(StaffDNScuhdule schedule);

	public StaffDNScuhdule getStartAndEndTime(String id_code);

	public String getDayOfWeek(String idcode);
}
