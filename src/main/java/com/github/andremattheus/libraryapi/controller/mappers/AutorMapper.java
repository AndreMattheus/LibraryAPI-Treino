package com.github.andremattheus.libraryapi.controller.mappers;

import com.github.andremattheus.libraryapi.controller.dto.AutorDTO;
import com.github.andremattheus.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);
}
