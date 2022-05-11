package lol.tempmail.api;

import lol.tempmail.api.entity.Email;
import lol.tempmail.api.entity.Inbox;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TempMailTest {
    
    @Test
    void generateEmailTest() {
        Inbox i = TempMail.generateInbox();
        assertNotNull(i);
        assertNotNull(i.getAddress());
        assertNotNull(i.getToken());
        System.out.printf("addr: %s; token: %s\n", i.getAddress(), i.getToken());
    }
    
    @Test
    void getEmailTest() {
        Inbox i = TempMail.generateInbox();
        System.out.printf("");
        assertNotNull(i);
        assertNotNull(i.getAddress());
        assertNotNull(i.getToken());
        System.out.printf("addr: %s; token: %s\n", i.getAddress(), i.getToken());
        
        Email[] emails = TempMail.getEmails(i);
        assertNotNull(emails);
        
        for (Email e : emails) {
            System.out.printf("%s\n", e.getSubject());
        }
    }
}