package com.elegant.training.core.workflows.participant;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = ParticipantStepChooser.class,
        property = {
                "chooser.label" + "=" + "Approve Asset Metadata"
        }
)
public class ApprovePageContentStep implements ParticipantStepChooser {
    private static final String TAG = ApprovePageContentStep.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(ApprovePageContentStep.class);

    private static final String CONTENT_PATH = "/content/Elegant";

    @Override
    public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        // Getting payload from Workflow - workItem -> workflowData -> payload
        String payloadType = workItem.getWorkflowData().getPayloadType();
        LOGGER.debug("{}: Payload type: {}", TAG, payloadType);
        // Check type of payload; there are two - JCR_PATH and JCR_UUID
        if (StringUtils.equals(payloadType, "JCR_PATH")) {
            // Get the JCR path from the payload
            String path = workItem.getWorkflowData().getPayload().toString();
            LOGGER.debug("{}: Payload path: {}", TAG, path);
            // Get process arguments which will contain the properties to update
            if (path.startsWith(CONTENT_PATH)) {
                LOGGER.debug("{}: selected user/group: {}", TAG, "content-authors");
                return "content-authors";
            }
        }
        return "administrators";
    }
}
