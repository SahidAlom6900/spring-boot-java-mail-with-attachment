package com.technoelevate.mail.controller;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.mail.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/mail")
@RequiredArgsConstructor
public class EmailController {
	
	private final EmailService emailService;
	
	@PostMapping
	public String sendMail(@RequestParam(name = "data",required = true) String mailDto,@RequestPart(name = "file",required = true) MultipartFile multipartFile) throws MessagingException {
		emailService.sendSimpleMessage(mailDto,multipartFile);
		return "mail Was successfully Send";
	}

}
