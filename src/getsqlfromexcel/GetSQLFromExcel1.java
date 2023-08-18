package getsqlfromexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author GC014121
 */
public class GetSQLFromExcel1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileInputStream fis = null;
        FileWriter myWriter = null;

        try {
//            File initialFile = new File("C:\\Users\\GC014121\\Documents\\Proyectos\\Logistica\\Documentos\\Depuracion Transportistas\\ActInaTransportistas.xlsx");
            File initialFile = new File("C:\\Users\\GC014121\\Desktop\\PROG.xlsx");
            fis = new FileInputStream(initialFile);

            File outFile = new File("C:\\Users\\GC014121\\Desktop\\Output.txt");
            if (outFile.createNewFile()) {
                System.out.println("File created: " + outFile.getName());
            } else {
                System.out.println("File already exists.");
            }

            myWriter = new FileWriter("C:\\Users\\GC014121\\Desktop\\Output.txt");

            int sheetLimit = 8;
            int iteration = 0;
            Map<Integer, String> pasesInactivados = new HashMap<>();
            Workbook workbook = new XSSFWorkbook(fis);
            while (iteration < sheetLimit) {
                Sheet sheet = workbook.getSheetAt(iteration);

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

                    if (!pasesInactivados.containsValue(row.getCell(6).getStringCellValue()) &&  row.getCell(9).getStringCellValue().equals("AC")) {
                        myWriter.write("UPDATE LOG_PASEC SET V_LPC_ESTADO = 'CA', D_FEC_B = sysdate, V_USU_CVE_B = 'PF013783' WHERE CIA_NUM = 96 AND N_LPC_NUMPASE = "+row.getCell(6).getStringCellValue());
                        myWriter.write(";\n");
                        pasesInactivados.put(pasesInactivados.size(), row.getCell(6).getStringCellValue());
                    }
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
