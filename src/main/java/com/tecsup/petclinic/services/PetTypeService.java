package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;

/**
 *
 * @author jgomezm
 *
 */
public interface PetTypeService {

	/**
	 *
	 * @param petTypeDTO
	 * @return
	 */
	public PetTypeDTO create(PetTypeDTO petTypeDTO);

	/**
	 *
	 * @param petTypeDTO
	 * @return
	 */

	PetTypeDTO update(PetTypeDTO petTypeDTO);

	/**
	 *
	 * @param id
	 * @throws PetTypeNotFoundException
	 */
	void delete(Integer id) throws PetTypeNotFoundException;

	/**
	 *
	 * @param id
	 * @return
	 */
	PetTypeDTO findById(Integer id) throws PetTypeNotFoundException;

	/**
	 *
	 * @param name
	 * @return
	 */
	List<PetTypeDTO> findByName(String name);

	/**
	 *
	 * @return
	 */
	List<PetType> findAll();
}

