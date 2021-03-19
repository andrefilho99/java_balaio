package com.andrefilho99.balaio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrefilho99.balaio.model.Balaio;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.repository.BalaioRepository;
import com.andrefilho99.balaio.utils.MessageUtils;

@Service
public class BalaioService {
	
	@Autowired
	private BalaioRepository balaioRepository;
	
	@Autowired
	private MessageUtils messageUtils;
	
	//CRUD
	
	public void create(String message, User from, User to, double latitude, double longitude) {
		
		Balaio balaio = new Balaio();
		
		balaio.setMessage(messageUtils.encode(message));
		balaio.setFrom(from);
		balaio.setTo(to);
		balaio.setLatitude(latitude);
		balaio.setLongitude(longitude);
		
		balaioRepository.save(balaio);
	}
	
	public Balaio get(Integer id) {
		return balaioRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		balaioRepository.deleteById(id);
	}
	
	//Other Methods
}
