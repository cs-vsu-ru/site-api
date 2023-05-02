package cs.vsu.is.web.rest;

import cs.vsu.is.service.ParserService;
import lombok.RequiredArgsConstructor;
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
            String path = filepath.substring(filepath.lastIndexOf('\\'));
            Workbook workbook = new XSSFWorkbook("files" + path);
            parserService.parseXLSXToSlots(workbook, 0);
            parserService.parseXLSXToSlots(workbook, 1);
            return ResponseEntity.ok().body(domain + "api/parseTimetable" + filepath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Parsing failed with reason: " + e.getMessage());
        }
    }

    @GetMapping("/filterTimetable")
    public ResponseEntity<String> filterTimetable(@RequestParam String teacherName) {
        try {
            String html = "";

//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = new XSSFSheet();
//            html = sheet.toString();

            //todo: algorithm of sorting timetable
            // 1. search for teacher name
            // 2. search for all lessons with id of previously found teacher
            // 3. sort timetables by weekdays and by time
            // 4. form a new Workbook and fill it with content
            // 5. solve problem with parsing XSSFWorkbook to html

            return ResponseEntity.ok().body(domain + "api/filterTimetable\n" + html);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Filtering timetable failed. Reason: " + e.getMessage());
        }
    }


}
