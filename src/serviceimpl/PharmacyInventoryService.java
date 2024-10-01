package serviceimpl;

import java.util.List;

import entity.Pharmacy;
import entity.ShowPharmacyData;

public interface PharmacyInventoryService {
	public void insert(Pharmacy pharmacy);

	public void update(Pharmacy pharmacy);

	public void reduceQty(Pharmacy pharmacy);

	public int getPharmacyID(Pharmacy pharmacy);

	public List<Pharmacy> find();

	public List<Pharmacy> findAll(Pharmacy pharmacy);

	public Pharmacy findByIDCode(String id_code);

	public List<ShowPharmacyData> findPharmacy(String name);

	public int getQty(String idcode);

	public void updateQty(String cond, String idcode, int qty);
}
