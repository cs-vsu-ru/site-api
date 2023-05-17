package cs.vsu.is.service.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFileDTO {
    MultipartFile file;
    String extension;
}
