// 代码生成时间: 2025-10-01 23:01:49
package com.example.wealthmanagement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Wealth management service class that provides functionality for managing wealth.
 */
@ApplicationScoped
public class WealthManagementService {

    /**
     * Calculates the total wealth based on assets and liabilities.
     *
     * @param assets The total assets of the user.
     * @param liabilities The total liabilities of the user.
     * @return The net worth of the user.
     */
    @Transactional
    public BigDecimal calculateNetWorth(BigDecimal assets, BigDecimal liabilities) {
        if (assets == null || liabilities == null) {
            throw new IllegalArgumentException("Assets and liabilities cannot be null");
        }

        return assets.subtract(liabilities);
    }

    /**
     * Simulates an investment return calculation.
     *
     * @param initialInvestment The initial amount invested.
     * @param rateOfReturn The rate of return as a percentage.
     * @return The final amount after the investment.
     */
    public BigDecimal calculateInvestmentReturn(BigDecimal initialInvestment, double rateOfReturn) {
        if (initialInvestment == null || rateOfReturn < 0) {
            throw new IllegalArgumentException("Initial investment cannot be null and rate of return must be non-negative");
        }

        return initialInvestment.multiply(BigDecimal.valueOf(1 + rateOfReturn / 100));
    }
}
