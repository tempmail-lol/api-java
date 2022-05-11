# TempMail.lol Java API

This repository is for the [TempMail.lol](https://tempmail.lol) Java API.

## Usage

```java
import lol.tempmail.api.TempMail;
import lol.tempmail.api.entity.Inbox;

public class Main {
    public static void main(String[] args) {
        //inbox may be null if there is an error, keep this in mind
        Inbox inbox = TempMail.generateInbox();
        
        System.out.printf("Address: %s; token: %s", inbox.getAddress(), inbox.getToken());
        
        //check for emails (will be null if there are no emails)
        Email[] emails_a = TempMail.getEmails(inbox);
        
        //or, just use the token
        Email[] emails_b = TempMail.getEmails(inbox.getToken());
    }
}
```

## Installation

### Maven

Add the following to your `pom.xml`:
```xml
<dependency>
  <groupId>lol.tempmail</groupId>
  <artifactId>api</artifactId>
  <version>[VERSION]</version>
</dependency>
```

where [VERSION] is the version displayed on the GitHub releases page.

### Gradle

Coming soon!
