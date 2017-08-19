package tool;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailSender  {
	private static String myEmailAccount = "jiangsysteminfo@163.com";
	private static String myEmailPassword = "a782341956";
	private static String myEmailSMTPHost = "smtp.163.com";
	private static String receiveMailAccount;
	private static String info;
	
	public static void sendMail(String _info,String mailAccount) throws Exception{
		info = _info;
		receiveMailAccount = mailAccount;
		
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", myEmailSMTPHost);
		props.setProperty("mail.smtp.auth", "true");
		
		final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);
		Transport transport = session.getTransport();
		transport.connect(myEmailAccount, myEmailPassword);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) 
			throws Exception {
        
		String userName = new String();
		String mes = new String("");
		
		// 1. ����һ���ʼ�
        MimeMessage message = new MimeMessage(session);

        // 2. From: ������
        message.setFrom(new InternetAddress(sendMail, "���Է�����Ϣ��ѯϵͳ", "UTF-8"));

        // 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, userName+" ", "UTF-8"));

        // 4. Subject: �ʼ�����
        message.setSubject("������Ϣ", "UTF-8");

        // 5. Content: �ʼ����ģ�����ʹ��html��ǩ��
        message.setContent("<p>"+userName+" "+"���  </p>"+ "<p> ������Ϣ���£�<br>"+info+"</p>"+mes, "text/html;charset=UTF-8");

        // 6. ���÷���ʱ��
        message.setSentDate(new Date());

        // 7. ��������
        message.saveChanges();

        return message;
    }
	
//	public static void main(String args[]) throws Exception{
//		SysInfoManager sysinfomanager = new SysInfoManager();
//		String wlanAddress = sysinfomanager.getWlanAddress();
//		String computerName = sysinfomanager.getComputerName();
//		MailSender mymail = new MailSender("computerName:" + computerName 
//				+ "<br>wlanAddress:" + wlanAddress, "jiacyc@sohu.com" );
//	}
	
}
