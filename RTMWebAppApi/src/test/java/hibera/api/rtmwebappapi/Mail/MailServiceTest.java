package hibera.api.rtmwebappapi.Mail;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import hibera.api.rtmwebappapi.Auth.service.mail.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MailServiceTest {
    @InjectMocks
    private MailService mailService;

    @Mock
    private MailjetResponse mailjetResponse;

    private final String apiKey = "293e75c07c9fab8172f14c657e5bfca3";
    private final String apiSecret = "e9de5e1def760d148ac124be0ef82939";
    private final String fromEmail = "tyra@hibera-agency.com";
    private final String frontendUrl = "http://localhost:4200";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(mailService, "apiKey", apiKey);
        ReflectionTestUtils.setField(mailService, "apiSecret", apiSecret);
        ReflectionTestUtils.setField(mailService, "fromEmail", fromEmail);
        ReflectionTestUtils.setField(mailService, "frontendUrl", frontendUrl);
    }

    @Test
    public void testSendVerificationEmail() throws Exception {
        String toEmail = "user@example.com";
        String token = "verificationToken";

        MailjetClient mailjetClient = mock(MailjetClient.class);
        when(mailjetClient.post(any(MailjetRequest.class))).thenReturn(mailjetResponse);
        ReflectionTestUtils.setField(mailService, "mailjetClient", mailjetClient);

        mailService.sendVerificationEmail(toEmail, token);

        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));
    }

    @Test
    public void testSendPasswordResetEmail() throws Exception {
        String toEmail = "user@example.com";
        String token = "resetToken";

        MailjetClient mailjetClient = mock(MailjetClient.class);
        when(mailjetClient.post(any(MailjetRequest.class))).thenReturn(mailjetResponse);
        ReflectionTestUtils.setField(mailService, "mailjetClient", mailjetClient);

        mailService.sendPasswordResetEmail(toEmail, token);

        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));
    }
}
