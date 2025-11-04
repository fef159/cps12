package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mapper.PetTypeMapper;
import com.tecsup.petclinic.repositories.PetTypeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jgomezm
 *
 */
@Service
@Slf4j
public class PetTypeServiceImpl implements PetTypeService {

	PetTypeRepository petTypeRepository;
	PetTypeMapper petTypeMapper;

	public PetTypeServiceImpl (PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
		this.petTypeRepository = petTypeRepository;
		this.petTypeMapper = petTypeMapper;
	}


	/**
	 * 
	 * @param petTypeDTO
	 * @return
	 */
	@Override
	public PetTypeDTO create(PetTypeDTO petTypeDTO) {

		PetType newPetType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));

		return petTypeMapper.mapToDto(newPetType);
	}

	/**
	 * 
	 * @param petTypeDTO
	 * @return
	 */
	@Override
	public PetTypeDTO update(PetTypeDTO petTypeDTO) {

		PetType newPetType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));

		return petTypeMapper.mapToDto(newPetType);

	}


	/**
	 * 
	 * @param id
	 * @throws PetTypeNotFoundException
	 */
	@Override
	public void delete(Integer id) throws PetTypeNotFoundException{

		PetTypeDTO petType = findById(id);

		petTypeRepository.delete(this.petTypeMapper.mapToEntity(petType));

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {

		Optional<PetType> petType = petTypeRepository.findById(id);

		if ( !petType.isPresent())
			throw new PetTypeNotFoundException("Record not found...!");

		return this.petTypeMapper.mapToDto(petType.get());
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public List<PetTypeDTO> findByName(String name) {

		List<PetType> petTypes = petTypeRepository.findByName(name);

		petTypes.forEach(petType -> log.info("" + petType));

		return petTypes
				.stream()
				.map(this.petTypeMapper::mapToDto)
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @return
	 */
	@Override
	public List<PetType> findAll() {
		//
		return petTypeRepository.findAll();

	}
}

