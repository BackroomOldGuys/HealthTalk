package com.brold.healthTalk.exercise.service;

import com.brold.healthTalk.exercise.service.ExerciseService;
import com.brold.healthTalk.exercise.domain.ExerciseDefinition;
import com.brold.healthTalk.exercise.dto.request.ExerciseRequest;
import com.brold.healthTalk.exercise.dto.response.ExerciseResponse;
import com.brold.healthTalk.exercise.repository.ExerciseDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseDefinitionRepository repo;
    private static final int SEARCH_LIMIT = 10;

    @Autowired
    public ExerciseServiceImpl(ExerciseDefinitionRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseResponse> searchExercises(String query) {
        return repo.findByNameContaining(query, PageRequest.of(0, SEARCH_LIMIT))
                .stream()
                .map(ExerciseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Integer createExercise(ExerciseRequest req) {
        ExerciseDefinition entity = new ExerciseDefinition(req.getName(), req.getTargetArea());
        return repo.save(entity).getId();
    }

    @Override
    public void updateExercise(Integer id, ExerciseRequest req) {
        ExerciseDefinition entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found: " + id));
        entity.setName(req.getName());
        entity.setTargetArea(req.getTargetArea());
        // JPA 변경 감지(dirty checking)로 자동 반영
    }

    @Override
    public void deleteExercise(Integer id) {
        repo.deleteById(id);
    }
}
