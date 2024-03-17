package com.elegant.training.core.services.impl;

import com.elegant.training.core.configs.CardConfiguration;
import com.elegant.training.core.services.CardService;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = CardService.class,
        immediate = true,
        property = {
                Constants.SERVICE_ID + "= Card Service",
                Constants.SERVICE_DESCRIPTION + "=This service reads values from card configurations"
        }
)
@Designate(ocd = CardConfiguration.class)
public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);
    private static final  String TAG = CardServiceImpl.class.getSimpleName();

    private CardConfiguration configs;

    @Activate
    protected void activate(CardConfiguration configuration){
        this.configs = configuration;
    }

    @Override
    public String getOrganizationName() {
        LOGGER.info("{}: reading organization name", TAG);
        return configs.getOrganizationName();
    }

    @Override
    public String getHomepageURL() {
        LOGGER.info("{}: reading organization url", TAG);
        return configs.getHomepageURL();
    }

}
