package com.finflux.emi.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;

import com.finflux.emi.EmiCalculator;
import com.finflux.emi.EmiParams;
import com.finflux.emi.PeriodFrequencyType;

public class ExcelUpdator {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        
        String fileName = args[0];
        System.out.println("file path: "+fileName);
        // obtaining input bytes from a file
        FileInputStream fis = new FileInputStream(new File(fileName));
        // creating workbook instance that refers to .xls file
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        // creating a Sheet object to retrieve the object
        HSSFSheet sheet = wb.getSheetAt(0);
        final Map<Integer, String> headersMap = new HashMap<>();
        for (Row row : sheet) {
            try {
                if (row.getRowNum() == 0) {
                    for (int i = 0; i < 9; i++) {
                        final Cell headerCell = row.getCell(i);
                        if (Objects.nonNull(headerCell)) {
                            headersMap.put(i, headerCell.getStringCellValue());
                        }
                    }
                    final Cell headerCell = row.createCell(9);
                    headerCell.setCellValue("Calculated Emi Amount");
                    headerCell.setCellType(CellType.STRING);
                    
                    continue;
                }

                double emiInMultiplesOf = 0;
                double principal = 0;
                double interestRate = 0;
                LocalDate interestStartdate = null;
                LocalDate firstDueDate = null;
                PeriodFrequencyType periodFrequency = PeriodFrequencyType.DAYS;
                int repayEvery = 0;
                int numberOfPayments = 0;

                for (int i = 1; i < headersMap.size(); i++) {
                    final Cell cell = row.getCell(i);
                    if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        switch (i) {
                            case 1:
                                principal = cell.getNumericCellValue();
                            break;
                            case 2:
                                interestRate = cell.getNumericCellValue();
                            break;
                            case 3:
                                interestStartdate = getLocalDate(cell.getDateCellValue());
                            break;
                            case 4:
                                firstDueDate = getLocalDate(cell.getDateCellValue());
                            break;
                            case 5:
                                int periodType = (int) cell.getNumericCellValue();
                                periodFrequency = PeriodFrequencyType.fromInt(periodType);
                            break;
                            case 6:
                                repayEvery = (int) cell.getNumericCellValue();
                            break;
                            case 7:
                                numberOfPayments = (int) cell.getNumericCellValue();
                            break;
                            case 8:
                                emiInMultiplesOf = cell.getNumericCellValue();
                            break;
                            default:
                            break;
                        }
                    } else {
                        final Cell cell2 = row.createCell(10);
                        cell2.setCellValue("Error");
                        cell2.setCellType(CellType.STRING);
                        continue;
                    }
                }

                EmiParams emiParams = new EmiParams(principal, interestRate, interestStartdate, firstDueDate, periodFrequency, repayEvery,
                        numberOfPayments, emiInMultiplesOf);
                double emiAmount = EmiCalculator.calculateEMI(emiParams);
                final Cell cell = row.createCell(9);
                cell.setCellValue(emiAmount);
                cell.setCellType(CellType.NUMERIC);

            } catch (Exception e) {
                e.printStackTrace();
                final Cell cell2 = row.createCell(10);
                cell2.setCellValue("Error");
                cell2.setCellType(CellType.STRING);
            }
        }
        
        
        IOUtils.closeQuietly(fis);
        
        FileOutputStream outputStream = new FileOutputStream(fileName);
        wb.write(outputStream);
        wb.close();
        IOUtils.closeQuietly(outputStream);
    }

    public static LocalDate getLocalDate(final Date date) {
        if (Objects.isNull(date)) { return null; }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
