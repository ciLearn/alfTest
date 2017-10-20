package medads.platformsample.services;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNode;

public class TestService implements TaskListener {

    @Override
    public void notify(DelegateTask task) {
        ActivitiScriptNode doctor = (ActivitiScriptNode) task.getVariable("mad_doctor");
        Object complaints = task.getVariable("mad_complaints");
        Object datetime = task.getVariable("mad_datetime");
        task.getExecution().setVariable("mad_complaints",complaints);
        task.getExecution().setVariable("mad_datetime",datetime);

        if(!doctor.hasAspect("{http://www.medads.com/model/content/1.0}appoint"))
        doctor.addAspect("{http://www.medads.com/model/content/1.0}appoint");

        doctor.getProperties().put("{http://www.medads.com/model/content/1.0}appDate",datetime);
        doctor.save();
        task.getExecution().setVariable("mad_doctor", doctor);
        if(task.getName().equals("Request correction"))
        {
            task.getExecution().setVariable("mad_corrOutcome", task.getVariable("mad_corrOutcome"));
        }
    }
}
