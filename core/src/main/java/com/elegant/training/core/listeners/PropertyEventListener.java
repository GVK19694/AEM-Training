package com.elegant.training.core.listeners;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.elegant.training.core.services.ResourceResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import java.util.Objects;

@Component(service = EventListener.class, immediate = true)
public class PropertyEventListener implements EventListener {

    private static final String TAG = PropertyEventListener.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyEventListener.class);

    @Reference
    ResourceResolverService resourceResolverService;

    @Activate
    protected void activate() {
        try {
            // Getting the resource resolver
            ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
            // Adapting the Resource Resolver to Session
            Session session = resourceResolver.adaptTo(Session.class);
            // Adding the event listener
            Objects.requireNonNull(session).
                    getWorkspace().
                    getObservationManager().
                    addEventListener(
                            this, Event.PROPERTY_ADDED | Event.NODE_ADDED,
                            "/content/Elegant/us/en", true, null,
                            null, false
                    );
        } catch (RepositoryException e) {
            LOGGER.error("{}: exception occurred: {}", TAG, e.getMessage());
        }
    }

    @Override
    public void onEvent(EventIterator events) {
        try {
            // Loop through all the events
            while (events.hasNext()) {
                // Get the current event
                Event event = events.nextEvent();
                LOGGER.info("{}:  : {} at path: {}", TAG, event.getUserID(), event.getPath());
            }
        } catch (RepositoryException e) {
            LOGGER.error("{}: exception occurred: {}: ", TAG, e.getMessage());
        }
    }
}
