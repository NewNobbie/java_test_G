package org.example.helpers.exceptions.Imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entities.Machine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {

    public List<Machine> importMachinesFromExcel(String filePath) throws IOException {
        List<Machine> machines = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String model = getCellValue(row.getCell(0));
                String serialNumber = getCellValue(row.getCell(1));
                String status = getCellValue(row.getCell(2));

                // Validate required fields
                if (model.isEmpty() || serialNumber.isEmpty() || status.isEmpty()) {
                    System.err.println("Skipping invalid row: Missing required data. Row: " + row.getRowNum());
                    continue;
                }

                machines.add(new Machine(model, serialNumber, status));
            }
        }
        return machines;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return ""; // Handle null cells

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to string
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}

