package cws.core.scheduler;

import cws.core.VM;
import cws.core.WorkflowEngine;
import cws.core.core.VMType;
import cws.core.jobs.Job;

/**
 * Workflow admissioner that always admissions workflows.
 */
public final class VoidWorkflowAdmissioner implements WorkflowAdmissioner {

    @Override
    public boolean isJobDagAdmitted(Job job, WorkflowEngine engine, VM vm) {
        return true;
    }

    @Override
    public VMType getVmType() {
        return null;
    }
}
