package com.samhitha.riskmonitor.rules;

import com.samhitha.riskmonitor.transaction.Transaction;

import java.util.Optional;

public interface Rule {
    String getName();
    Optional<RuleHit> evaluate(Transaction transaction, RuleContext context);
}
