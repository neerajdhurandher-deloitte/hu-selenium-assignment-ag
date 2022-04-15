package Util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CardExcelReader {
    String filePath;
    File file;
    FileInputStream inputStream;
    XSSFWorkbook workbook;
    XSSFSheet workbookSheet;

    ArrayList<Card> cardList = new ArrayList<>();


    public CardExcelReader(String path) throws IOException {

        filePath = path;

        file = new File(filePath);

        inputStream = new FileInputStream(file);

        workbook = new XSSFWorkbook(inputStream);

        workbookSheet = workbook.getSheet("sheet1");

    }

    public ArrayList<Card> readCardDetailsFile() throws IOException {

        int rowCount = workbookSheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rowCount; i++) {

            Row row = workbookSheet.getRow(i);

            int cellCount = row.getLastCellNum();

            Card card = new Card();


            for (int j = 0; j < cellCount; j++) {

                String data;
                try {
                    data = row.getCell(j).getStringCellValue();
                } catch (Exception e) {
                    data = String.valueOf((int)row.getCell(j).getNumericCellValue());

                }

                switch (j) {
                    case 0 -> card.setCardNo(data);
                    case 1 -> card.setExpiryMonth(data);
                    case 2 -> card.setExpiryYear(data);
                    case 3 -> card.setCvc(data);
                    case 4 -> card.setZipCode(data);
                    case 5 -> card.setEmail(data);
                }
            }

            cardList.add(card);
        }

        return cardList;
    }
}
