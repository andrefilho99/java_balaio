package com.andrefilho99.balaio.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	
	public static final String ACCOUNT_SID = "AC270297f46755763ea730043e5428f7aa";
	public static final String AUTH_TOKEN = "ffe988623f1a512725b0b869168229b2";
	
	public void notifyNumber(String number) {
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber(number),new PhoneNumber("+19418775044"), "Um amigo tentou te enviar um Balaio! Utilize nosso app e tente encontrar mensagens por onde você anda").create();
		System.out.println(message.getSid());
	}
	
	public void notifyNewUser(String number, String token) {
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber(number),new PhoneNumber("+19418775044"), "Sua conta no Balaio foi criada com sucesso! Utilize o token "+token+" para validar e acessar sua conta.").create();
		System.out.println(message.getSid());
	}
}
