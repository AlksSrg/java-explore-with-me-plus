package ru.practicum.complitation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.complitation.dto.CompilationDto;
import ru.practicum.complitation.dto.NewCompilationDto;
import ru.practicum.complitation.dto.UpdateCompilationRequest;
import ru.practicum.complitation.mapper.CompilationMapper;
import ru.practicum.complitation.model.Compilation;
import ru.practicum.complitation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.NotFoundResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        log.info("Creating new compilation with title: {}", newCompilationDto.getTitle());

        // Проверка на уникальность названия
        if (compilationRepository.existsByTitle(newCompilationDto.getTitle())) {
            throw new ConflictResource("Compilation with title '" + newCompilationDto.getTitle() + "' already exists");
        }

        Compilation compilation = compilationMapper.toEntity(newCompilationDto);

        // Устанавливаем значение pinned (по умолчанию false)
        if (newCompilationDto.getPinned() == null) {
            compilation.setPinned(false);
        } else {
            compilation.setPinned(newCompilationDto.getPinned());
        }

        // Добавляем события если они указаны
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
            compilation.setEvents(events);
        } else {
            compilation.setEvents(new ArrayList<>());
        }

        try {
            Compilation savedCompilation = compilationRepository.save(compilation);
            log.info("Compilation created successfully with id: {}", savedCompilation.getId());
            return compilationMapper.toDto(savedCompilation);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictResource("Compilation creation failed due to data integrity violation");
        }
    }

    @Override
    public void deleteCompilation(Long compId) {
        log.info("Deleting compilation with id: {}", compId);

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundResource("Compilation with id=" + compId + " was not found"));

        compilationRepository.delete(compilation);
        log.info("Compilation with id: {} deleted successfully", compId);
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateRequest) {
        log.info("Updating compilation with id: {}", compId);

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundResource("Compilation with id=" + compId + " was not found"));

        // Обновление title если указано
        if (updateRequest.getTitle() != null) {
            if (!compilation.getTitle().equals(updateRequest.getTitle()) &&
                    compilationRepository.existsByTitle(updateRequest.getTitle())) {
                throw new ConflictResource("Compilation with title '" + updateRequest.getTitle() + "' already exists");
            }
            compilation.setTitle(updateRequest.getTitle());
        }

        // Обновление pinned если указано
        if (updateRequest.getPinned() != null) {
            compilation.setPinned(updateRequest.getPinned());
        }

        // Обновление событий если указано
        if (updateRequest.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(updateRequest.getEvents());
            compilation.setEvents(events);
        }

        try {
            Compilation updatedCompilation = compilationRepository.save(compilation);
            log.info("Compilation with id: {} updated successfully", compId);
            return compilationMapper.toDto(updatedCompilation);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictResource("Compilation update failed due to data integrity violation");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable) {
        log.info("Getting compilations with pinned={}, pageable={}", pinned, pageable);

        List<Compilation> compilations;
        if (pinned != null) {
            compilations = compilationRepository.findAllByPinned(pinned, pageable).getContent();
        } else {
            compilations = compilationRepository.findAll(pageable).getContent();
        }

        return compilations.stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long compId) {
        log.info("Getting compilation by id: {}", compId);

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundResource("Compilation with id=" + compId + " was not found"));

        return compilationMapper.toDto(compilation);
    }
}