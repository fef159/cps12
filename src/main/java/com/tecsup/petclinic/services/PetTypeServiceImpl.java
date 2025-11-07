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

	private final PetTypeRepository petTypeRepository;
	private final PetTypeMapper petTypeMapper;

	public PetTypeServiceImpl(PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
		this.petTypeRepository = petTypeRepository;
		this.petTypeMapper = petTypeMapper;
	}

	@Override
	public PetTypeDTO create(PetTypeDTO petTypeDTO) {
		PetType newPetType = petTypeMapper.mapToEntity(petTypeDTO);
		PetType savedPetType = petTypeRepository.save(newPetType);
		return petTypeMapper.mapToDto(savedPetType);
	}

	@Override
	public PetTypeDTO update(PetTypeDTO petTypeDTO) {
		PetType updatedEntity = petTypeMapper.mapToEntity(petTypeDTO);
		PetType savedPetType = petTypeRepository.save(updatedEntity);
		return petTypeMapper.mapToDto(savedPetType);
	}

	@Override
	public void delete(Integer id) throws PetTypeNotFoundException {
		PetTypeDTO petType = findById(id);
		petTypeRepository.delete(petTypeMapper.mapToEntity(petType));
	}

	@Override
	public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {
		Optional<PetType> petType = petTypeRepository.findById(id);

		if (petType.isEmpty()) {
			throw new PetTypeNotFoundException("Record not found...!");
		}

		return petTypeMapper.mapToDto(petType.get());
	}

	@Override
	public List<PetTypeDTO> findByName(String name) {
		List<PetType> petTypes = petTypeRepository.findByName(name);
		petTypes.forEach(petType -> log.info("{}", petType));
		return petTypes.stream()
				.map(petTypeMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<PetType> findAll() {
		return petTypeRepository.findAll();
	}
}
