package com.tecsup.petclinic.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;

/**
 * Mapper para convertir entre PetType y PetTypeDTO.
 * Usa MapStruct para generar el código automáticamente.
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PetTypeMapper {

	PetTypeMapper INSTANCE = Mappers.getMapper(PetTypeMapper.class);

	PetType mapToEntity(PetTypeDTO petTypeDTO);

	PetTypeDTO mapToDto(PetType petType);

	List<PetTypeDTO> mapToDtoList(List<PetType> petTypeList);

	List<PetType> mapToEntityList(List<PetTypeDTO> petTypeDTOList);
}
