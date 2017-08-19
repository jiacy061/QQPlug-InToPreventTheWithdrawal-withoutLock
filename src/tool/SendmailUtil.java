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

	// ���÷�����
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_SMTP = "smtp.163.com";
	// ��������֤
	private static String KEY_PROPS = "mail.smtp.auth";
	private static boolean VALUE_PROPS = true;
	// �������û���������
	private static String SEND_USER_ACCOUNT = "jiangsysteminfo@163.com";
	private static String SEND_USER_NAME = "���Է�����Ϣ��ѯϵͳ";
	private static String SEND_PWD = "a782341956";
	// �����Ự
	private static MimeMessage message;
	private static Session s;

	/*
	 * ��ʼ������
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
	 * �����ʼ�
	 * 
	 * @param headName
	 *            �ʼ�ͷ�ļ���
	 * @param sendHtml
	 *            �ʼ�����
	 * @param receiveUser
	 *            �ռ��˵�ַ
	 */
	public static void doSendHtmlEmail(String headName, String sendHtml, String receiveUser) {
		initSendmailUtil();
		try {
			// ������
			InternetAddress from = new InternetAddress(SEND_USER_ACCOUNT, SEND_USER_NAME, "UTF-8");
			message.setFrom(from);
			// �ռ���
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);
			// �ʼ�����
			message.setSubject(headName);
			String content = sendHtml.toString();
			// �ʼ�����,Ҳ����ʹ���ı�"text/plain"
			message.setContent(content, "text/html;charset=GBK");
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			// smtp��֤���������������ʼ��������û�������
			transport.connect(VALUE_SMTP, SEND_USER_ACCOUNT, SEND_PWD);
			// ����
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("send success!");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		SysInfoManager sysinfomanager = new SysInfoManager();
//		String wlanAddress = sysinfomanager.getWlanAddress();
//		String computerName = sysinfomanager.getComputerName();
//		SendmailUtil.doSendHtmlEmail(computerName+"��ϵͳ������Ϣ", "computerName:"
//				+ computerName + "<br>wlanAddress:" + wlanAddress , "jiacyc@sohu.com");
//	}
}