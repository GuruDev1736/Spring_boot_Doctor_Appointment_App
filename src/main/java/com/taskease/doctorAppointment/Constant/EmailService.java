package com.taskease.doctorAppointment.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void postpondAppointment(String email, String date , String time , String userName, String userEmail , String phone, String doctorName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Rescheduled Appointment Notification");
        message.setText("Dear "+doctorName+",\n" +
                "\n" +
                "I hope this message finds you well. I wanted to inform you that your appointment has been postponed. The new details are as follows:\n" +
                "\n" +
                "Date: "+date+"\n" +
                "Time: "+time+"\n" +
                "User: "+userName+"\n" +
                "User Email: "+userEmail+"\n" +
                "User Phone: "+phone+"\n" +

                "\n" +
                "We apologize for any inconvenience caused and appreciate your understanding. Please let us know if you have any concerns or if this new schedule works for you.\n" +
                "\n" +
                "Thank you for your patience.\n" +
                "\n" +
                "Best regards,\n" +
                "DocConnect Team\n");
        mailSender.send(message);
    }
}