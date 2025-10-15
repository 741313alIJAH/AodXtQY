// 代码生成时间: 2025-10-15 22:17:19
package com.example;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/montecarlo")
# 优化算法效率
public class MonteCarloSimulator {

    @Inject
    Random random;

    // Entry point for the simulation
    @GET
    @Path("/estimatePi")
    @Produces(MediaType.TEXT_PLAIN)
    public String estimatePi(int numTrials) {
        if (numTrials <= 0) {
            throw new IllegalArgumentException("Number of trials must be positive");
        }

        int insideCircle = 0;
        for (int i = 0; i < numTrials; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                insideCircle++;
            }
        }

        return Double.toString(Math.PI * (4 * insideCircle) / numTrials);
    }
}
