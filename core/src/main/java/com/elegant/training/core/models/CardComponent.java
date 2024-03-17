package com.elegant.training.core.models;

import com.elegant.training.core.services.CardService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class CardComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardComponent.class);
    private static final String TAG = CardComponent.class.getSimpleName();

    @Inject
    String cardTitle;

    @Inject
    String buttonText;

    @OSGiService
    CardService cardService;

    private String organizationName;

    private String homepageURL;

    @PostConstruct
    protected void init() {
        organizationName = cardService.getOrganizationName();
        homepageURL = cardService.getHomepageURL();
        LOGGER.info("{}: organization name: {}", TAG, organizationName);
        LOGGER.info("{}: homepage url: {}", TAG, homepageURL);
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getHomepageURL() {
        return homepageURL;
    }
}
