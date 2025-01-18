package com.saytech.expentrack.transactionservice.utility;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericConverter {

    @Autowired
    private ModelMapper modelMapper;

    // Convert a DTO to an Entity
    public <D, E> E convertDtoToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    // Convert an Entity to a DTO
    public <E, D> D convertEntityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    // Convert a List of Entities to a List of DTOs
    public <E, D> List<D> convertEntitiesToDtos(List<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> convertEntityToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    // Convert a List of DTOs to a List of Entities
    public <D, E> List<E> convertDtosToEntities(List<D> dtos, Class<E> entityClass) {
        return dtos.stream()
                .map(dto -> convertDtoToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }
}
