package com.samhitha.riskmonitor.rules;

import com.samhitha.riskmonitor.transaction.Customer;
import com.samhitha.riskmonitor.transaction.Transaction;

import java.time.Instant;
import java.util.Optional;

public interface RuleContext {
    Customer getCustomer();
    long countTransactionsInWindow(Long accountId, Instant start, Instant end);
    Double getTrailingAverageAmount(Long accountId, Instant start, Instant end);
    Optional<Transaction> getPreviousTransaction(Long accountId, Instant beforeTime);
    long countPriorAccountDeviceUsage(Long accountId, Long deviceId, Instant beforeTime);
}
