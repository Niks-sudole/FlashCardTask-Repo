package com.flashcard.service;

import com.flashcard.model.Flashcard;
import com.flashcard.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository repository;

    public Map<String, Object> addFlashcard(String studentId, String question, String answer) {
        String subject = inferSubject(question);                          // Direct call to classification method
        Flashcard flashcard = new Flashcard(null, studentId, question, answer, subject);
        repository.save(flashcard);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Flashcard added successfully");
        response.put("subject", subject);
        return response;
    }

    public List<Flashcard> getMixedFlashcards(String studentId, int limit) {
        List<Flashcard> all = repository.findByStudentId(studentId);
        Collections.shuffle(all);

        return all.stream()
                .distinct()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Flashcard::getSubject, LinkedHashMap::new, Collectors.toList()),
                        map -> map.values().stream()
                                .flatMap(list -> list.stream().limit(5))
                                .limit(limit)
                                .collect(Collectors.toList())
                ));
    }


    private String inferSubject(String question) {
        question = question.toLowerCase();
        if (question.contains("force") || question.contains("law") || question.contains("acceleration"))
            return "Physics";
        else if (question.contains("photosynthesis") || question.contains("plants"))
            return "Biology";
        else if (question.contains("war") || question.contains("empire"))
            return "History";
        else if (question.contains("framework") || question.contains("programing") || question.contains("language")|| question.contains("coding"))
            return "Coding";
        else if (question.contains("equation") || question.contains("algebra") || question.contains("math"))
            return "Mathematics";
        return "General";
    }
}
