package com.finflux.emi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmiCalculator {

//    public static void main(String[] args) {
//        EmiParams emiParams = new EmiParams(1000000, 28, LocalDate.of(2021, 7, 26), LocalDate.of(2021, 7, 26).plusDays(28 * 4),
//                PeriodFrequencyType.DAYS, 28, 10, 50);
//        double emi = calculateEMI(emiParams);
//        
//        System.out.println(emi);
//
//    }


    public static double calculateEMI(EmiParams emiParams) {
        double interestRatePerDay = emiParams.getInterestRate() / 36500;

        final List<LocalDate> repaymentDates = new ArrayList<>();
        LocalDate dueDate = emiParams.getFirstDueDate();
        repaymentDates.add(dueDate);
        for (int i = 1; i < emiParams.getNumberOfPayments(); i++) {
            dueDate = getRepaymentPeriodDate(emiParams.getPeriodFrequency(), emiParams.getRepayEvery(), dueDate);
            repaymentDates.add(dueDate);
        }
        double emi = FinanicalFunctions.pmtDaily(interestRatePerDay, emiParams.getInterestStartdate(), repaymentDates,
                emiParams.getPrincipal());
        return ceiling(emi, emiParams.getEmiInMultiplesOf());
    }
    
    
    public static double ceiling(final double n, final double s) {
        double c;

        if ((n < 0 && s > 0) || (n > 0 && s < 0)) {
            c = Double.NaN;
        } else {
            c = (n == 0 || s == 0) ? 0 : Math.ceil(n / s) * s;
        }

        return c;
    }
    
    public static LocalDate getRepaymentPeriodDate(final PeriodFrequencyType frequency, final int repaidEvery, final LocalDate startDate) {
        LocalDate dueRepaymentPeriodDate = startDate;
        switch (frequency) {
            case DAYS:
                dueRepaymentPeriodDate = startDate.plusDays(repaidEvery);
            break;
            case WEEKS:
                dueRepaymentPeriodDate = startDate.plusWeeks(repaidEvery);
            break;
            case MONTHS:
                dueRepaymentPeriodDate = startDate.plusMonths(repaidEvery);
            break;
            case YEARS:
                dueRepaymentPeriodDate = startDate.plusYears(repaidEvery);
            break;
            case INVALID:
            break;
        }
        return dueRepaymentPeriodDate;
    }

}
