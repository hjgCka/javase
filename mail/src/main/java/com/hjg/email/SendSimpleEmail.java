package com.hjg.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class SendSimpleEmail {

    String  to = "", //收件人
            subject = null, //主题
            from = null,//发件人
            fromUser = null, fromPassword = null,
            //抄送
            cc = null,
                    //暗抄送，密送
                    bcc = null,
                            //收件邮箱的url，可能包含host,user,pwd信息。用于读取邮件。
                            url = null;

    //发送邮件的服务器地址
    String mailhost = null;

    //X-Mailer是代理发信的客户端，如这里是Coremail，代表是从Coremail的Webmail发出的信件(163.net)，
    // 如果是从Outlook发出的，X-Mailer内容会是这样的，X-Mailer: Microsoft Outlook Express 5.50.4807.1700
    String mailer = "msgsend";

    String file = null;

    //协议，收件邮箱地址，收件邮箱用户，收件邮箱的密码
    String protocol = null, host = null, user = null, password = null;
    String record = null;	// name of folder in which to record mail

    SendSimpleEmail() {
        this.mailhost = "smtp.163.com";
        this.to = "1179214342@qq.com";
        this.subject = "测试发送邮件";
        this.from = "computer_cka@163.com";
        this.fromUser = "computer_cka";
        this.fromPassword = "19921023abcde";

        //pop3协议不支持appendMessages方法
        //如果需要获取邮件的imap/smtp/pop3等地址，需要查看对应邮件服务商的帮助文档
        this.url = "imap://computer_cka:19921023abcde@imap.163.com:143";
        this.record = "备份文件夹";
    }

    public static void main(String[] args) throws MessagingException, IOException {

        SendSimpleEmail sendSimpleEmail = new SendSimpleEmail();

        /*
         * Initialize the Jakarta Mail Session.
         */
        Properties props = System.getProperties();
        // XXX - could use Session.getTransport() and Transport.connect()
        // XXX - assume we're using SMTP
        if (sendSimpleEmail.mailhost != null)
            props.put("mail.smtp.host", sendSimpleEmail.mailhost);


        // Get a Session object
        Session session = Session.getInstance(props, null);

        /*
         * Construct the message and send it.
         */
        Message msg = new MimeMessage(session);
        if (sendSimpleEmail.from != null)
            msg.setFrom(new InternetAddress(sendSimpleEmail.from));
        else
            msg.setFrom();

        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(sendSimpleEmail.to, false));
        if (sendSimpleEmail.cc != null)
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(sendSimpleEmail.cc, false));
        if (sendSimpleEmail.bcc != null)
            msg.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(sendSimpleEmail.bcc, false));

        msg.setSubject(sendSimpleEmail.subject);

        String text = "hello world ... 备份邮件";

        //设置附件
        if (sendSimpleEmail.file != null) {
            // Attach the specified file.
            // We need a multipart message to hold the attachment.
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(text);
            MimeBodyPart mbp2 = new MimeBodyPart();
            mbp2.attachFile(sendSimpleEmail.file);
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            msg.setContent(mp);
        } else {
            // If the desired charset is known, you can use
            // setText(text, charset)
            msg.setText(text);
        }

        msg.setHeader("X-Mailer", sendSimpleEmail.mailer);
        msg.setSentDate(new Date());

        // send the thing off
        Transport.send(msg, sendSimpleEmail.fromUser, sendSimpleEmail.fromPassword);

        System.out.println("\nMail was sent successfully.");

        sendSimpleEmail.pullAndSave(session, msg);
    }

    private void pullAndSave(Session session, Message msg) throws MessagingException {
        /*
         * Save a copy of the message, if requested.
         */
        if (record != null) {
            // Get a Store object
            Store store = null;
            if (url != null) {
                URLName urln = new URLName(url);
                store = session.getStore(urln);
                store.connect();
            } else {
                if (protocol != null)
                    store = session.getStore(protocol);
                else
                    store = session.getStore();

                // Connect
                if (host != null || user != null || password != null)
                    store.connect(host, user, password);
                else
                    store.connect();
            }

            // Get record Folder.  Create if it does not exist.
            Folder folder = store.getFolder(record);
            if (folder == null) {
                System.err.println("Can't get record folder.");
                System.exit(1);
            }
            if (!folder.exists())
                folder.create(Folder.HOLDS_MESSAGES);

            Message[] msgs = new Message[1];
            msgs[0] = msg;
            folder.appendMessages(msgs);

            System.out.println("Mail was recorded successfully.");
        }
    }
}
