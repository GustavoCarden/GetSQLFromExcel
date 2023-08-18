/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getsqlfromexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class ComapreObjects {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileInputStream fis = null;
        FileWriter myWriter = null;

        try {
            //File initialFile = new File("C:\\Users\\GC014121\\Documents\\Proyectos\\Logistica\\Documentos\\Depuracion GM\\Archivos Separados\\Cambio de Id.xlsx");
            File initialFile = new File("C:\\Users\\GC014121\\Desktop\\Libro1.xlsx");
            fis = new FileInputStream(initialFile);

            File outFile = new File("C:\\Users\\GC014121\\Desktop\\Output.txt");
            if (outFile.createNewFile()) {
                System.out.println("File created: " + outFile.getName());
            } else {
                System.out.println("File already exists.");
            }

            myWriter = new FileWriter("C:\\Users\\GC014121\\Desktop\\Output.txt");

            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(2);

            //every sheet has rows, iterate over them
            List<Obj> todos = new ArrayList<>();
            List<Obj> activos = new ArrayList<>();
            List<Obj> inactivos = new ArrayList<>();
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                //Get the row object
                Row row = rowIterator.next();
                Iterator<Cell> iterator = row.cellIterator();
                while (iterator.hasNext()) {
                    Cell celda = iterator.next();
                    celda.setCellType(CellType.STRING);
                }
                todos.add(new Obj(Integer.valueOf(row.getCell(0).getStringCellValue()), Integer.valueOf(row.getCell(1).getStringCellValue())));
                if (row.getCell(3) != null && row.getCell(3).getStringCellValue() != null) {
                    activos.add(new Obj(Integer.valueOf(row.getCell(3).getStringCellValue()), Integer.valueOf(row.getCell(4).getStringCellValue())));
                }

                //myWriter.write("DELETE LOG_CONCEPTOS_GASTOS WHERE CIA_NUM = " + row.getCell(0).getStringCellValue() + " AND N_CCG_ID = " + row.getCell(4).getStringCellValue() + " ;\n");
            } //end of rows iterato

            for (Obj obj : todos) {
                boolean exist = false;
                for (Obj objAux : activos) {
                    if (obj.getCia_num().equals(objAux.getCia_num()) && obj.getId().equals(objAux.getId())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    inactivos.add(obj);
                }
            }

            for (Obj obj : inactivos) {
                myWriter.write(obj.getId() + " "+ obj.getCia_num() +"\n");
            }
            
            System.out.println("");
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
