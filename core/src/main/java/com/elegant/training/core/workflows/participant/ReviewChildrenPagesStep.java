package com.elegant.training.core.workflows.participant;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = ParticipantStepChooser.class,
        property = {
                "chooser.label" + "=" + "Review Children Pages"
        }
)
public class ReviewChildrenPagesStep implements ParticipantStepChooser {
    private static final String TAG = ReviewChildrenPagesStep.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewChildrenPagesStep.class);

    @Override
    public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        // Get the count of the children pages in the workflow
        int childPagesCount = workItem.getWorkflow().getMetaDataMap().get("childPageCount", 0);
        LOGGER.debug("{}: child pages count: {}", TAG, childPagesCount);
        // Return the user group based on the number of child pages
        return childPagesCount > 0 ? "administrators" : "content-authors";
    }
}
