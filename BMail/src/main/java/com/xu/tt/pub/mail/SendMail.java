package com.xu.tt.pub.mail;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-08-14 22:18:06
 * @tips 对于163邮箱服务器会产生的其他问题，参见：http://help.163.com/09/1224/17/5RAJ4LMH00753VB8.html
 */
@Slf4j
@Service
@Profile("dev") // 开发环境的时候(表明只有Spring定义的Profile为dev时才会实例化MailService这个类)
public class SendMail {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;

	private static String MAIL_FROM = "zgc_xudong@163.com";
	private static String MAIL_TO = "solaxd@163.com";

	/**
	 * @tips LOOK 发送文本
	 */
	public void send1() throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(MAIL_FROM);
		mail.setTo(MAIL_TO);
		mail.setSubject("我是邮件主题");
		mail.setText("我是邮件内容");

		mailSender.send(mail);
		log.info("########## 文本邮件已发送");
	}

	/**
	 * @tips LOOK 发送附件
	 * @tips org.springframework.core.io.FileSystemResource
	 */
	public void send2() throws Exception {
		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		// 基本设置
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		helper.setSubject("我是邮件主题");
		helper.setText("我是邮件内容");
		// 附件1,获取文件对象
		FileSystemResource f1 = new FileSystemResource(new File("D:\\tt\\03.png"));
		// 添加附件，这里第一个参数是在邮件中显示的名称，也可以直接是head.jpg，但是一定要有文件后缀，不然就无法显示图片了
		helper.addAttachment("头像1.jpg", f1);
		// 附件2
		FileSystemResource f2 = new FileSystemResource(new File("D:\\tt\\05.png"));
		helper.addAttachment("头像2.jpg", f2);

		mailSender.send(mail);
		log.info("########## 附件邮件已发送");
	}

	/**
	 * @tips LOOK 发送静态资源（邮件内容中可能希望通过嵌入图片等静态资源）
	 */
	public void send3() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		// 基本设置
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		helper.setSubject("我是邮件主题");
		// 邮件内容，第二个参数指定发送的是HTML格式
		// 这里需要注意的是addInline函数中资源名称head需要与正文中cid:head对应起来
		// 说明：嵌入图片<img src='cid:head'/>，其中cid:是固定的写法，而aaa是一个contentId
		helper.setText("<body>我是邮件内容：<img src='cid:head' /></body>", true);
		FileSystemResource file = new FileSystemResource(new File("D:\\tt\\05.png"));
		helper.addInline("head", file);

		mailSender.send(mimeMessage);
		log.info("########## 静态资源邮件已发送");
	}

	/**
	 * @tips LOOK 发送模板
	 */
	public void send4() throws Exception {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("userName", "Sola_徐");
		map.put("createDate", LocalDateTime.now());
		map.put("exportUrl", "bing.com");

		Context context = new Context();
		context.setLocale(Locale.CHINA);
		context.setVariables(map);
		String content = templateEngine.process("MAIL_TEMPLATE_1", context);

		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setFrom(MAIL_FROM);
		helper.setTo(MAIL_TO);
		helper.setSubject("我是邮件主题");
		helper.setText(content, true);

		mailSender.send(mail);
		log.info("########## 模板邮件已发送");
	}

}
