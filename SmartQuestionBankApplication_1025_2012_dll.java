// 代码生成时间: 2025-10-25 20:12:59
package com.example.smartquestionbank;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * SmartQuestionBankApplication is the main entry point of the application.
 * It initializes the RESTEasy application with the JAX-RS resources.
 */
@QuarkusMain
public class SmartQuestionBankApplication {

    /**
     * The application path for the RESTful services.
     */
    @ApplicationPath("/api")
    public static class QuestionBankApp extends Application {
        @Override
        public Set<Class<?>> getClasses() {
            Set<Class<?>> resources = new HashSet<>();
            // JAX-RS resources (endpoints)
            resources.add(QuestionResource.class);
            // Add more resources if needed
            return resources;
        }
    }

    public static void main(String... args) {
        Quarkus.run(SmartQuestionBankApplication.class, args);
    }
}

// QuestionResource.java
package com.example.smartquestionbank;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/questions")
public class QuestionResource {

    private QuestionService questionService = new QuestionService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllQuestions() {
        return Response.ok(questionService.getAllQuestions()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionById(@PathParam("id\) Long id) {
        Optional<Question> question = questionService.getQuestionById(id);
        return question.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addQuestion(Question question) {
        questionService.addQuestion(question);
        return Response.status(Response.Status.CREATED).entity(question).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@PathParam("id\) Long id, Question question) {
        boolean updated = questionService.updateQuestion(id, question);
        if (updated) {
            return Response.ok(question).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteQuestion(@PathParam("id\) Long id) {
        boolean deleted = questionService.deleteQuestion(id);
        if (deleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

// QuestionService.java
package com.example.smartquestionbank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionService {
    private static final AtomicLong counter = new AtomicLong();
    private List<Question> questions = new ArrayList<>();

    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    public Optional<Question> getQuestionById(Long id) {
        return questions.stream().filter(q -> q.getId().equals(id)).findFirst();
    }

    public void addQuestion(Question question) {
        question.setId(counter.incrementAndGet());
        questions.add(question);
    }

    public boolean updateQuestion(Long id, Question question) {
        Optional<Question> existingQuestion = getQuestionById(id);
        if (existingQuestion.isPresent()) {
            existingQuestion.get().setQuestion(question.getQuestion());
            existingQuestion.get().setCategory(question.getCategory());
            return true;
        }
        return false;
    }

    public boolean deleteQuestion(Long id) {
        return questions.removeIf(q -> q.getId().equals(id));
    }
}

// Question.java
package com.example.smartquestionbank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String question;
    private String category;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}