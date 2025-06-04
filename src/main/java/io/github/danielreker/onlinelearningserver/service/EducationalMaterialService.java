package io.github.danielreker.onlinelearningserver.service;

import io.github.danielreker.onlinelearningserver.exception.AppException;
import io.github.danielreker.onlinelearningserver.exception.ResourceNotFoundException;
import io.github.danielreker.onlinelearningserver.mapper.*;
import io.github.danielreker.onlinelearningserver.model.*;
import io.github.danielreker.onlinelearningserver.model.dto.*;
import io.github.danielreker.onlinelearningserver.model.enums.Role;
import io.github.danielreker.onlinelearningserver.repository.EducationalMaterialRepository;
import io.github.danielreker.onlinelearningserver.repository.TestRepository;
import io.github.danielreker.onlinelearningserver.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationalMaterialService {

    private final EducationalMaterialRepository materialRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final EducationalMaterialMapper materialMapper;
    private final TextBlockMapper textBlockMapper;
    private final TestMapper testMapper;

    private static final int WORDS_PER_MINUTE = 200;

    @Transactional
    public EducationalMaterialResponseDto createMaterial(EducationalMaterialRequestDto requestDto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        EducationalMaterial material = materialMapper.toEducationalMaterial(requestDto, author);

        processAndLinkChildren(material, requestDto);

        material.setReadingTimeMinutes(calculateReadingTimeMinutes(material));
        EducationalMaterial savedMaterial = materialRepository.save(material);
        return materialMapper.toEducationalMaterialResponseDto(savedMaterial);
    }

    @Transactional(readOnly = true)
    public Page<EducationalMaterialListItemDto> findAllMaterials(Pageable pageable, String topic,
                                                                 Integer minReadingTimeMinutes, Integer maxReadingTimeMinutes,
                                                                 Boolean hasTest, String authorUsername) {
        Specification<EducationalMaterial> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(topic)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("topic")), "%" + topic.toLowerCase() + "%"));
            }
            if (minReadingTimeMinutes != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("readingTimeMinutes"), minReadingTimeMinutes));
            }
            if (maxReadingTimeMinutes != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("readingTimeMinutes"), maxReadingTimeMinutes));
            }
            if (hasTest != null) {
                if (hasTest) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("test")));
                } else {
                    predicates.add(criteriaBuilder.isNull(root.get("test")));
                }
            }
            if (StringUtils.hasText(authorUsername)) {
                predicates.add(criteriaBuilder.equal(root.get("author").get("username"), authorUsername));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<EducationalMaterial> materialsPage = materialRepository.findAll(spec, pageable);
        return materialsPage.map(materialMapper::toEducationalMaterialListItemDto);
    }

    @Transactional(readOnly = true)
    public EducationalMaterialResponseDto findMaterialById(Long id) {
        EducationalMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EducationalMaterial", "id", id));
        return materialMapper.toEducationalMaterialResponseDto(material);
    }

    @Transactional
    public EducationalMaterialResponseDto updateMaterial(Long id, EducationalMaterialRequestDto requestDto, String currentUsername) {
        EducationalMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EducationalMaterial", "id", id));
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        if (!material.getAuthor().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not authorized to update this material.");
        }

        materialMapper.updateEducationalMaterialFromDto(requestDto, material);

        material.getTextBlocks().clear();
        if (requestDto.getTextBlocks() != null) {
            for (int i = 0; i < requestDto.getTextBlocks().size(); i++) {
                TextBlockDto tbDto = requestDto.getTextBlocks().get(i);
                TextBlock newTb = textBlockMapper.toTextBlock(tbDto);
                newTb.setBlockOrder(i);
                material.addTextBlock(newTb);
            }
        }

        Test existingTest = material.getTest();
        if (existingTest != null) {
            material.setTest(null);
            testRepository.delete(existingTest);
            testRepository.flush();
        }

        if (requestDto.getTest() != null) {
            Test newTest = testMapper.toTest(requestDto.getTest());

            if (newTest.getQuestions() != null) {
                for (int i = 0; i < newTest.getQuestions().size(); i++) {
                    Question newQ = newTest.getQuestions().get(i);
                    newQ.setQuestionOrder(i);
                    newQ.setTest(newTest);

                    if (newQ.getAnswerOptions() != null) {
                        for (int j = 0; j < newQ.getAnswerOptions().size(); j++) {
                            AnswerOption newAo = newQ.getAnswerOptions().get(j);
                            newAo.setOptionOrder(j);
                            newAo.setQuestion(newQ);
                        }
                    }
                }
            }
            material.setTest(newTest);
        }

        material.setReadingTimeMinutes(calculateReadingTimeMinutes(material));

        EducationalMaterial updatedMaterial = materialRepository.save(material);
        return materialMapper.toEducationalMaterialResponseDto(updatedMaterial);
    }


    @Transactional
    public void deleteMaterial(Long id, String currentUsername) {
        EducationalMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EducationalMaterial", "id", id));
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        if (!material.getAuthor().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not authorized to delete this material.");
        }
        materialRepository.delete(material);
    }

    @Transactional(readOnly = true)
    public TestResultDto submitTest(Long materialId, TestSubmissionRequestDto submissionDto) {
        EducationalMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("EducationalMaterial", "id", materialId));

        if (material.getTest() == null || material.getTest().getQuestions().isEmpty()) {
            throw new AppException("This material does not have a test or it has no questions.", HttpStatus.BAD_REQUEST);
        }

        Test test = material.getTest();
        Map<Long, Question> questionMap = test.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        int correctAnswersCount = 0;
        for (UserAnswerDto userAnswer : submissionDto.getAnswers()) {
            Question question = questionMap.get(userAnswer.getQuestionId());
            if (question == null) {
                throw new ResourceNotFoundException("Question", "id", userAnswer.getQuestionId());
            }
            AnswerOption correctAnswer = question.getAnswerOptions().stream()
                    .filter(AnswerOption::isCorrect)
                    .findFirst()
                    .orElse(null);

            if (correctAnswer != null && correctAnswer.getId().equals(userAnswer.getSelectedAnswerOptionId())) {
                correctAnswersCount++;
            }
        }

        double scorePercentage = 0;
        if (!test.getQuestions().isEmpty()) {
            scorePercentage = ((double) correctAnswersCount / test.getQuestions().size()) * 100;
        }

        return TestResultDto.builder()
                .materialId(materialId)
                .scorePercentage(scorePercentage)
                .totalQuestions(test.getQuestions().size())
                .correctAnswersCount(correctAnswersCount)
                .build();
    }

    private void processAndLinkChildren(EducationalMaterial material, EducationalMaterialRequestDto requestDto) {
        if (requestDto.getTextBlocks() != null) {
            for (int i = 0; i < requestDto.getTextBlocks().size(); i++) {
                TextBlockDto tbDto = requestDto.getTextBlocks().get(i);
                TextBlock newTb = textBlockMapper.toTextBlock(tbDto);
                newTb.setBlockOrder(i);
                material.addTextBlock(newTb);
            }
        }

        if (requestDto.getTest() != null) {
            Test newTest = testMapper.toTest(requestDto.getTest());
            material.setTest(newTest);

            if (newTest.getQuestions() != null) {
                for (int i = 0; i < newTest.getQuestions().size(); i++) {
                    Question newQ = newTest.getQuestions().get(i);
                    newQ.setQuestionOrder(i);
                    newQ.setTest(newTest);

                    if (newQ.getAnswerOptions() != null) {
                        for (int j = 0; j < newQ.getAnswerOptions().size(); j++) {
                            AnswerOption newAo = newQ.getAnswerOptions().get(j);
                            newAo.setOptionOrder(j);
                            newAo.setQuestion(newQ);
                        }
                    }
                }
            }
        }
    }

    private int calculateReadingTimeMinutes(EducationalMaterial material) {
        if (material.getTextBlocks() == null || material.getTextBlocks().isEmpty()) {
            return 0;
        }
        int totalWords = 0;
        for (TextBlock block : material.getTextBlocks()) {
            if (StringUtils.hasText(block.getContent())) {
                totalWords += block.getContent().trim().split("\\s+").length;
            }
        }
        return (int) Math.ceil((double) totalWords / WORDS_PER_MINUTE);
    }
}