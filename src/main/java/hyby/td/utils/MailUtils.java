package hyby.td.utils;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * 邮件发送工具类
 */
public class MailUtils {

    private static String smtp_host = "smtp.mxhichina.com";

    private static String username = "xmdc@rxjy.com";
    private static String password = "Aa123456";

    private static String from = "xmdc@rxjy.com"; // 使用当前账户


    public static void sendMail(String subject, String content, String to) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtp_host);
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=utf-8");
            Transport transport = session.getTransport();
            transport.connect(smtp_host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败...");
        }
    }

    public static void main(String[] args) {
        sendMail("111","dfsfs","cuimangang@163.com");
    }
}
