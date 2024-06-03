package hibera.api.rtmwebappapi.Auth.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mailjet.apiKey}")
    private String apiKey;

    @Value("${mailjet.apiSecret}")
    private String apiSecret;

    @Value("${from.email.url}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void sendVerificationEmail(String toEmail, String token) {
        String verificationUrl = frontendUrl + "/auth/confirm-email?token=" + token;

        MailjetClient client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v3.1"));
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", fromEmail)
                                        .put("Name", "RTM"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)))
                                .put(Emailv31.Message.SUBJECT, "RTM - Please verify your email 💙")
                                .put(Emailv31.Message.TEXTPART, "Hi there👋 You recently registered to RTM. We need you to verify your email!")
                                .put(Emailv31.Message.HTMLPART, "<p>Hi there👋 You recently registered to RTM. We need you to verify your email by clicking the link: <a href='" + verificationUrl + "'>Verify Email</a></p>")));
        try {
            MailjetResponse response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPasswordResetEmail(String toEmail, String token) {
    }
}
