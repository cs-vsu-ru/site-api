package cs.vsu.is.web.rest;

import cs.vsu.is.service.ParserService;
import io.undertow.server.handlers.form.FormData;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jboss.marshalling.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.Tuple;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParsingResource {
    private final Logger log = LoggerFactory.getLogger(ParserService.class);
    private final String timetableExtension = ".xlsx";
    @Value("${domain}")
    private String domain;
    private final ParserService parserService;

    @PostMapping("/parseTimetable")
    public ResponseEntity<String> parseFile(
        @RequestParam("filepath") String filepath) {
        try {
            String path = filepath.substring(filepath.lastIndexOf('/'));
            parserService.parseTimetable(path);
            return ResponseEntity.ok().body(domain + "api/parseTimetable" + filepath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Parsing failed with reason: " + e.getMessage());
        }
    }

    @GetMapping("/filterTimetable")
    public ResponseEntity<String> filterTimetable(@RequestParam String teacherName) {
        try {
            Workbook workbook = parserService.filterTimetableByTeacher(teacherName);

            String html = ParserService.convertHSSFToHtmlSchema((HSSFWorkbook) workbook);
            StringBuilder responseBodyArgs = new StringBuilder();
            for (String line : html.split("\n")) {
                if (!(line.contains("<col") || line.contains("</col"))) {
                    responseBodyArgs.append(line);
                    responseBodyArgs.append("\n");
                }
            }

            return ResponseEntity.ok().body(responseBodyArgs.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Filtering timetable failed. Reason: " + e.getMessage());
        }
    }

    @GetMapping("/filterTimetableForChair")
    public ResponseEntity<String> filterTimetable(@RequestParam List<String> employeeNames) { //todo: or introduce chair index
        try {
            Workbook workbook = parserService.filterTimetableByChair(employeeNames);

            String html = ParserService.convertHSSFToHtmlSchema((HSSFWorkbook) workbook);
            StringBuilder responseBodyArgs = new StringBuilder();
            for (String line : html.split("\n")) {
                if (!(line.contains("<col") || line.contains("</col"))) {
                    responseBodyArgs.append(line);
                    responseBodyArgs.append("\n");
                }
            }

            return ResponseEntity.ok().body(responseBodyArgs.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Filtering timetable failed. Reason: " + e.getMessage());
        }
    }
}
