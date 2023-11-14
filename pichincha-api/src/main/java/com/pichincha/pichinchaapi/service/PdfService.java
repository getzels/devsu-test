package com.pichincha.pichinchaapi.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.pichincha.pichinchaapi.dto.StatementOfAccountDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfService {

    public byte[] createPdf(StatementOfAccountDTO report) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add content to the document using iText
            document.add(new Paragraph("Report Title"));
            // More code to add content from 'report' to the PDF

            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error in PDF generation", e);
        }
    }
}

