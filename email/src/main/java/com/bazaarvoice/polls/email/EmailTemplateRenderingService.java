package com.bazaarvoice.polls.email;

import com.google.common.base.Throwables;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Map;

@Log
public class EmailTemplateRenderingService {
    private static final String POSITIVE_TEMPLATE_NAME = "positive_email";
    private static final String NEGATIVE_TEMPLATE_NAME = "negative_email";
    private final JadeConfiguration _jadeConfig;
    private JadeTemplate _positiveTemplate;
    private JadeTemplate _negativeTemplate;

    public enum Template {
        POSITIVE,
        NEGATIVE
    }

    public EmailTemplateRenderingService() {
        _jadeConfig = new JadeConfiguration();
        _jadeConfig.setTemplateLoader(new ClasspathTemplateLoader());
        _jadeConfig.setPrettyPrint(true);

        try {
            _positiveTemplate = _jadeConfig.getTemplate(POSITIVE_TEMPLATE_NAME);
            _negativeTemplate = _jadeConfig.getTemplate(NEGATIVE_TEMPLATE_NAME);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    public String render(Template template, Map<String, Object> model) {
        JadeTemplate jadeTemplate = null;
        switch (template) {
            case POSITIVE:
                jadeTemplate = _positiveTemplate;
                break;
            case NEGATIVE:
                jadeTemplate = _negativeTemplate;
                break;
            default:
                log.severe("Invalid email template: " + template);
        }
        return _jadeConfig.renderTemplate(jadeTemplate, model);
    }
}
