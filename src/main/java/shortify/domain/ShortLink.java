package shortify.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShortLink {
    private String originalUrl;
    private String shortUrl;
    private UUID userId;
    private LocalDateTime expirationDate;
    private int clickLimit;
    private int currentClickCount;
    private LocalDateTime createdAt;

    public ShortLink(String originalUrl, String shortUrl, LocalDateTime expirationDate, int clickLimit) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.expirationDate = expirationDate;
        this.clickLimit = clickLimit;
        this.currentClickCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isAvailable() {
        return currentClickCount < clickLimit && LocalDateTime.now().isBefore(expirationDate);
    }

    public void incrementClickCount() {
        if (isAvailable()) {
            currentClickCount++;
        }
    }

    public int getClickLimit() {
        return clickLimit;
    }

    public void setClickLimit(int clickLimit) {
        this.clickLimit = clickLimit;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}