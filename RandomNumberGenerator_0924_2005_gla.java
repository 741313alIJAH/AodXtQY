// 代码生成时间: 2025-09-24 20:05:24
package com.example.random;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.annotation.security.RolesAllowed;
import java.util.Random;
import java.security.SecureRandom;

/**
 * 随机数生成器程序
 *
 * @author <Your Name>
 * @version 1.0
 */
@QuarkusMain
public class RandomNumberGenerator implements QuarkusApplication {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int MAX_NUMBER = 100; // 最大随机数范围

    private static final String ERROR_MESSAGE = "Error generating random number";

    private static int generateRandomNumber() {
        try {
            // 生成0到MAX_NUMBER之间的随机数
            return SECURE_RANDOM.nextInt(MAX_NUMBER + 1);
        } catch (Exception e) {
            // 错误处理
            System.err.println(ERROR_MESSAGE);
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public int run(String... args) throws Exception {
        try {
            // 生成一个随机数并打印
            int randomNumber = generateRandomNumber();
            System.out.println("Generated random number: " + randomNumber);
            return 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
    }

    public static void main(String[] args) {
        // 使用QuarkusApplicationRunner启动程序
        new RandomNumberGenerator().run(args);
    }
}