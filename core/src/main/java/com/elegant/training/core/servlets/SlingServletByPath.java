package com.elegant.training.core.servlets;

import org.apache.sling.api.SlingException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        immediate = true,
        property = {
                Constants.SERVICE_DESCRIPTION + "= Custom Servlet By Path",
                "sling.servlet.methods="+ HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/practice"
        }
)
public class SlingServletByPath extends SlingAllMethodsServlet {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Reference
    private JobManager jobManager;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException, SlingException {
        final Map<String, Object> props = new HashMap<>();
        props.put("data", "test");
        jobManager.addJob("practice/job", props);

        LOGGER.error("PRACTICE SERVLET CALLED");

        resp.setStatus(SlingHttpServletResponse.SC_OK);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().print("{\"Response Message \" : \"Servlet Called\"}");
    }

}
