package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.PetType;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface PetTypeRepository 
	extends JpaRepository<PetType, Integer> {

	// Fetch pet types by name
	List<PetType> findByName(String name);

	// Fetch all pet types
	@Override
	List<PetType> findAll();

}

