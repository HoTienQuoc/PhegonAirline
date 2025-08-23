package com.phegon.PhegonAirline;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

@SpringBootApplication
public class PhegonAirlineApplication {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public static void main(String[] args) {
		SpringApplication.run(PhegonAirlineApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(){
		return args -> {
			try{
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(
					mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name()
				);
				helper.setTo("hotienquoc0429@gmail.com");
				helper.setSubject("Hello Testing");
				helper.setText("Testing 123");
				System.out.println("Testing email");
				javaMailSender.send(mimeMessage);
			}
			catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		};
	}

}
