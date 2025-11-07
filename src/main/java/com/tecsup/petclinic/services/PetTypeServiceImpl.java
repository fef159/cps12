package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mapper.PetTypeMapper;
import com.tecsup.petclinic.repositories.PetTypeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio para la gestión de tipos de mascotas.
 *
 * Implementa las operaciones CRUD usando DTOs y el mapper.
 *
 * @author jgome
 */
@Service
@Slf4j
public class PetTypeServiceImpl implements PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetTypeMapper petTypeMapper;

    /**
     * Constructor explícito (Spring lo usará automáticamente)
     */
    public PetTypeServiceImpl(PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
        this.petTypeRepository = petTypeRepository;
        this.petTypeMapper = petTypeMapper;
    }

    /**
     * Crear un nuevo tipo de mascota
     */
    @Override
    public PetTypeDTO create(PetTypeDTO petTypeDTO) {

        // ✅ Convertimos el DTO en entidad
        PetType entity = petTypeMapper.mapToEntity(petTypeDTO);

        // ✅ Guardamos en la base de datos
        PetType savedEntity = petTypeRepository.save(entity);

        // ✅ Convertimos la entidad guardada nuevamente a DTO (esto copia todos los campos)
        PetTypeDTO newPetTypeDTO = petTypeMapper.mapToDto(savedEntity);

        log.info("Created PetType: {}", newPetTypeDTO);

        return newPetTypeDTO;
    }

    /**
     * Actualizar un tipo existente
     */
    @Override
    public PetTypeDTO update(PetTypeDTO petTypeDTO) {

        // ✅ Convertimos el DTO en entidad
        PetType entity = petTypeMapper.mapToEntity(petTypeDTO);

        // ✅ Guardamos la entidad actualizada
        PetType updatedEntity = petTypeRepository.save(entity);

        // ✅ Devolvemos el DTO actualizado
        PetTypeDTO updatedPetTypeDTO = petTypeMapper.mapToDto(updatedEntity);

        log.info("Updated PetType: {}", updatedPetTypeDTO);

        return updatedPetTypeDTO;
    }

    /**
     * Eliminar un tipo por ID
     */
    @Override
    public void delete(Integer id) throws PetTypeNotFoundException {

        PetTypeDTO petType = findById(id);

        petTypeRepository.delete(this.petTypeMapper.mapToEntity(petType));

        log.info("Deleted PetType ID: {}", id);
    }

    /**
     * Buscar tipo por ID
     */
    @Override
    public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {

        Optional<PetType> petType = petTypeRepository.findById(id);

        if (!petType.isPresent())
            throw new PetTypeNotFoundException("Record not found...!");

        PetTypeDTO dto = this.petTypeMapper.mapToDto(petType.get());

        log.info("Found PetType: {}", dto);

        return dto;
    }

    /**
     * Buscar tipos por nombre
     */
    @Override
    public List<PetTypeDTO> findByName(String name) {

        List<PetType> petTypes = petTypeRepository.findByName(name);

        petTypes.forEach(petType -> log.info("{}", petType));

        return petTypes
                .stream()
                .map(this.petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Listar todos los tipos
     */
    @Override
    public List<PetType> findAll() {
        List<PetType> list = petTypeRepository.findAll();
        log.info("Found {} PetTypes", list.size());
        return list;
    }
}
