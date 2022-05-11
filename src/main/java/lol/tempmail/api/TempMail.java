package lol.tempmail.api;

import lol.tempmail.api.entity.Email;
import lol.tempmail.api.entity.Inbox;
import lol.tempmail.api.exception.TempMailTokenExpiredException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

/**
 * Utility class to interact with the TempMail.lol API.
 *
 * @author Alexander Epolite
 * @since 1.0
 * 
 * @see Inbox
 * @see Email
 */
public class TempMail {
    
    private static final String BASE_URL = "https://api.tempmail.lol";
    
    private TempMail() {}
    
    /**
     * Make an HTTP request to the TempMail API.
     * 
     * @param endpoint The endpoint to make the request to.
     * @return The response as a {@link String} from the server, or {@code null} if there is no response data.
     * @throws IOException If the request fails.
     */
    @NotNull
    @Contract(pure = true)
    private static String makeHTTPRequest(@NotNull String endpoint) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(BASE_URL + endpoint).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "TempMailJavaAPI/1.0");
        
        //read all bytes from the input stream
        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder sb = new StringBuilder();
        while((bytesRead = connection.getInputStream().read(buffer)) != -1) {
            sb.append(new String(buffer, 0, bytesRead));
        }
        return sb.toString();
    }
    
    /**
     * Generate a new {@link Inbox}.
     * 
     * @return The new {@link Inbox}, or {@code null} if the request fails.
     */
    @Nullable
    @Contract(pure = true)
    public static Inbox generateInbox() {
        try {
            String s = makeHTTPRequest("/generate");
            JSONObject json = new JSONObject(s);
            return new Inbox(json.getString("address"), json.getString("token"));
        } catch(Exception e) {
            return null;
        }
    }
    
    /**
     * Get emails from the inbox provided the {@link Inbox}.
     * 
     * @param inbox The inbox to get emails from.
     * @return {@link Email[]} The emails received, or {@code null} if there are no emails.
     * @throws TempMailTokenExpiredException If the token is invalid.
     */
    @Nullable
    @Contract(pure = true)
    public static Email[] getEmails(@NotNull Inbox inbox) throws TempMailTokenExpiredException {
        try {
            String s = makeHTTPRequest("/auth/" + inbox.getToken());
            JSONObject json = new JSONObject(s);
            if(json.has("token") && json.getString("token").equals("invalid")) {
                throw new TempMailTokenExpiredException();
            }
            
            Email[] emails = new Email[json.getJSONArray("email").length()];
            for(int i = 0; i < emails.length; i++) {
                JSONObject obj = json.getJSONArray("email").getJSONObject(i);
                emails[i] = new Email(
                        obj.getString("to"),
                        obj.getString("from"),
                        obj.getString("subject"),
                        obj.getString("body"),
                        obj.has("html") ? obj.getString("html") : null, //html may be null
                        obj.getInt("date")
                );
            }
            
            return emails;
        } catch(Exception e) {
            return null;
        }
    }
    
    /**
     * Get emails from the inbox provided the token.
     * 
     * @param token The token of the inbox.
     * @return {@link Email[]} The emails received, or {@code null} if there are no emails.
     * @throws TempMailTokenExpiredException If the token is invalid.
     */
    @Nullable
    @Contract(pure = true)
    public static Email[] getEmails(@NotNull String token) throws TempMailTokenExpiredException {
        //just call the other method with an empty address for the inbox
        //since the address isn't used the token is all that's needed
        return getEmails(new Inbox("", token));
    }
    
}
