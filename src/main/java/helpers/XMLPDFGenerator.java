package helpers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Bug;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.ejb.Stateless;
import java.io.*;
import java.util.List;


@Stateless
public class XMLPDFGenerator implements Serializable{

    public InputStream objToPdf(List<Bug> lst){
        Document document = new Document();
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();



        for(Bug e:lst) {
            String line=e.getTitle()+" "+e.getDescription()+" "+ " " +e.getFixedInVersion()+" "+e.getRevision()+" "+e.getSeverity()
                         +" " + e.getStatus()+" "+ e.getAssignedTo().getName()+ " "+e.getTargetData() + " "+e.getAttachment();
            Paragraph subPara = new Paragraph(line);
            try {
                document.add(subPara);
            } catch (DocumentException exc) {
                exc.printStackTrace();
            }
        }
        document.close();

        ByteArrayInputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }
    public InputStream objToExcel(List<Bug> lst){
        String[] columns = {"Title", "Description", "FixedInVersion","Revision","Severity","Status","AssignedTo","TargetData","Attachements"};
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        Workbook workbook = new HSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Bugs");


        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        int rowNum = 1;
        for(Bug bug: lst) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(bug.getTitle());

            row.createCell(1)
                    .setCellValue(bug.getDescription());

            row.createCell(2)
                    .setCellValue(bug.getFixedInVersion());

            row.createCell(3)
                    .setCellValue(bug.getRevision());

            row.createCell(4)
                    .setCellValue(bug.getSeverity());

            row.createCell(5)
                    .setCellValue(bug.getStatus());

            row.createCell(6)
                    .setCellValue(bug.getAssignedTo().getName());

            row.createCell(7)
                    .setCellValue(bug.getTargetData().toString());

            row.createCell(8)
                    .setCellValue("");


        }
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Closing the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }


}