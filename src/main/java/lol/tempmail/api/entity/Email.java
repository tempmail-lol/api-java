package lol.tempmail.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@AllArgsConstructor
public final class Email {
    
    @NotNull
    private final String toAddress;
    
    @NotNull
    private final String fromAddress;
    
    @NotNull
    private final String subject;
    
    @NotNull
    private final String body;
    
    @Nullable
    private final String html;
    
    private final int date;
}
