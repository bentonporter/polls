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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Log
public class PollResultEmailSenderService {
    private final AWSCredentials _credentials;
    private final EmailTemplateRenderingService _renderingService;
    private final static String FROM_EMAIL = "no-reply@hackathon.ts.bazaarvoice.com";
    private static final String AWS_CREDENTIAL_FILENAME = "aws-keys.properties";
    private static final String SUBJECT_LINE = "Your poll results are in!";

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
        sendEmail(recipient, SUBJECT_LINE, content, true);
    }

    public void sendNegativeEmail(String recipient, Map<String, Object> model) {
        log.info("Sending email to " + recipient);
        String content = _renderingService.render(EmailTemplateRenderingService.Template.NEGATIVE, model);
        sendEmail(recipient, SUBJECT_LINE, content, true);
    }

    public static void main(String[] args)
            throws IOException {
        PollResultEmailSenderService service = new PollResultEmailSenderService();

        Map<String, Object> positiveModel = new HashMap<String, Object>();
        positiveModel.put("product", "Titanfall for Xbox 360");
        positiveModel.put("vendor", "BestBuy");
        positiveModel.put("product_url", "http://example.com/product-link");
        service.sendPositiveEmail("jeremy.shoemaker@bazaarvoice.com", positiveModel);

        Map<String, Object> negativeModel = new HashMap<String, Object>();
        negativeModel.put("product", "Titanfall for Xbox 360");
        negativeModel.put("vendor", "BestBuy");
        negativeModel.put("product_url", "http://example.com/product-link");
        List<Map<String, Object>> recommendations = new LinkedList<Map<String, Object>>();
        recommendations.add(ImmutableMap.<String, Object>of("name", "Call of Duty: Ghosts", "url", "http://example.com/call-of-duty-ghosts"));
        recommendations.add(ImmutableMap.<String, Object>of("name", "Dust 514", "url", "http://example.com/dust-514"));
        negativeModel.put("recs", recommendations);
        service.sendNegativeEmail("jeremy.shoemaker@bazaarvoice.com", negativeModel);
    }
}
