package cs.vsu.is.web.rest;

import cs.vsu.is.security.AuthoritiesConstants;
import cs.vsu.is.service.ParserService;
import cs.vsu.is.service.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jca.work.WorkManagerTaskExecutor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UtilResource {
    private final Logger log = LoggerFactory.getLogger(UtilResource.class);
    @Value("${domain}")
    private String domain;

    private final HttpServletRequest request;

    public UtilResource(HttpServletRequest request) {
        this.request = request;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(
        @RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            UUID uuid = UUID.randomUUID();
            Path path = Path.of("files/" + uuid + file.getOriginalFilename());
            log.debug("path {}", path);
            Path write = Files.write(path, bytes);
            String scheme = request.getScheme();
            String domain = request.getServerName();
            int port = request.getServerPort();
            String url = scheme + "://" + domain + ":" + port;
            return ResponseEntity.ok().body(url + "/is/api/" + write);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
