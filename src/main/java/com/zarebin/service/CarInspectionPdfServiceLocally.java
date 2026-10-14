package com.zarebin.service;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.zarebin.entity.CarInspection;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class CarInspectionPdfServiceLocally {

    private static final String TEMPLATE_PATH = "templates/bishomare.pdf";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    private static final String FONT_PATH = "fonts/Vazir-Bold.ttf";
    private static final float FONT_SIZE = 88f;

    // Page dimensions (A4 – adjust if your template is different)
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();   // 595.0
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight(); // 842.0

    // -------------------------------------------------------------------
    // Main service method
    // -------------------------------------------------------------------
    public byte[] fillPdfForm(CarInspection inspection) throws IOException {
        try (InputStream templateStream = new ClassPathResource(TEMPLATE_PATH).getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // Load the template
            PDDocument document = Loader.loadPDF(templateStream.readAllBytes());
            PDPage page = document.getPage(0);  // first page

            // Load font – caching inside the method is optional
            PDType0Font font = loadFont(document);

            // Open content stream in append mode (preserve existing content)
            PDPageContentStream cs = new PDPageContentStream(
                    document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // Draw text
            writeRightAligned(cs, font, inspection.getFullName(), 690, 5300);
            writeRightAligned(cs, font, inspection.getPhoneNumber(), 800, 5600);
            writeRightAligned(cs, font, inspection.getCarName(), 900, 710);
            writeRightAligned(cs, font, inspection.getCarModel(), 5300, 690);
            writeRightAligned(cs, font, inspection.getChassisNumber(), 500, 670);
            writeRightAligned(cs, font,
                    inspection.getPlateNumber() != null ? inspection.getPlateNumber().toString() : "",
                    500, 650);

            if (inspection.getInspectionDate() != null) {
                String formattedDate = inspection.getInspectionDate().format(DATE_FORMATTER);
                writeRightAligned(cs, font, formattedDate, 800, 5600);
            }

            if (inspection.getExpertDescription() != null) {
                writeRightAligned(cs, font, inspection.getExpertDescription(), 500, 200);
            }

            // Draw check marks
            if (Boolean.TRUE.equals(inspection.getFrontBumper())) markCircle(cs, 3000, 4000);
            if (Boolean.TRUE.equals(inspection.getHood())) markCircle(cs, 300, 480);
            if (Boolean.TRUE.equals(inspection.getFrontRightFender())) markCircle(cs, 350, 460);
            if (Boolean.TRUE.equals(inspection.getFrontRightDoor())) markCircle(cs, 350, 440);
            if (Boolean.TRUE.equals(inspection.getRearRightDoor())) markCircle(cs, 350, 420);
            if (Boolean.TRUE.equals(inspection.getRearRightFender())) markCircle(cs, 350, 400);
            if (Boolean.TRUE.equals(inspection.getTrunk())) markCircle(cs, 300, 380);
            if (Boolean.TRUE.equals(inspection.getRearLeftFender())) markCircle(cs, 250, 400);
            if (Boolean.TRUE.equals(inspection.getRearLeftDoor())) markCircle(cs, 250, 420);
            if (Boolean.TRUE.equals(inspection.getFrontLeftDoor())) markCircle(cs, 250, 440);
            if (Boolean.TRUE.equals(inspection.getFrontLeftFender())) markCircle(cs, 250, 460);
            if (Boolean.TRUE.equals(inspection.getRoof())) markCircle(cs, 300, 520);
            if (Boolean.TRUE.equals(inspection.getRearBumper())) markCircle(cs, 300, 360);
            if (Boolean.TRUE.equals(inspection.getChassis())) markCircle(cs, 300, 340);

            cs.close();
            document.save(outputStream);
            document.close();

            return outputStream.toByteArray();
        }
    }

    // -------------------------------------------------------------------
    // Coordinate test helper
    // -------------------------------------------------------------------
    public byte[] generateCoordinateTestPdf() throws IOException {
        try (InputStream templateStream = new ClassPathResource(TEMPLATE_PATH).getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDDocument document = Loader.loadPDF(templateStream.readAllBytes());
            PDPage page = document.getPage(0);

            PDPageContentStream cs = new PDPageContentStream(
                    document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            log.info("Page size: {} x {}", pageWidth, pageHeight);

            // Horizontal lines (Y)
            cs.setStrokingColor(Color.RED);
            cs.setLineWidth(1.5f);
            for (int y = 0; y <= pageHeight; y += 200) {
                cs.moveTo(0, y);
                cs.lineTo(pageWidth, y);
                cs.stroke();

                // Y label
                cs.beginText();
                cs.setFont(loadFont(document), 8);
                cs.newLineAtOffset(20, y + 5);
                cs.showText("Y=" + y);
                cs.endText();
            }

            // Vertical lines (X)
            cs.setStrokingColor(Color.BLUE);
            for (int x = 0; x <= pageWidth; x += 200) {
                cs.moveTo(x, 0);
                cs.lineTo(x, pageHeight);
                cs.stroke();

                // X label
                cs.beginText();
                cs.setFont(loadFont(document), 8);
                cs.newLineAtOffset(x + 5, 20);
                cs.showText("X=" + x);
                cs.endText();
            }

            cs.close();
            document.save(outputStream);
            document.close();

            return outputStream.toByteArray();
        }
    }

    // -------------------------------------------------------------------
    // Write right‑aligned Persian text (uses ICU4J for shaping)
    // -------------------------------------------------------------------
    private void writeRightAligned(PDPageContentStream cs, PDType0Font font,
                                   String text, float x, float y) throws IOException {
        if (text == null || text.isEmpty()) return;

        String visualText = prepareRTLText(text);   // shape + reverse

        // Calculate width of the visual string (left‑to‑right glyphs)
        float textWidth = font.getStringWidth(visualText) / 1000 * FONT_SIZE;
        float drawX = x - textWidth;   // right‑aligned: position the right edge at x

        cs.beginText();
        cs.setFont(font, FONT_SIZE);
        cs.newLineAtOffset(drawX, y);
        cs.showText(visualText);
        cs.endText();
    }

    // -------------------------------------------------------------------
    // Arabic shaping + reversal (ICU4J)
    // -------------------------------------------------------------------
    private String prepareRTLText(String input) {
        try {
            ArabicShaping shaper = new ArabicShaping(ArabicShaping.LETTERS_SHAPE);
            String shaped = shaper.shape(input);
            // PDF expects glyphs in visual (left‑to‑right) order → reverse the string
            return new StringBuilder(shaped).reverse().toString();
        } catch (ArabicShapingException e) {
            log.error("Arabic shaping failed", e);
            return input;
        }
    }

    // -------------------------------------------------------------------
    // Draw a small checked box (black background, white X)
    // -------------------------------------------------------------------
    private void markCircle(PDPageContentStream cs, float x, float y) throws IOException {
        float size = 12;
        // Black rectangle
        cs.setNonStrokingColor(Color.BLACK);
        cs.addRect(x - 1, y - 1, size, size);
        cs.fill();

        // White X
        cs.setStrokingColor(Color.WHITE);
        cs.setLineWidth(1f);
        float r = size / 3;
        cs.moveTo(x - r, y - r);
        cs.lineTo(x + r, y + r);
        cs.moveTo(x + r, y - r);
        cs.lineTo(x - r, y + r);
        cs.stroke();
    }

    // -------------------------------------------------------------------
    // Load Vazir‑Bold font
    // -------------------------------------------------------------------
    private PDType0Font loadFont(PDDocument doc) throws IOException {
        try (InputStream fontStream = new ClassPathResource(FONT_PATH).getInputStream()) {
            return PDType0Font.load(doc, fontStream);
        }
    }
}
