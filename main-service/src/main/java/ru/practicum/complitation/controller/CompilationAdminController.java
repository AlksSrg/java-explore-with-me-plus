package ru.practicum.complitation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.complitation.dto.CompilationDto;
import ru.practicum.complitation.dto.NewCompilationDto;
import ru.practicum.complitation.dto.UpdateCompilationRequest;
import ru.practicum.complitation.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Creating new compilation with title: {}", newCompilationDto.getTitle());
        return compilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Deleting compilation with id: {}", compId);
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @Valid @RequestBody UpdateCompilationRequest updateRequest) {
        log.info("Updating compilation with id: {}", compId);
        return compilationService.updateCompilation(compId, updateRequest);
    }
}