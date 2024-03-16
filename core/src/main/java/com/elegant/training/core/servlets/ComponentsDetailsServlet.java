package com.elegant.training.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import javax.servlet.Servlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Components Details Servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/components/details"
        })
public class ComponentsDetailsServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentsDetailsServlet.class);
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver.getResource("/apps/Elegant/components");
        Node node = resource.adaptTo(Node.class);
        ArrayList<Map<String, String>> components = new ArrayList<>();

        try {
            NodeIterator nodes = node.getNodes();
            while (nodes.hasNext()) {
                Node componentNode = nodes.nextNode();
                Map<String, String> componentDetails = new HashMap<>();
                componentDetails.put("name", componentNode.getName());
                componentDetails.put("path", componentNode.getPath());
                components.add(componentDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(components);
        response.getWriter().write(jsonResponse);
    }
}
