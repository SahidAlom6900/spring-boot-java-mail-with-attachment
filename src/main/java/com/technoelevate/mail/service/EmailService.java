package com.technoelevate.mail.service;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.mail.dto.MailDto;

@Service
@javax.annotation.ParametersAreNonnullByDefault
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private ObjectMapper objectMapper;

	public void sendSimpleMessage(@Nonnull String mailDto, @Nonnull MultipartFile multipartFile)
			throws MessagingException {
		MailDto mail = getMailDto(mailDto);
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody());
		helper.setTo(mail.getTo());
		helper.addAttachment(""+multipartFile.getOriginalFilename(), multipartFile);
		emailSender.send(message);

	}

	@Nonnull
	private MailDto getMailDto(String data) {
		MailDto mailDto = MailDto.builder().build();
		try {
			mailDto = objectMapper.readValue(data, MailDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return mailDto;
	}
}
