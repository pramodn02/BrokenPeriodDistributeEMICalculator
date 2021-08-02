# BrokenPeriodDistributeEMICalculator
Utility to compute the equal EMI with broken period. with this utility while computing the EMI instead of equal periods considered actual days of the peiord.

### How to Use the Utility
with in the project Under samples included executable jar and a sample Excel file 
To compute any value add the required data into the excel and then run the jar file.
same excel will get updated with EMI amount.

### How to run the Utility
java -jar emi_excel_updator.jar /Volumes/work/emi_updator.xls

### required content of the Excel
index 0 : any identity.  
index 1 : Principal Amount.  
index 2 : Interest Rate.  
index 3 : Interest Start Date.  
index 4 : First EMI Date.  
index 5 : Period Type (days-0 , weeks-1, months-2, years-3).  
index 6 : repay every (ex : every 28 days).  
index 7 : number Of EMI's.  
index 8 : EMI in multiples of.  
index 9 : EMI Amount (Updated By the Utility after the execution).  
