package com.elegant.training.core.workflows.process;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.elegant.training.core.services.ResourceResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Objects;

import static com.day.cq.wcm.api.NameConstants.NT_PAGE;
@Component(
        service = WorkflowProcess.class,
        property = {
                "process.label" + "=" + "Fetch Children Pages"
        }
)
public class FetchChildrenPagesStep implements WorkflowProcess {
    private static final String TAG = FetchChildrenPagesStep.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchChildrenPagesStep.class);

    @Reference
    ResourceResolverService resourceResolverService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        // Get the payload path of the page
        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        // Get resource resolver object
        ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
        // Get resource corresponding to the given payload path
        Resource resource = resourceResolver.getResource(payloadPath);
        // Check if the type of resource is of a page
        if (Objects.requireNonNull(resource).getResourceType().equals(NT_PAGE)) {
            // Get the reference of the Page Manager class
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            // Get the reference of the Page
            Page currentPage = Objects.requireNonNull(pageManager).getPage(payloadPath);
            LOGGER.debug("{}: fetching count of children of page: {}", TAG, payloadPath);
            // Child page count
            int childPageCount = getChildrenPagesCount(currentPage, 0);
            LOGGER.debug("{}: total children pages: {}", TAG, childPageCount);
            // Set this value in the metadata map
            workItem.getWorkflow().getMetaDataMap().put("childPageCount", childPageCount);
        }
    }

    private int getChildrenPagesCount(Page page, int count) {
        // Get the iterator which contains children of page
        Iterator<Page> pageIterator = page.listChildren();
        // Check if the Iterator has values
        while (pageIterator.hasNext()) {
            // Get the current page in the iterator
            final Page child = pageIterator.next();
            // Check if the current child also has children
            if (child.listChildren() != null) {
                count += getChildrenPagesCount(child, count);
            }
            count++;
        }
        return count;
    }
}
