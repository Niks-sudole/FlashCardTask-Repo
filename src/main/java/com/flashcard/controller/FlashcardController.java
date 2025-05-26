package com.flashcard.controller;
import com.flashcard.model.Flashcard;
import com.flashcard.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * FlashcardController is a REST controller that handles HTTP requests
 * related to flashcards, such as adding and retrieving flashcards.
 */
@RestController
public class FlashcardController {

    @Autowired
    private FlashcardService service;

    /**
     * POST endpoint to add a flashcard.
     * Expects a JSON request body with keys: studentId, question, and answer.
     */
    @PostMapping("/flashcard")
    public Map<String, Object> addFlashcard(@RequestBody Map<String, String> request) {
        return service.addFlashcard(
                request.get("studentId"),
                request.get("question"),
                request.get("answer")
        );
    }

    /**
     * GET endpoint to retrieve a list of flashcards from different subjects for a specific student.
     * The result is shuffled and limited by the given number.
     * Example Request: /get-subject?student_id=stu001&limit=5
     */
    @GetMapping("/get-subject")
    public List<Flashcard> getFlashcards(@RequestParam String student_id,
                                         @RequestParam int limit) {
        return service.getMixedFlashcards(student_id, limit);
    }
}
