package shortify.domain;

import shortify.infrastructure.ConfigLoader;
import shortify.infrastructure.InMemoryShortLinkRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShortLinkService {

    private final InMemoryShortLinkRepository repository = new InMemoryShortLinkRepository();

    public ShortLink createShortLink(String originalUrl, UUID userId, Integer clickLimit, LocalDateTime expirationDate) {
        int defaultClickLimit = ConfigLoader.getIntProperty("defaultClickLimit");
        int limit = clickLimit != null ? Math.max(clickLimit, defaultClickLimit) : defaultClickLimit;

        int defaultExpirationDays = ConfigLoader.getIntProperty("defaultExpirationDays");
        LocalDateTime finalExpirationDate = expirationDate != null ? expirationDate : LocalDateTime.now().plusDays(defaultExpirationDays);

        String shortUrl = generateShortUrl(originalUrl, userId);
        ShortLink link = new ShortLink(originalUrl, shortUrl, finalExpirationDate, limit);
        repository.save(link);
        return link;
    }

    public void deleteShortLink(String shortUrl, UUID userId) {
        ShortLink link = repository.findByShortUrl(shortUrl);
        if (link != null && link.getUserId().equals(userId)) {
            repository.delete(shortUrl);
            System.out.println("Ссылка успешно удалена.");
        } else {
            System.out.println("Недостаточно прав для удаления ссылки или ссылка не найдена.");
        }
    }

    public void editClickLimit(String shortUrl, UUID userId, int newLimit) {
        ShortLink link = repository.findByShortUrl(shortUrl);
        if (link != null && link.getUserId().equals(userId)) {
            link.setClickLimit(newLimit);
            System.out.println("Лимит переходов успешно изменен.");
        } else {
            System.out.println("Недостаточно прав для редактирования ссылки или ссылка не найдена.");
        }
    }

    public String findOriginalUrl(String shortUrl) {
        ShortLink link = repository.findByShortUrl(shortUrl);
        return (link != null && link.isAvailable()) ? link.getOriginalUrl() : null;
    }

    private String generateShortUrl(String originalUrl, UUID userId) {
        return "clck.ru/" + UUID.randomUUID().toString().substring(0, 8);
    }
}