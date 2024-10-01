package serviceimpl;

import java.util.List;

import entity.Staff;

public interface StaffService {

	public void insert(Staff staff);

	public void update(Staff staff);

	public List<Staff> getAllData();

	public void delete(int id);

	public int getID(Staff staff);

	public Staff checkVaildUser(String username, String password);

	public void changePassword(String id_code, String password);

	public List<Staff> findAll(Staff staff);

	public Staff findByIdCode(String id_code);

	public Staff findById(int id);

}
