package getsqlfromexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author GC014121
 */
public class GetSQLFromExcel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileInputStream fis = null;
        FileWriter myWriter = null;

        try {
            File initialFile = new File("dataFilePath");
            fis = new FileInputStream(initialFile);

            File outFile = new File("destinationFilePath");
            if (outFile.createNewFile()) {
                System.out.println("File created: " + outFile.getName());
            } else {
                System.out.println("File already exists.");
            }

            myWriter = new FileWriter(outFile);

            int sheetLimit = 8;
            int iteration = 0;
            Workbook workbook = new XSSFWorkbook(fis);
            while (iteration < sheetLimit) {
                Sheet sheet = workbook.getSheetAt(0);

                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    //Get the row object
                    Row row = rowIterator.next();
                    Iterator<Cell> iterator = row.cellIterator();
                    while (iterator.hasNext()) {
                        Cell celda = iterator.next();
                        celda.setCellType(CellType.STRING);
                    }

                    myWriter.write("INSERT INTO TABLE(OPL_ID,OPU_ID,OPL_TIPO_LIC) "
                            + "VALUES(" + row.getCell(0).getStringCellValue() + "," + row.getCell(1).getStringCellValue() + "," + row.getCell(2).getStringCellValue() +");\n");
                } //end of rows iterato
                iteration++;
            }
            myWriter.write("COMMIT;");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (myWriter != null) {
                myWriter.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
}
