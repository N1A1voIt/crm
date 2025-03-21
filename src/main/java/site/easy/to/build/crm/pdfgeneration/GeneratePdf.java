package site.easy.to.build.crm.pdfgeneration;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class GeneratePdf<T>  {
    public void exportToPDF(List<T> entityList, String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            if (entityList.isEmpty()) {
                document.add(new Paragraph("No data available."));
            } else {
                Field[] fields = entityList.get(0).getClass().getDeclaredFields();
                PdfPTable table = new PdfPTable(fields.length);
                for (Field field : fields) {
                    table.addCell(new PdfPCell(new Paragraph(field.getName())));
                }

                for (T entity : entityList) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        table.addCell(value != null ? value.toString() : "");
                    }
                }
                document.add(table);
            }
        } catch (DocumentException | IOException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
