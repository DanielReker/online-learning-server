package io.github.danielreker.onlinelearningserver.controller;

import io.github.danielreker.onlinelearningserver.model.dto.EducationalMaterialListItemDto;
import io.github.danielreker.onlinelearningserver.model.dto.EducationalMaterialRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.EducationalMaterialResponseDto;
import io.github.danielreker.onlinelearningserver.model.dto.TestResultDto;
import io.github.danielreker.onlinelearningserver.model.dto.TestSubmissionRequestDto;
import io.github.danielreker.onlinelearningserver.service.EducationalMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/educational-materials")
@CrossOrigin
@RequiredArgsConstructor
public class EducationalMaterialController {

    private final EducationalMaterialService materialService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EducationalMaterialResponseDto> createMaterial(
            @Valid @RequestBody EducationalMaterialRequestDto requestDto,
            Authentication authentication
    ) {
        String currentUsername = authentication.getName();
        EducationalMaterialResponseDto createdMaterial = materialService.createMaterial(requestDto, currentUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaterial);
    }

    @GetMapping
    public ResponseEntity<Page<EducationalMaterialListItemDto>> getAllMaterials(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) Integer minReadingTimeMinutes,
            @RequestParam(required = false) Integer maxReadingTimeMinutes,
            @RequestParam(required = false) Boolean hasTest,
            @RequestParam(required = false) String authorUsername
    ) {
        Page<EducationalMaterialListItemDto> materials = materialService.findAllMaterials(
                pageable, topic, minReadingTimeMinutes, maxReadingTimeMinutes, hasTest, authorUsername
        );
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationalMaterialResponseDto> getMaterialById(@PathVariable Long id) {
        EducationalMaterialResponseDto material = materialService.findMaterialById(id);
        return ResponseEntity.ok(material);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EducationalMaterialResponseDto> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody EducationalMaterialRequestDto requestDto,
            Authentication authentication
    ) {
        String currentUsername = authentication.getName();
        EducationalMaterialResponseDto updatedMaterial = materialService.updateMaterial(id, requestDto, currentUsername);
        return ResponseEntity.ok(updatedMaterial);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMaterial(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String currentUsername = authentication.getName();
        materialService.deleteMaterial(id, currentUsername);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{materialId}/test/submit")
    public ResponseEntity<TestResultDto> submitTest(
            @PathVariable Long materialId,
            @Valid @RequestBody TestSubmissionRequestDto submissionDto
    ) {
        TestResultDto result = materialService.submitTest(materialId, submissionDto);
        return ResponseEntity.ok(result);
    }
}