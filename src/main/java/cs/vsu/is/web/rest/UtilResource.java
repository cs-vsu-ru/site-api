package cs.vsu.is.web.rest;

import cs.vsu.is.security.AuthoritiesConstants;
import cs.vsu.is.service.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UtilResource {
    private final Logger log = LoggerFactory.getLogger(UtilResource.class);

    @PostMapping("/uploadFile")
    public ResponseEntity<Path> uploadFile(
        @RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            UUID uuid = UUID.randomUUID();
            Path path = Path.of("files/"+uuid + file.getOriginalFilename());
            Files.write(path, bytes);
            return ResponseEntity.ok().body(path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
