package serviceimpl;

import java.util.List;

import entity.DN;

public interface DNService {
	public void insert(DN dns);

	public void update(DN dns);

	public List<DN> getAllData();

	public void delete(int id);

	public int getID(DN dns);

	public DN finByID(int id);

	public DN findByidCode(String id_code);

	public List<DN> findAll(DN dn);

	public List<String> getDNInfo(String name);

	public double getSalary(String idcode);

}
