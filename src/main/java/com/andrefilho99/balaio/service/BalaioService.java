package com.andrefilho99.balaio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrefilho99.balaio.exception.BalaioException;
import com.andrefilho99.balaio.exception.UserNotFoundException;
import com.andrefilho99.balaio.model.Balaio;
import com.andrefilho99.balaio.model.User;
import com.andrefilho99.balaio.repository.BalaioRepository;
import com.andrefilho99.balaio.utils.GeoMatchUtils;
import com.andrefilho99.balaio.utils.MessageUtils;

@Service
public class BalaioService {
	
	@Autowired
	private BalaioRepository balaioRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageUtils messageUtils;
	
	@Autowired
	private GeoMatchUtils geoMatchUtils;
	
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
	
	public Balaio getByDistance(Integer userId, Double latitude, Double longitude) {
		
		User user = userService.get(userId);
		List<Balaio> balaiosNotFound = user.getBalaiosNotFound();
		Double closestDistance = null;
		Balaio closestBalaio = null;
		
		if(balaiosNotFound.size() > 0) {
			
			for(int i = 0; i < balaiosNotFound.size(); i++) {
				
				if(i != 0) {
					
					Double balaioDistance = geoMatchUtils.distFrom(latitude, longitude, balaiosNotFound.get(i).getLatitude(), balaiosNotFound.get(i).getLongitude());
					
					if(balaioDistance < closestDistance) {
						
						closestDistance = balaioDistance;
						closestBalaio = balaiosNotFound.get(i);
					}
				} else {
					
					closestDistance = geoMatchUtils.distFrom(latitude, longitude, balaiosNotFound.get(i).getLatitude(), balaiosNotFound.get(i).getLongitude());
					closestBalaio = balaiosNotFound.get(i);
				}
			}
		} else {
			throw new BalaioException("There are no balaios near you");
		}
		
		if(closestDistance < 31) {
			
			closestBalaio.setFound(true);
			balaioRepository.save(closestBalaio);
			
			return closestBalaio;
		} else {
			throw new BalaioException("There are no balaios near you");
		}
	}
	
	public List<Balaio> getBalaio(Integer userId) {
		
		List<Balaio> balaios = new ArrayList<Balaio>();
		User user = userService.get(userId);
		
		for(Balaio balaio : user.getBalaiosSent()) {
			
			if(!balaios.contains(balaio)) {
				balaios.add(balaio);
			}
		}
		
		for(Balaio balaio : user.getBalaiosReceived()) {
			
			if(!balaios.contains(balaio)) {
				balaios.add(balaio);
			}
		}
		
		if(balaios.isEmpty()) {
			throw new BalaioException("You have no balaios.");
		}
		
		return balaios;
	}
	
	public List<Balaio> getBalaiosFound(Integer userId) {
		
		List<Balaio> balaios = new ArrayList<Balaio>();
		User user = userService.get(userId);
		
		for(Balaio balaio : user.getBalaiosReceived()) {
			
			if(!balaios.contains(balaio) && balaio.getFound()) {
				balaios.add(balaio);
			}
		}
		
		if(balaios.isEmpty()) {
			throw new BalaioException("You have no balaios.");
		}
		
		return balaios;
	}
	
	public List<Balaio> getBalaiosSent(Integer userId) {
		
		List<Balaio> balaios = new ArrayList<Balaio>();
		User user = userService.get(userId);
		
		for(Balaio balaio : user.getBalaiosSent()) {
			
			if(!balaios.contains(balaio)) {
				balaios.add(balaio);
			}
		}
		
		if(balaios.isEmpty()) {
			throw new BalaioException("You have no balaios.");
		}
		
		return balaios;
	}
}
