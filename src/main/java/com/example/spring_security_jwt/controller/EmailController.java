package com.example.spring_security_jwt.controller;


import com.example.spring_security_jwt.service.EmailService;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/feature")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;


//    @PostMapping("/sendwithsingleattachment")
//    public ResponseEntity<String> sendEmailWithAttachment(
//            @RequestPart("name") String name,
//            @RequestPart("subject") String subject,
//            @RequestPart("text") String text,
//            @RequestPart("file") MultipartFile file) {
//
//        try {
//            emailService.sendEmailWithAttachment(name, subject, text, file);
//            return ResponseEntity.ok("Email sent successfully");
//        } catch (IOException | MessagingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to send email: " + e.getMessage());
//        }
//    }



    @PostMapping("/sendmultiplefiles")
    public ResponseEntity<String> sendEmailWithAttachments(
            @RequestPart("name") String name,
            @RequestPart("subject") String subject,
            @RequestPart("text") String text,
            @RequestPart("files") List<MultipartFile> files) {

        try {
            emailService.sendEmailWithAttachments(name, subject, text, files);
            return ResponseEntity.ok("Email sent successfully");
        } catch (IOException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/sendmail")
    public ResponseEntity<String> sendEmailMessage(
            @RequestPart("name") String name,
            @RequestPart("subject") String subject,
            @RequestPart("text") String text) {
        try {
            emailService.sendEmailWithText(name, subject, text);
            return ResponseEntity.ok("Email sent successfully");
        } catch (IOException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }


//    @PostMapping("/sendsimpleemail")
//    public String sendEmail(@RequestParam String name,@RequestParam String subject,@RequestParam String body)
//    {
//        emailService.sendEmailTestTrial(name,subject,body);
//        return "Email sent successfully!";
//    }


    @PostMapping("/sendattachment")
    public String sendEmailWithAttachment(
            @RequestPart String name,
            @RequestPart String subject,
            @RequestPart String body,
            @RequestPart(required = false) MultipartFile attachment) {

        File attachmentFile = null;
        if (attachment != null && !attachment.isEmpty()) {
            try {
                attachmentFile = File.createTempFile("attachment", attachment.getOriginalFilename());
                attachment.transferTo(attachmentFile);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store attachment", e);
            }
        }

        emailService.sendEmailWithSingleAttachment(name, subject, body, attachmentFile);

        // Clean up the temp file if it was created
        if (attachmentFile != null) {
            attachmentFile.deleteOnExit();
        }

        return "Email sent successfully!";
    }

    @PostMapping("/sendmultipleattachments")
    public String sendEmailmultipleattachments(
            @RequestPart String name,
            @RequestPart String subject,
            @RequestPart String body,
            @RequestPart(required = false) List<MultipartFile> attachments) {

        List<File> allFiles = new ArrayList<>();
        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
                if (!attachment.isEmpty()) {
                    try {
                        File attachmentFile = File.createTempFile("attachment", attachment.getOriginalFilename());
                        attachment.transferTo(attachmentFile);
                        allFiles.add(attachmentFile);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to store attachment", e);
                    }
                }
            }
        }

        emailService.sendEmailWithMultipleAttachments(name, subject, body, allFiles);

        // Clean up the temp files
        for (File file : allFiles) {
            file.deleteOnExit();
        }

        return "Email sent successfully!";
    }


}




