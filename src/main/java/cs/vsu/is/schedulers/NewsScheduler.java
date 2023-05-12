package cs.vsu.is.schedulers;

import cs.vsu.is.repository.NewsletterRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NewsScheduler {
    private final NewsletterRepository newsletterRepository;

    private final JavaMailSender mailSender;

    public NewsScheduler(NewsletterRepository newsletterRepository, JavaMailSender mailSender) {
        this.newsletterRepository = newsletterRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
    public void makeNewsletter() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime tenMinutesAgo = currentTime.minusMinutes(10);
        System.out.println("scheduler start");
        newsletterRepository.findAllByNewsletterDateBetween(tenMinutesAgo, currentTime)
            .forEach(item -> {
                if ("open".equals(item.getStatus())) {
                    item.getEmails().forEach(email -> {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(email);
                        message.setSubject(item.getSubject());
                        message.setText(item.getContent());
                        mailSender.send(message);
                    });
                    item.setStatus("close");
                    newsletterRepository.save(item);
                }
            });
    }
}
