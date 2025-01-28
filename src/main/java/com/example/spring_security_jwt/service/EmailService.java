package com.example.spring_security_jwt.service;

import com.example.spring_security_jwt.entity.UserInfo;
import com.example.spring_security_jwt.repository.UserInfoRepository;
import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class EmailService {

    private UserInfoRepository userInfoRepository;

    private JavaMailSender mailSender;


    Logger logger= LoggerFactory.getLogger(EmailService.class);


    public EmailService(UserInfoRepository userInfoRepository, JavaMailSender mailSender) {
        this.userInfoRepository = userInfoRepository;
        this.mailSender = mailSender;

//        getEmailIds("pooja");
//        sendEmailTest("pooja", "text", "this is test email from constructor");
        sendMultipleEmailIdTest("pooja", "text", "this is test email from constructor");
    }

    public void sendEmailWithAttachment(String name, String subject, String text, MultipartFile file) throws MessagingException, IOException {
        String[] emailId = emailFromDbByName(name);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart

        helper.setTo(emailId);
        helper.setSubject(subject);
        helper.setText(text);

        // Attach the file
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));
        }
        mailSender.send(message);
    }

    public String[] emailFromDbByName(String name) {
        List<String> toEmailId = userInfoRepository.findEmailIdByName(name);
        String[] emailId = null;
        for (String email : toEmailId) {
            emailId = email.split(",");
        }
        return emailId;
    }


    public void sendEmailWithText(String name, String subject, String text) throws MessagingException, IOException {
        String[] emailId = emailFromDbByName(name);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart

        helper.setTo(emailId);
        helper.setSubject(subject);
        helper.setText(text);
        mailSender.send(message);
    }

    public void sendEmailWithAttachments(String name, String subject, String text, List<MultipartFile> files) throws MessagingException, IOException {
        String[] emailId = emailFromDbByName(name);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart

        helper.setTo(emailId);
        helper.setSubject(subject);
        helper.setText(text);

        // Attach all files
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                if (fileName != null) {
                    helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));
                }
            }
        }

        mailSender.send(message);
    }


    public void sendEmail(String name, String subject, String body) {
        Optional<UserInfo> email = userInfoRepository.findByName(name);
        if (email != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.get().getEmail());
            message.setCc(email.get().getCcEmail());
            message.setBcc(email.get().getBccEmail());
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } else {
            throw new RuntimeException("Email not found for name: " + name);
        }
    }


    public void sendEmailWithSingleAttachment(String name, String subject, String body, File attachment) {
        Optional<UserInfo> email = userInfoRepository.findByName(name);
        if (email != null) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo(email.get().getEmail());
                helper.setCc(email.get().getCcEmail());
                helper.setBcc(email.get().getBccEmail());
                helper.setSubject(subject);
                helper.setText(body);

                if (attachment != null) {
                    FileSystemResource file = new FileSystemResource(attachment);
                    helper.addAttachment(file.getFilename(), file);
                }

                mailSender.send(message);
            } catch (MessagingException e) {
                logger.error(e.getMessage());
            }
        } else {
            throw new RuntimeException("Email not found for name: " + name);
        }
    }

    public void sendEmailWithMultipleAttachments(String name, String subject, String body, List<File> attachments) {
        Optional<UserInfo> email = userInfoRepository.findByName(name);
        if (email != null) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart

                helper.setTo(email.get().getEmail());
                helper.setCc(email.get().getCcEmail());
                helper.setBcc(email.get().getBccEmail());
                helper.setSubject(subject);
                helper.setText(body);

                // Add attachments if provided
                if (attachments != null) {
                    for (File attachment : attachments) {
                        FileSystemResource file = new FileSystemResource(attachment);
                        helper.addAttachment(file.getFilename(), file);
                    }
                }

                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Error sending email", e);
            }
        } else {
            throw new RuntimeException("Email not found for name: " + name);
        }
    }


    // fresh code => get EmailId & return
    public List<String> getEmailIds(String username) {
        List<String> allEmailIds = userInfoRepository.getEmailId(username);
        return allEmailIds;
    }

    public void sendEmailTest(String name, String subject, String body) {
        List<String> email = getEmailIds(name);
        String toEmailId = null;
        String ccEmailId = null;
        String bccEmailId = null;
        // to get to Email, bcc & cc email
        for (String allemail : email) {
            String[] indivisualEmail = allemail.split(",");
            toEmailId = indivisualEmail[0];
            ccEmailId = indivisualEmail[1];
            bccEmailId = indivisualEmail[2];
        }
        if (email != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmailId);
            message.setCc(ccEmailId);
            message.setBcc(bccEmailId);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("email sent successfully");
        } else {
            throw new RuntimeException("Email not found for name: " + name);
        }
    }

    //sible as well as multiple
    public void sendMultipleEmailIdTest(String name, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        List<List<Object>> email = userInfoRepository.getListEmailId(name);
            if (!email.isEmpty()) {
                for (List<Object> allemail : email) {
                    String[] toEmailId = String.valueOf(allemail.get(0)).split(",");   //toEmailId="pritinswami@gmail.com,daya.swami@gmail.com"
                    String[] ccEmailId = String.valueOf(allemail.get(1)).split(",");
                    String[] bccEmailId = String.valueOf(allemail.get(2)).split(",");
                    message.setTo(toEmailId);
                    message.setCc(ccEmailId);
                    message.setBcc(bccEmailId);
                }
                message.setSubject(subject);
                message.setText(body);
                mailSender.send(message);
                System.out.println("email sent successfully");
            } else {
                System.out.println("emailId not found");
            }
        }
    }






