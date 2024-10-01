package utils;

import entity.Staff;

public class MemberVerify {
	private static Staff staff;

	public static Staff getStaff() {
		return staff;
	}

	public static void setStaff(Staff staff) {
		MemberVerify.staff = staff;
	}

}
