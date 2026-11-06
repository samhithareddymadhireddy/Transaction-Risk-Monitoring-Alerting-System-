package com.samhitha.riskmonitor.rules;

import com.samhitha.riskmonitor.alert.Alert;
import com.samhitha.riskmonitor.alert.AlertService;
import com.samhitha.riskmonitor.transaction.Customer;
import com.samhitha.riskmonitor.transaction.Transaction;
import com.samhitha.riskmonitor.transaction.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleEngine {

    private final List<Rule> rules;
    private final AlertService alertService;
    private final TransactionRepository transactionRepository;

    public RuleEngine(List<Rule> rules, AlertService alertService, TransactionRepository transactionRepository) {
        this.rules = rules;
        this.alertService = alertService;
        this.transactionRepository = transactionRepository;
    }

    public List<Rule> getRules() {
        return rules;
    }

    @Transactional
    public List<Alert> processTransaction(Transaction transaction) {
        Customer customer = transaction.getAccount() != null ? transaction.getAccount().getCustomer() : null;
        RuleContext context = new DefaultRuleContext(transactionRepository, customer);

        List<Alert> generatedAlerts = new ArrayList<>();
        for (Rule rule : rules) {
            Optional<RuleHit> hitOpt = rule.evaluate(transaction, context);
            if (hitOpt.isPresent()) {
                RuleHit hit = hitOpt.get();
                Alert alert = alertService.createAlert(transaction, hit.getRuleName(), hit.getSeverity(), hit.getReason());
                generatedAlerts.add(alert);
            }
        }
        return generatedAlerts;
    }
}
