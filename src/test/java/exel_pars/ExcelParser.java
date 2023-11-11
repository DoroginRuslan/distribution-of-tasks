package exel_pars;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ExcelParser {

    public static String parse(String fileName) {
        //инициализируем потоки
        String result = "";
        InputStream inputStream = null;
        XSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream(fileName);
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //разбираем первый лист входного файла на объектную модель
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        //проходим по всему листу
        it.next();
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            cells.next();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                //перебираем возможные типы ячеек
                if (cell.getColumnIndex() > 7) break;
                switch (cell.getCellType()) {
                    case STRING:
                        result += cell.getStringCellValue() + " ";
                        break;
                    case NUMERIC:
                        result += cell.getNumericCellValue() + " ";
                        break;

                    case FORMULA:
                        result += cell.getNumericCellValue() + " ";
                        break;
                    default:
                        result += "empty";
                        break;
                }
            }
            result += "\n";
        }

        return result;
    }

}

