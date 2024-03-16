package com.elegant.training.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.ServletResolverConstants;
import javax.servlet.Servlet;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;

import javax.servlet.ServletException;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=List all sites Servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/listSites"
        })
public class SitesListingServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SitesListingServlet.class);
    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource contentResource = resourceResolver.getResource("/content");

        JsonArray sitesArray = new JsonArray();

        if (contentResource != null) {
            Iterable<Resource> siteResources = contentResource.getChildren();
            for (Resource siteResource : siteResources) {
                JsonObject siteObject = new JsonObject();
                siteObject.addProperty("path", siteResource.getPath());
                siteObject.addProperty("name", siteResource.getName());
                sitesArray.add(siteObject);
            }
        }

        response.getWriter().print(sitesArray.toString());
    }
}
