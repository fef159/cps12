package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 * Controller para la entidad PetType
 *
 * @author jgome
 */
@RestController
@Slf4j
public class TypesController {

    @Autowired
    private PetTypeService petTypeService;

    @Autowired
    private PetTypeMapper mapper;

    /**
     * Constructor explícito (opcional con @Autowired implícito)
     */
    public TypesController(PetTypeService petTypeService, PetTypeMapper mapper) {
        this.petTypeService = petTypeService;
        this.mapper = mapper;
    }

    /**
     * GET /types - Lista todos los tipos de mascota
     */
    @GetMapping(value = "/types")
    public ResponseEntity<List<PetTypeDTO>> findAllTypes() {

        List<PetType> petTypes = petTypeService.findAll();
        log.info("petTypes: {}", petTypes);

        List<PetTypeDTO> petTypesTO = this.mapper.mapToDtoList(petTypes);
        log.info("petTypesDTO: {}", petTypesTO);

        return ResponseEntity.ok(petTypesTO);
    }

    /**
     * POST /types - Crea un nuevo tipo de mascota
     */
    @PostMapping(value = "/types")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PetTypeDTO> create(@RequestBody PetTypeDTO petTypeDTO) {

        // ✅ Usar directamente el servicio con DTO, según tu interfaz actual
        PetTypeDTO newPetTypeDTO = petTypeService.create(petTypeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPetTypeDTO);
    }

    /**
     * GET /types/{id} - Buscar tipo por ID
     */
    @GetMapping(value = "/types/{id}")
    public ResponseEntity<PetTypeDTO> findById(@PathVariable Integer id) {

        try {
            PetTypeDTO petTypeDto = petTypeService.findById(id);
            return ResponseEntity.ok(petTypeDto);
        } catch (PetTypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /types/{id} - Actualiza un tipo de mascota existente
     */
    @PutMapping(value = "/types/{id}")
    public ResponseEntity<PetTypeDTO> update(@RequestBody PetTypeDTO petTypeDTO, @PathVariable Integer id) {

        try {
            PetTypeDTO updatePetTypeDto = petTypeService.findById(id);

            updatePetTypeDto.setName(petTypeDTO.getName());
            updatePetTypeDto.setDescription(petTypeDTO.getDescription());
            updatePetTypeDto.setCareLevel(petTypeDTO.getCareLevel());

            PetTypeDTO updatedPetType = petTypeService.update(updatePetTypeDto);

            return ResponseEntity.ok(updatedPetType);

        } catch (PetTypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /types/{id} - Elimina un tipo de mascota por ID
     */
    @DeleteMapping(value = "/types/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            petTypeService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (PetTypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
