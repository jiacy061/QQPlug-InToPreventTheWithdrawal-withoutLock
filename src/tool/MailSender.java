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
		
		// 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "电脑返回信息查询系统", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, userName+" ", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("返回信息", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("<p>"+userName+" "+"你好  </p>"+ "<p> 返回信息如下：<br>"+info+"</p>"+mes, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
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
