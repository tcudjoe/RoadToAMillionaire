package hibera.api.rtmwebappapi.Auth.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${mailjet.apiKey}")
    private static String apiKey= "293e75c07c9fab8172f14c657e5bfca3";

    @Value("${mailjet.apiSecret}")
    private static String apiSecret = "2361127bdd3bb225834cded879020c0a";

    public void sendVerificationEmail(String toEmail, String verificationUrl) {
        MailjetClient client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v3.1"));
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "tyra@hibera-agency.com")
                                        .put("Name", "RTM"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)))
                                .put(Emailv31.Message.SUBJECT, "Please verify your email")
                                .put(Emailv31.Message.TEXTPART, "Please verify your email by clicking the link: " + verificationUrl)
                                .put(Emailv31.Message.HTMLPART, "<p>Please verify your email by clicking the link: <a href='" + verificationUrl + "'>Verify Email</a></p>")));
        try {
            MailjetResponse response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
