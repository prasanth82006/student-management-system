package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final StudentRepository studentRepository;

    public byte[] generateExcelReport() throws Exception {
        List<Student> students = studentRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Students");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("First Name");
            headerRow.createCell(2).setCellValue("Last Name");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("CGPA");

            int rowIdx = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(student.getStudentId() != null ? student.getStudentId() : student.getId().toString());
                row.createCell(1).setCellValue(student.getFirstName());
                row.createCell(2).setCellValue(student.getLastName());
                row.createCell(3).setCellValue(student.getEmail());
                row.createCell(4).setCellValue(student.getCgpa());
            }
            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generatePdfReport() throws Exception {
        List<Student> students = studentRepository.findAll();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            
            document.add(new Paragraph("Student Management System - Report"));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(5);
            table.addCell("ID");
            table.addCell("First Name");
            table.addCell("Last Name");
            table.addCell("Email");
            table.addCell("CGPA");
            
            for (Student student : students) {
                table.addCell(student.getStudentId() != null ? student.getStudentId() : student.getId().toString());
                table.addCell(student.getFirstName());
                table.addCell(student.getLastName());
                table.addCell(student.getEmail());
                table.addCell(String.valueOf(student.getCgpa()));
            }
            document.add(table);
            document.close();
            return out.toByteArray();
        }
    }
}
