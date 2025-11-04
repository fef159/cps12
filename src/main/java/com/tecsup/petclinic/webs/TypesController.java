package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	String name = null;

	//@Autowired
	private PetTypeService petTypeService;

	//@Autowired
	private PetTypeMapper mapper;

	/**
	 *  Change
	 * @param petTypeService
	 * @param mapper
	 */
	public TypesController(PetTypeService petTypeService, PetTypeMapper mapper){
		this.petTypeService = petTypeService;
		this.mapper = mapper ;
	}

	/**
	 * Get all pet types
	 *
	 * @return
	 */
	@GetMapping(value = "/types")
	public ResponseEntity<List<PetTypeDTO>> findAllTypes() {

		List<PetType> petTypes = petTypeService.findAll();
		log.info("petTypes: " + petTypes);
		petTypes.forEach(item -> log.info("PetType >>  {} ", item));

		List<PetTypeDTO> petTypesTO = this.mapper.mapToDtoList(petTypes);
		log.info("petTypesTO: " + petTypesTO);
		petTypesTO.forEach(item -> log.info("PetTypeTO >>  {} ", item));

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

		//PetType newPetType = this.mapper.mapToEntity(petTypeDTO);
		PetTypeDTO newPetTypeDTO = petTypeService.create(petTypeDTO);

		return  ResponseEntity.status(HttpStatus.CREATED).body(newPetTypeDTO);

	}


	/**
	 * Find pet type by id
	 *
	 * @param id
	 * @return
	 * @throws PetTypeNotFoundException
	 */
	@GetMapping(value = "/types/{id}")
	ResponseEntity<PetTypeDTO> findById(@PathVariable Integer id) {

		PetTypeDTO petTypeDto = null;

		try {
            petTypeDto = petTypeService.findById(id);

		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(petTypeDto);
	}

	/**
	 * Update and create pet type
	 *
	 * @param petTypeDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/types/{id}")
	ResponseEntity<PetTypeDTO>  update(@RequestBody PetTypeDTO petTypeDTO, @PathVariable Integer id) {

		PetTypeDTO updatePetTypeDto = null;

		try {

            updatePetTypeDto = petTypeService.findById(id);

            updatePetTypeDto.setName(petTypeDTO.getName());

			petTypeService.update(updatePetTypeDto);

		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updatePetTypeDto);
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
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}

