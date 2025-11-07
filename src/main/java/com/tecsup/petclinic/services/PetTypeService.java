package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;

/**
 * @author jgomezm
 */
public interface PetTypeService {

	/**
	 * Crea un nuevo tipo de mascota.
	 *
	 * @param petTypeDTO DTO con name, description y careLevel.
	 * @return PetTypeDTO creado.
	 */
	PetTypeDTO create(PetTypeDTO petTypeDTO);

	/**
	 * Actualiza un tipo de mascota existente.
	 *
	 * @param petTypeDTO DTO con los datos actualizados.
	 * @return PetTypeDTO actualizado.
	 */
	PetTypeDTO update(PetTypeDTO petTypeDTO);

	/**
	 * Elimina un tipo de mascota por su ID.
	 *
	 * @param id ID del tipo de mascota.
	 * @throws PetTypeNotFoundException si el ID no existe.
	 */
	void delete(Integer id) throws PetTypeNotFoundException;

	/**
	 * Busca un tipo de mascota por ID.
	 *
	 * @param id ID del tipo de mascota.
	 * @return PetTypeDTO encontrado.
	 * @throws PetTypeNotFoundException si no existe.
	 */
	PetTypeDTO findById(Integer id) throws PetTypeNotFoundException;

	/**
	 * Busca tipos de mascota por nombre.
	 *
	 * @param name Nombre del tipo de mascota.
	 * @return Lista de PetTypeDTO.
	 */
	List<PetTypeDTO> findByName(String name);

	/**
	 * Devuelve todos los tipos de mascota existentes.
	 *
	 * @return Lista de entidades PetType.
	 */
	List<PetType> findAll();
}
