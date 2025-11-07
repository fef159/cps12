package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mapper.PetTypeMapper;
import com.tecsup.petclinic.services.PetTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author jgomezm
 *
 */
@RestController
@Slf4j
public class TypesController {

	private final PetTypeService petTypeService;
	private final PetTypeMapper mapper;

	public TypesController(PetTypeService petTypeService, PetTypeMapper mapper) {
		this.petTypeService = petTypeService;
		this.mapper = mapper;
	}

	/**
	 * Get all pet types
	 *
	 * @return
	 */
	@GetMapping(value = "/types")
	public ResponseEntity<List<PetTypeDTO>> findAllTypes() {
		List<PetType> petTypes = petTypeService.findAll();
		log.info("petTypes: {}", petTypes);
		petTypes.forEach(item -> log.info("PetType >>  {} ", item));

		List<PetTypeDTO> petTypesTO = this.mapper.mapToDtoList(petTypes);
		log.info("petTypesTO: {}", petTypesTO);

		return ResponseEntity.ok(petTypesTO);
	}

	/**
	 * Create pet type
	 *
	 * @param petTypeDTO
	 * @return
	 */
	@PostMapping(value = "/types")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<PetTypeDTO> create(@RequestBody PetTypeDTO petTypeDTO) {
		PetTypeDTO newPetTypeDTO = petTypeService.create(petTypeDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newPetTypeDTO);
	}

	/**
	 * Find pet type by id
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/types/{id}")
	ResponseEntity<PetTypeDTO> findById(@PathVariable Integer id) {
		try {
			PetTypeDTO petTypeDto = petTypeService.findById(id);
			return ResponseEntity.ok(petTypeDto);
		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Update pet type by id
	 *
	 * @param petTypeDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/types/{id}")
	ResponseEntity<PetTypeDTO> update(@RequestBody PetTypeDTO petTypeDTO, @PathVariable Integer id) {
		try {
			PetTypeDTO existingPetType = petTypeService.findById(id);

			existingPetType.setName(petTypeDTO.getName());
			existingPetType.setDescription(petTypeDTO.getDescription());
			existingPetType.setCareLevel(petTypeDTO.getCareLevel());

			PetTypeDTO updatedPetType = petTypeService.update(existingPetType);
			return ResponseEntity.ok(updatedPetType);

		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Delete pet type by id
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/types/{id}")
	ResponseEntity<String> delete(@PathVariable Integer id) {
		try {
			petTypeService.delete(id);
			return ResponseEntity.ok("Deleted ID: " + id);
		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
