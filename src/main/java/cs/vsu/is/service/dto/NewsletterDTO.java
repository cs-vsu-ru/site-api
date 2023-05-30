package cs.vsu.is.service.dto;

import cs.vsu.is.domain.Emails;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsletterDTO {

    private Long id;

    private String newsletterDate;

    private String subject;

    private String content;

    private String status;

    private List<Emails> emails = new ArrayList<>();
}
