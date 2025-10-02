// 代码生成时间: 2025-10-03 03:25:19
package com.example.notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.Startup;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/notification")
@ApplicationScoped
public class MessageNotificationService {

    // Logger instance
    private static final Logger LOGGER = Logger.getLogger(MessageNotificationService.class.getName());

    // Executor service for handling notifications in a separate thread
    private ExecutorService executorService;

    @Inject
    @Startup
    public void onStart(StartupEvent event) {
        // Initialize the executor service with a fixed thread pool
        executorService = Executors.newFixedThreadPool(5);
    }

    @GET
    @Path("/send")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage(String message) {
        if (message == null || message.isEmpty()) {
            return "Error: Message cannot be null or empty";
        }

        // Schedule a task to send the message
        executorService.submit(() -> {
            try {
                // Simulate message sending delay
                TimeUnit.SECONDS.sleep(2);
                LOGGER.info("Message sent: " + message);
            } catch (InterruptedException e) {
                LOGGER.severe("Error sending message: " + e.getMessage());
            }
        });

        return "Message queued for sending: " + message;
    }

    @GET
    @Path("/shutdown")
    public void shutdown() {
        // Shutdown the executor service gracefully
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            LOGGER.severe("Executor service shutdown interrupted: " + e.getMessage());
        }
    }
}
