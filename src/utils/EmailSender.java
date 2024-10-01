package utils;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.validator.routines.EmailValidator;

import application.HMSException;
import entity.Staff;
import impl.StaffServiceImpl;
import serviceimpl.StaffService;

public class EmailSender {
	static String txt = "";
	static StaffService service = new StaffServiceImpl();

	public static String generatePassword() {
		String password = "";
		String array[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Random r = new Random();
		for (int i = 1; i <= 10; i++) {
			password += array[r.nextInt(array.length - 1)];
		}
		return password;
	}

	public static void changePassword(String userID, String email) throws IOException {
		if (userID.isEmpty() || email.isEmpty()) {
			throw new HMSException("Please enter your UserID or Email.");
		}
		if (!EmailValidator.getInstance().isValid(email)) {
			throw new HMSException("Please enter your Email address correctly.");
		}
		String generatePassword = generatePassword();
		Staff staff = service.findByIdCode(userID);
		String sub = "Changing your password";
		service.changePassword(staff.getId_code(), generatePassword);
		String msg = "Dear " + staff.getName() + "," + "\n" + "Your previous password is change." + "\n"
				+ "Enter this password to system" + "\n" + "Password:" + generatePassword + "\n"
				+ "Please use this password to enter the system.";
		txt = "amF2YXRoaWhhNzc3";
		
		String pass = PasswordEncypt.decode(txt);
		if (staff != null) {
			send("thantsin7755@gmail.com", pass, email.trim(), sub, msg);
		}
	}

	public static void send(String from, String password, String to, String sub, String msg) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setText(msg);
			System.out.println("Before");
			Transport.send(message);
			System.out.println("message sent successfully");

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}
}
