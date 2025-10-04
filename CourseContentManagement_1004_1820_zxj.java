// 代码生成时间: 2025-10-04 18:20:47
package com.yourcompany.quarkusapp;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@Path("/courses")
public class CourseContentManagement {

    @Inject
    CourseRepository courseRepository;

    /**
     * Retrieve all courses
     *
     * @return A list of courses
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<List<Course>> getAllCourses() {
        return courseRepository.listAll();
    }

    /**
     * Retrieve a course by its ID
     *
     * @param id The ID of the course
     * @return A course or a 404 if not found
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> getCourseById(@PathParam("id") Long id) {
        return courseRepository.findById(id)
                .map(course -> Response.ok(course).build())
                .orElse(Uni.createFrom().item(() -> Response.status(Response.Status.NOT_FOUND).entity("Course not found").build()));
    }

    /**
     * Add a new course
     *
     * @param course The course to add
     * @return The added course with its ID
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> addCourse(Course course) {
        return courseRepository.add(course)
                .onItem().transformToUni(c -> Uni.createFrom().item(() -> Response.ok(c).build()))
                .onFailure().recoverWithUni(throwable -> Uni.createFrom().item(() -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding course").build()));
    }
}

/**
 * CourseRepository - A Panache repository for the Course entity.
 *
 * @author Your Name
 * @version 1.0
 */
public class CourseRepository implements PanacheRepositoryBase<Course, Long> {
    // Add custom queries here
}

/**
 * Course - The entity representing a course.
 *
 * @author Your Name
 * @version 1.0
 */
@Entity
public class Course {
    @Id
    public Long id;
    public String name;
    public String description;

    // standard getters and setters
}
