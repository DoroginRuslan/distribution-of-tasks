package exel_pars;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class main {

    public static void main(String[] args) {
        // Создается новый список и указывается начальный размер внутреннего массива
        ArrayList<String> adress = new ArrayList<>(20);
        ArrayList<String> error = new ArrayList<>(20);
        ArrayList<String> work1 = new ArrayList<>(20);
        ArrayList<String> work2 = new ArrayList<>(20);
        ArrayList<String> work3 = new ArrayList<>(20);

        //инициализируем потоки
        InputStream inputStream = null;
        XSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream("/home/vboxuser/tests/hacaton/distribution-of-tasks/src/test/java/exel_pars/data.xlsx");
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //разбираем первый лист входного файла на объектную модель
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        //проходим по всему листу
        it.next();
        int i = 0;
        int W1 = 0; int W2 = 0; double W3 = 0; double WAll = 0;
        while (it.hasNext()) {
            boolean time = false;
            boolean ok = false;
            double data, statements, cards;
            data = 0;
            cards = 0;
            statements =0;
            error.add(i,"отсутствует:");
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                switch (cell.getColumnIndex()) {
                    case 1:
                        if(cell.getStringCellValue().isEmpty()) error.add(i,(error.get(i)+"адрес;"));
                        else adress.add(cell.getStringCellValue());
                        break;
                    case 2:
                        if(cell.getStringCellValue().isEmpty()) error.add(i,(error.get(i)+" подключение точки;"));
                        else{
                            if (cell.getStringCellValue().equals("вчера")) time = true;
                            else if (cell.getStringCellValue().equals("давно")) time = false;
                        }
                        break;
                    case 3:
                        if(cell.getStringCellValue().isEmpty()) error.add(i,(error.get(i)+" доставка;"));
                        else {
                            if (cell.getStringCellValue().equals("да")) ok = true;
                        }
                        break;
                    case 4:
                            data = cell.getNumericCellValue();
                        break;
                    case 5:
                            statements = cell.getNumericCellValue();
                        break;
                    case 6:
                            cards = cell.getNumericCellValue();
                        break;
                    default:
                        break;

                }
            }
            if (((data > 7) && (cards != 0)) | (data > 14)) {
                work1.add("1");
                W1 += 4;
            }
            else work1.add("0");
            if ((statements > 0) && (cards/statements < 0.50)) {
                work2.add("1");
                W2 += 2;
            }
            else work2.add("0");
            if (time | ok) {
                work3.add("1");
                W3 += 1.5;
            }
            else work3.add("0");
            //System.out.print("["+(i+1)+"]");
            //if (!error.get(i).equals("отсутствует:")) System.out.print("ERROR! " + error.get(i) + "\t");
            //System.out.print(" Адрес: " + adress.get(i) + "\t Приоритет задач: ");
            //System.out.print("высокий " + work1.get(i) + "; средний " + work2.get(i) +"; низкий " + work3.get(i));
            i++;
        }
        WAll = W1 + W2 + W3;
        System.out.println("Общее время выполнения: " + WAll + "\tW1= " + W1/4 + "\tW2= " + W2/2 + "\tW3= " + W3/1.5);
    }
}
