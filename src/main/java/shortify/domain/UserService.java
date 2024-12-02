package shortify.domain;

import java.util.UUID;

public class UserService {

    public UUID createUser() {
        return UUID.randomUUID();
    }
}