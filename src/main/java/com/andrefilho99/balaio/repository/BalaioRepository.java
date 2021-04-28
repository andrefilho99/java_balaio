package com.andrefilho99.balaio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.andrefilho99.balaio.model.Balaio;

@Repository
public interface BalaioRepository extends JpaRepository<Balaio, Integer>{
	
	@Query(value = "SELECT * FROM BALAIO WHERE FOUND = TRUE AND USER_ID_TO = ?1 ORDER BY CREATED DESC",nativeQuery = true)
	public List<Balaio> getBalaiosFound(Integer userId);
}
