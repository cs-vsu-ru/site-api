package cs.vsu.is.web.rest;

import cs.vsu.is.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            int timetableIndex = 0; //todo: remember why inited there?
            Workbook workbook = parserService.filterTimetableByTeacher(teacherName, timetableIndex);

            String html = ParserService.convertHSSFToHtmlSchema((HSSFWorkbook) workbook);
            //todo: make two arguments for return
            return ResponseEntity.ok().body(domain + "api/filterTimetable\n" + html);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Filtering timetable failed. Reason: " + e.getMessage());
        }
    }

    public ResponseEntity<Tuple> filterTimetable(@RequestParam List<String> employeeNames){ //todo: or introduce chair index
        try {
            int timetableIndex = 0;
            Workbook workbook = parserService.filterTimetableByChair(employeeNames);

            String html = ParserService.convertHSSFToHtmlSchema((HSSFWorkbook) workbook);
            //todo: tuple also and return not null
            return null;
        }catch (Exception e){
            e.printStackTrace();
            Tuple responseArgs = null; //todo: fill tuple w/ hmtl ans File
            return ResponseEntity.badRequest().body(responseArgs);
        }
    }
}
