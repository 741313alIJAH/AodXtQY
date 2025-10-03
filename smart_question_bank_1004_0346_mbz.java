// 代码生成时间: 2025-10-04 03:46:21
package com.example.questionbank;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main class for the smart question bank system.
 */
@QuarkusMain
public class SmartQuestionBank {

    private static final List<Question> questions = new ArrayList<>();

    static {
        // Initialize the question bank with sample questions
        questions.add(new Question(1, "What is the capital of France?", "Paris"));
        questions.add(new Question(2, "Who wrote 'Hamlet'?", "William Shakespeare"));
        // Add more sample questions as needed
    }

    public static void main(String... args) {
        Quarkus.run(SmartQuestionBank.class);
    }

    @GET
    @Path("/questions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getAllQuestions() {
        return questions.stream().collect(Collectors.toList());
    }

    @GET
    @Path("/questions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestionById(@PathParam("id") int id) throws QuestionNotFoundException {
        Question question = questions.stream()
            .filter(q -> q.getId() == id)
            .findFirst()
            .orElseThrow(() -> new QuestionNotFoundException("Question with ID: " + id + " not found."));
        return question;
    }
}

class Question {
    private int id;
    private String questionText;
    private String correctAnswer;

    public Question(int id, String questionText, String correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
