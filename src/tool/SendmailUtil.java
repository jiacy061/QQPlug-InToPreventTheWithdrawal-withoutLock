package tool;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendmailUtil {

	// 设置服务器
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_SMTP = "smtp.163.com";
	// 服务器验证
	private static String KEY_PROPS = "mail.smtp.auth";
	private static boolean VALUE_PROPS = true;
	// 发件人用户名、密码
	private static String SEND_USER_ACCOUNT = "jiangsysteminfo@163.com";
	private static String SEND_USER_NAME = "电脑返回信息查询系统";
	private static String SEND_PWD = "a782341956";
	// 建立会话
	private static MimeMessage message;
	private static Session s;

	/*
	 * 初始化方法
	 */
	private static void initSendmailUtil() {
		Properties props = System.getProperties();
		props.setProperty(KEY_SMTP, VALUE_SMTP);
		props.put(KEY_PROPS, "true");
		// props.put("mail.smtp.auth", "true");
		s = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SEND_USER_ACCOUNT, SEND_PWD);
			}
		});
		s.setDebug(true);
		message = new MimeMessage(s);
	}

	/**
	 * 发送邮件
	 * 
	 * @param headName
	 *            邮件头文件名
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public static void doSendHtmlEmail(String headName, String sendHtml, String receiveUser) {
		initSendmailUtil();
		try {
			// 发件人
			InternetAddress from = new InternetAddress(SEND_USER_ACCOUNT, SEND_USER_NAME, "UTF-8");
			message.setFrom(from);
			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);
			// 邮件标题
			message.setSubject(headName);
			String content = sendHtml.toString();
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(content, "text/html;charset=GBK");
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(VALUE_SMTP, SEND_USER_ACCOUNT, SEND_PWD);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("send success!");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		SysInfoManager sysinfomanager = new SysInfoManager();
//		String wlanAddress = sysinfomanager.getWlanAddress();
//		String computerName = sysinfomanager.getComputerName();
//		SendmailUtil.doSendHtmlEmail(computerName+"的系统返回信息", "computerName:"
//				+ computerName + "<br>wlanAddress:" + wlanAddress , "jiacyc@sohu.com");
//	}
}