package com.andrefilho99.balaio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrefilho99.balaio.model.Contact;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	//CRUD
	
	public void create(User from, User to) {
		
		Contact contact = new Contact();
		
		contact.setFrom(from);
		contact.setTo(to);
		
		contactRepository.save(contact);
	}
	
	public Contact get(Integer id) {
		return contactRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		contactRepository.deleteById(id);
	}
	
	//Other Methods
}
