package com.bazaarvoice.polls.email;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.google.common.collect.ImmutableMap;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Log
public class PollResultEmailSenderService {
    private final AWSCredentials _credentials;
    private final EmailTemplateRenderingService _renderingService;
    private final static String FROM_EMAIL = "no-reply@hackathon.ts.bazaarvoice.com";
    private static final String AWS_CREDENTIAL_FILENAME = "aws-keys.properties";

    public PollResultEmailSenderService()
            throws IOException {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(AWS_CREDENTIAL_FILENAME);
        _credentials = new PropertiesCredentials(input);
        _renderingService = new EmailTemplateRenderingService();
    }

    private void sendEmail(String recipient, String subject, String content, boolean isHTML) {
        Destination destination = new Destination().withToAddresses(recipient);
        Content subjectContent = new Content().withData(subject);
        Content bodyContent = new Content().withData(content);
        Body body = new Body();
        if (isHTML) {
            body.setHtml(bodyContent);
        } else {
            body.setText(bodyContent);
        }

        Message msg = new Message()
                .withSubject(subjectContent)
                .withBody(body);

        SendEmailRequest request = new SendEmailRequest()
                .withSource(FROM_EMAIL)
                .withDestination(destination)
                .withMessage(msg);

        try {
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(_credentials);

            Region region = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(region);
            client.sendEmail(request);
            log.info("Email sent to " + recipient);
        } catch (Exception e) {
            log.warning("Email failed to send: " + e.getMessage());
        }
    }

    public void sendPositiveEmail(String recipient, Map<String, Object> model) {
        log.info("Sending email to " + recipient);
        String content = _renderingService.render(EmailTemplateRenderingService.Template.POSITIVE, model);
        log.info(content);
        // sendEmail(recipient, "Your poll has completed!", content, true);
    }

    public void sendNegativeEmail(String recipient, Map<String, Object> model) {
        log.info("Sending email to " + recipient);
        log.info(_renderingService.render(EmailTemplateRenderingService.Template.NEGATIVE, model));
    }

    public static void main(String[] args)
            throws IOException {
        PollResultEmailSenderService service = new PollResultEmailSenderService();

        service.sendPositiveEmail("jeremy.shoemaker@bazaarvoice.com", ImmutableMap.<String, Object>of("product", "Titanfall for Xbox 360"));
        service.sendNegativeEmail("jeremy.shoemaker@bazaarvoice.com", ImmutableMap.<String, Object>of("product", "Titanfall for Xbox 360"));
    }
}
