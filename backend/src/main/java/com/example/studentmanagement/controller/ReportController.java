package com.example.studentmanagement.controller;

import com.example.studentmanagement.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel() throws Exception {
        byte[] data = reportService.generateExcelReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() throws Exception {
        byte[] data = reportService.generatePdfReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}
