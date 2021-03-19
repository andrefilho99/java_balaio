package com.andrefilho99.balaio.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class MessageUtils {
	
	public String encode(String message) {
		
		Base64 base64 = new Base64();
		return new String(base64.encode(message.getBytes()));
	}
	
	public String decode(String message) {

		Base64 base64 = new Base64();
		return new String(base64.decode(message.getBytes()));
	}
}
