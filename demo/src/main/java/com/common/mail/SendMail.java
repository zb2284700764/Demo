package com.common.mail;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import sun.misc.BASE64Encoder;

/**
 * 邮件发送类
 * @author user
 *
 */
public class SendMail {

	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();
	
	/**
	 * 登录服务器登录验证
	 */
	private transient MailAuthenticator authenticator;
	
	/**
	 * 邮箱session
	 */
	private transient Session session;
	
	/**
	 * 初始化邮件发送器
	 * @param smtpHostName SMTP服务器地址
	 * @param username 发件人用户名
	 * @param password 发件人密码
	 */
	public SendMail(final String smtpHostName, final String username, final String password)
	{
		init(smtpHostName,username,password);
	}
	
	/**
	 * 初始化邮件发送器
	 * @param username 发件人用户名
	 * @param password 发件人密码
	 */
	public SendMail(final String username, final String password)
	{
		final String smtpHostName = "smtp." + username.split("@")[1];
		init(smtpHostName,username,password);
	}
	
	/**
	 * 初始化
	 * @param smtpHostName SMTP服务器地址
	 * @param username 用户名
	 * @param password 密码
	 */
	private void init(String smtpHostName, String username, String password)
	{
		// 初始化 props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建 session
		session = Session.getInstance(props, authenticator);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
	}
	
	/**
	 * 发送邮件
	 * @param recipient 收件人邮箱地址
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public void send(String recipient,String subject,Object content) throws AddressException, MessagingException
	{
		// 创建 MimeMessae类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置主题
		message.setSubject(subject);
		// 设置内容
		message.setContent(content.toString(),"text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}
	
	/**
	 * 群发邮件
	 * @param recipient 收件人邮箱地址（群）
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public void send(List<String> recipients,String subject,Object content) throws AddressException, MessagingException
	{
		// 创建 MimeMessae类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		int num = recipients.size();
		InternetAddress[] address = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			address[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, address);
		// 设置主题
		message.setSubject(subject);
		// 设置内容
		message.setContent(content.toString(),"text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}
	
	/**
	 * 发送带附件的邮件
	 * @param recipient 收件人邮箱地址（群）
	 * @param subject 邮件主题
	 * @param content 邮件文本内容
	 * @param affixName 附件名称
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient,String subject,String content,String affixName) throws AddressException, MessagingException
	{
		// 创建 MimeMessae类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置主题
		message.setSubject(subject);
		/*
		 *  消息包（内容和附件）添加附件和正文
		 */
		Multipart multipart = new MimeMultipart();
//		Multipart multipart = new MimeMultipart("mixed");// 指定为混合关系  
		
		// 消息快（文本内容），设置邮件的文本内容
		BodyPart contentPart = new MimeBodyPart();
		
		// 内容消息块子消息包，混合类型的（文本内容）
		Multipart contentMultipart = new MimeMultipart();
//		Multipart contentMultipart = new MimeMultipart("related"); // 依赖关系  
		contentPart.setContent(contentMultipart);

		// 内容消息块子消息块（文本内容）设置文字样式
		BodyPart htmlPart = new MimeBodyPart();
		contentMultipart.addBodyPart(htmlPart);
		htmlPart.setContent("<font style='color:red;font-size:40px;'>"+content+"</font>", "text/html;charset=utf-8");
		
//		contentPart.setText(content);
		multipart.addBodyPart(contentPart);
		
		// 消息块（附件）添加附件
		BodyPart messageBodyPart = new MimeBodyPart();
		// 定义一个数据源对象
		DataSource ds = new FileDataSource(new File(affixName));
		// 定义一个DataHandler用来包装一个文件
		DataHandler dh = new DataHandler(ds);
		messageBodyPart.setDataHandler(dh);
		// 添加附件标题，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
		BASE64Encoder enc = new BASE64Encoder();
		// 获取上传的附件的名字，包括后缀名
		String fileName = affixName.substring(affixName.lastIndexOf(File.separatorChar)+1);
		messageBodyPart.setFileName("=?GBK?B?"+enc.encode(fileName.getBytes())+"?=");
		multipart.addBodyPart(messageBodyPart);
		
		// 将multipart对象放到message中
		message.setContent(multipart);
		// 保存邮件
		message.saveChanges();
		// 发送
		Transport.send(message);
	}

	/**
	 * 群发带附件的邮件
	 * @param recipients
	 * @param subject
	 * @param content
	 * @param affixName
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients,String subject,String content,String affixName) throws AddressException, MessagingException
	{
		// 创建 MimeMessae类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		int num = recipients.size();
		InternetAddress[] address = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			address[i] = new InternetAddress(recipients.get(i));
		}
		// 设置收件人
		message.setRecipients(RecipientType.TO, address);
		// 设置主题
		message.setSubject(subject);
		/*
		 *  消息包（内容和附件）添加附件和正文
		 */
		Multipart multipart = new MimeMultipart();
//		Multipart multipart = new MimeMultipart("mixed");// 指定为混合关系  
		
		// 消息快（文本内容），设置邮件的文本内容
		BodyPart contentPart = new MimeBodyPart();
		
		// 内容消息块子消息包，混合类型的（文本内容）
		Multipart contentMultipart = new MimeMultipart();
//		Multipart contentMultipart = new MimeMultipart("related"); // 依赖关系  
		contentPart.setContent(contentMultipart);

		// 内容消息块子消息块（文本内容）设置文字样式
		BodyPart htmlPart = new MimeBodyPart();
		contentMultipart.addBodyPart(htmlPart);
		htmlPart.setContent("<font style='color:red;font-size:40px;'>"+content+"</font>", "text/html;charset=utf-8");
		
//		contentPart.setText(content);
		multipart.addBodyPart(contentPart);
		
		// 消息块（附件）添加附件
		BodyPart messageBodyPart = new MimeBodyPart();
		// 定义一个数据源对象
		DataSource ds = new FileDataSource(new File(affixName));
		// 定义一个DataHandler用来包装一个文件
		DataHandler dh = new DataHandler(ds);
		messageBodyPart.setDataHandler(dh);
		// 添加附件标题，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
		BASE64Encoder enc = new BASE64Encoder();
		// 获取上传的附件的名字，包括后缀名
		String fileName = affixName.substring(affixName.lastIndexOf(File.separatorChar)+1);
		messageBodyPart.setFileName("=?GBK?B?"+enc.encode(fileName.getBytes())+"?=");
		multipart.addBodyPart(messageBodyPart);
		
		// 将multipart对象放到message中
		message.setContent(multipart);
		// 保存邮件
		message.saveChanges();
		// 发送
		Transport.send(message);
	}
	
	
	
	public static void main(String[] args) {
		SendMail sendMail = new SendMail("发件人地址","密码");
		try {
			sendMail.send("收件人地址", "主题", "文本内容", "E:\\学习资料\\正则表达式.docx");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
