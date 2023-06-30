package com.cbr.bpm.lottery.handler;

import com.cbr.bpm.lottery.constant.BpmnErrorConstant;
import com.cbr.bpm.lottery.constant.ProcessVariableConstant;
import com.cbr.bpm.lottery.model.Participant;
import com.cbr.bpm.lottery.service.ParticipantService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
@ExternalTaskSubscription("get-participants")
public class GetParticipantsHandler implements ExternalTaskHandler{

    private final ParticipantService participantService;

    public GetParticipantsHandler(ParticipantService participantService) {
        this.participantService = participantService;
    }


    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            List<Participant> participants = participantService.get();
            HashMap<String, Object> variableMap = new HashMap<>();
            variableMap.put(ProcessVariableConstant.PARTICIPANTS, participants);
            externalTaskService.complete(externalTask, variableMap);
        } catch (Exception e) {
            externalTaskService.handleBpmnError(externalTask, BpmnErrorConstant.EXTERNAL_TASK_ERROR);
        }
    }
}
