package org.cboard.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by shengjk1 on 2016/11/4. Blog
 * Address:http://blog.csdn.net/jsjsjs1789
 */
public class SendEmail {
	//private static Logger logger = Logger.getLogger("SendEmailUtils.class");

	// 用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
	static class MyAuthenricator extends Authenticator {
		String u = null;
		String p = null;

		public MyAuthenricator(String u, String p) {
			this.u = u;
			this.p = p;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(u, p);
		}
	}

	/**
	 * 
	 * 方法名: sendMail 描述: 发送邮件 创建人: Yasha 创建时间: 2017年1月12日 下午7:19:06 版本号: v1.0
	 * 抛出异常: 参数: @param subject 参数: @param toMail 参数: @param content 参数: @param
	 * files 参数: @return 返回类型: boolean
	 */
	public static boolean sendMail(String subject, String toMail, String content, InputStream is, String fileName) {
		boolean isFlag = false;
		try {

//			 公司邮箱
			 String smtpFromMail = "mox_oa.it@mo-co.com"; //账号
			 String pwd = "EPOmoco1234"; //密码
			 int port = 25; //端口
			 String host = "smtphq.mo-co.org"; //端口

			// 163邮箱
//			String smtpFromMail = "mox_it@163.com"; // 账号
//			String pwd = "moco2018"; // 密码 即授权码
//			int port = 25; // 端口
//			String host = "smtp.163.com"; // 端口

			//
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");

			props.put("mail.smtp.port", port);
			props.put("mail.transport.protocol", "smtps");

			String[] addressList = toMail.split(",");
			InternetAddress[] internetAddress = new InternetAddress[addressList.length];
			for (int i = 0; i < addressList.length; i++) {
				internetAddress[i] = new InternetAddress(addressList[i]);
			}

			// 公司账号
			// Session session = Session.getDefaultInstance(props, new
			// MyAuthenricator("mox_oa.it", "EPOmoco1234"));

			// 163账号
			Session session = Session.getDefaultInstance(props, new MyAuthenricator("mox_oa.it", "EPOmoco1234"));
			// Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(smtpFromMail, "报表"));
				message.addRecipients(Message.RecipientType.TO, internetAddress);
				message.setSubject(subject);
				message.addHeader("charset", "UTF-8");

				/* 添加正文内容 */
				Multipart multipart = new MimeMultipart();
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content);

				contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
				multipart.addBodyPart(contentPart);

				/* 添加附件 */
				MimeBodyPart fileBody = new MimeBodyPart();
				DataSource source = new ByteArrayDataSource(is, "application/msexcel");
				fileBody.setDataHandler(new DataHandler(source));
				// 中文乱码问题
				fileBody.setFileName(MimeUtility.encodeText(fileName));
				multipart.addBodyPart(fileBody);

				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");

				transport.connect(host, port, smtpFromMail, pwd);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
			} catch (Exception e) {
				e.printStackTrace();
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}

	public static void send(String title, String context, String to, HSSFWorkbook workbook) {
		Properties prop = new Properties();
		// 协议
		prop.setProperty("mail.transport.protocol", "smtps");
		// 服务器
		prop.setProperty("mail.smtp.host", "smtphq.mo-co.org");
		// 端口
		prop.setProperty("mail.smtp.port", "25");
		// 使用smtp身份验证
		prop.setProperty("mail.smtp.auth", "true");
		// 使用SSL，企业邮箱必需！
		// 开启安全协议
		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		}
		// prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);
		// 发件人，进行权限认证
		Session session = Session.getDefaultInstance(prop, new MyAuthenricator("mox_oa.it", "EPOmoco1234"));
		// session.setDebug(true);

		HtmlEmail he = new HtmlEmail();
		try {
			he.setFrom("mox_oa.it@mo-co.com");
			he.addTo(to);
			he.setSubject(title);
			he.setSentDate(new Date());
			he.setTextMsg(context);

			ByteArrayOutputStream baos = null;
			baos = new ByteArrayOutputStream();
			try {
				workbook.write(baos);
			} catch (IOException e) {
				e.printStackTrace();
			}

			StringBuilder sb = new StringBuilder("<html>");
			if (baos != null) {
				ByteArrayDataSource ds = new ByteArrayDataSource(baos.toByteArray(), "application/octet-stream");
				he.attach(ds, "report.xls", EmailAttachment.ATTACHMENT, "test");
			}
			he.send();
		} catch (EmailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// MimeMessage mimeMessage = new MimeMessage(session);
		// try {
		// //发件人地址
		// mimeMessage.setFrom(new InternetAddress("mox_oa.it@mo-co.com"));
		// //收件人的地址
		// mimeMessage.addRecipient(Message.RecipientType.TO, new
		// InternetAddress(to));
		// mimeMessage.setSubject(title);
		// mimeMessage.setSentDate(new Date());
		// mimeMessage.setText(context);
		// mimeMessage.saveChanges();

		// } catch (Exception e) {
		// logger.error("scan 邮件异常 " +e);
		//
		// }
	}
}