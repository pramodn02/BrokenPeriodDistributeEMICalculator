package com.finflux.emi;

import java.time.LocalDate;

public class EmiParams {

    double emiInMultiplesOf;
    double principal;
    double interestRate;
    LocalDate interestStartdate;
    LocalDate firstDueDate;
    PeriodFrequencyType periodFrequency;
    int repayEvery;
    int numberOfPayments;

    public EmiParams(double principal, double interestRate, LocalDate interestStartdate, LocalDate firstDueDate,
            PeriodFrequencyType periodFrequency, int repayEvery, int numberOfPayments, double emiInMultiplesOf) {
        this.emiInMultiplesOf = emiInMultiplesOf;
        this.principal = principal;
        this.interestRate = interestRate;
        this.interestStartdate = interestStartdate;
        this.firstDueDate = firstDueDate;
        this.periodFrequency = periodFrequency;
        this.repayEvery = repayEvery;
        this.numberOfPayments = numberOfPayments;
    }

    public double getEmiInMultiplesOf() {
        return this.emiInMultiplesOf;
    }

    public double getPrincipal() {
        return this.principal;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public LocalDate getInterestStartdate() {
        return this.interestStartdate;
    }

    public LocalDate getFirstDueDate() {
        return this.firstDueDate;
    }

    public PeriodFrequencyType getPeriodFrequency() {
        return this.periodFrequency;
    }

    public int getRepayEvery() {
        return this.repayEvery;
    }

    
    public int getNumberOfPayments() {
        return this.numberOfPayments;
    }
}
