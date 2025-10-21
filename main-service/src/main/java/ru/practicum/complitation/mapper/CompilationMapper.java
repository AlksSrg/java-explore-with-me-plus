package ru.practicum.complitation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.complitation.dto.CompilationDto;
import ru.practicum.complitation.dto.NewCompilationDto;
import ru.practicum.complitation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {
//
//    // TODO: Временно устранить проблему

    /// /    @Mapping(target = "events", source = "events")
    /// /    CompilationDto toDto(Compilation compilation);
//    default CompilationDto toDto(Compilation compilation) {
//        return null;
//    }
//
    default CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::mapToEventShortDto)
                        .toList())
                .build();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    Compilation toEntity(NewCompilationDto newCompilationDto);

}