package com.elegant.training.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class,
        property = {"process.label=Practice Custom Workflow Process" })
public class CustomWorkflowProcess implements WorkflowProcess {
    protected final Logger LOGGER = LoggerFactory.getLogger(CustomWorkflowProcess.class);

    public void execute(WorkItem workItem, WorkflowSession wfSession,
                        MetaDataMap metaDataMap) throws WorkflowException {
        String payloadType = workItem.getWorkflowData().getPayloadType();

        if (StringUtils.equals(payloadType, "JCR_PATH")) {
            LOGGER.error("Payload type: {}", payloadType);

            String path = workItem.getWorkflowData().getPayload().toString();
            LOGGER.error("Payload path: {}", path);
        }

        String args = metaDataMap.get("PROCESS_ARGS", String.class);
        LOGGER.error("Process args: {}", args);
    }
}
