package cs.vsu.is.web.rest;

import cs.vsu.is.security.AuthoritiesConstants;
import cs.vsu.is.service.ParserService;
import cs.vsu.is.service.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UtilResource {
    private final Logger log = LoggerFactory.getLogger(UtilResource.class);
    private final ParserService parserService;
    @PostMapping("/uploadFile")
    public ResponseEntity<Path> uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("extension") String extension) {
        try {
            byte[] bytes = file.getBytes();
            UUID uuid = UUID.randomUUID();
            Path path = Path.of("files/" + uuid + extension);
            Files.write(path, bytes);
            String filepath = "src/main/resources/Saspisanie.xlsx";

            Workbook workbook = new XSSFWorkbook(path.toString());
            parserService.parseXLSXToSlots(workbook, 0);
            parserService.parseXLSXToSlots(workbook, 1);

            return ResponseEntity.ok().body(path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
