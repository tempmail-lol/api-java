package lol.tempmail.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Inbox {
    private final String address;
    private final String token;
}
