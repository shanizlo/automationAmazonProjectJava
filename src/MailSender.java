import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;

public class MailSender {

    Configuration configuration;

    public MailSender() {
        configuration = new Configuration()
                .apiKey("key-f2bfcc80873bb104362602055d79f8ab")
                .domain("sandboxf4e867ccd7be4ad5a8c29e8e6177f68d.mailgun.org");
        // TODO: a real domain must be used in order to send mails. For now, each subscriber has to be added manually
        // and confirm an invitation
    }

    public void send(String from, String to, String subject, String text) throws Exception {
        Response response = Mail.using(configuration)
                .from(from)
                .to(to)
                .subject(subject)
                .text(text)
                .build()
                .send();

        if (!response.isOk())
            throw new Exception("Failed to response mail: " + response.responseMessage());
    }
}
