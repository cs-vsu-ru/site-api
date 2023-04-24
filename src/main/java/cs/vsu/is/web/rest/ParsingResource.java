package cs.vsu.is.web.rest;

import cs.vsu.is.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
//            Workbook workbook = new XSSFWorkbook("C:\\Users\\Рабочая\\IdeaProjects\\site-api\\files\\120d0925-4064-4fa9-aa25-82bc28c82647Saspisanie.xlsx");
            Workbook workbook = new XSSFWorkbook(filepath);
            parserService.parseXLSXToSlots(workbook, 0);
            parserService.parseXLSXToSlots(workbook, 1);
            return ResponseEntity.ok().body(domain + "api/parseTimetable" + filepath);
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.badRequest().body("Parsing failed");
        }
    }
}
