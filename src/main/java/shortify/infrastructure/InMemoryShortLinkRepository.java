package shortify.infrastructure;


import shortify.domain.ShortLink;

import java.util.HashMap;
import java.util.Map;

public class InMemoryShortLinkRepository {

    private final Map<String, ShortLink> storage = new HashMap<>();

    public void save(ShortLink link) {
        storage.put(link.getShortUrl(), link);
    }

    public ShortLink findByShortUrl(String shortUrl) {
        return storage.get(shortUrl);
    }

    public void delete(String shortUrl) {
        storage.remove(shortUrl);
    }
}