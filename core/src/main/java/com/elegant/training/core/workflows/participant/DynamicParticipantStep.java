package com.elegant.training.core.workflows.participant;

import com.adobe.granite.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.ParticipantStepChooser;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.Workflow;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = ParticipantStepChooser.class,
        property = { "chooser.label="+"Dynamic Participant Workflow" }
)
public class DynamicParticipantStep implements ParticipantStepChooser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicParticipantStep.class);
    @Override
    public String getParticipant(WorkItem wi, WorkflowSession ws, MetaDataMap mdm) {
        LOGGER.info("Dynamic Participant Step was running");
        Workflow wf = wi.getWorkflow();

        return "asining-ownership";
    }
}
