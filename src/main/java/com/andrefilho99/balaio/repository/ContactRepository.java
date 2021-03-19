package com.andrefilho99.balaio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrefilho99.balaio.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{

}
