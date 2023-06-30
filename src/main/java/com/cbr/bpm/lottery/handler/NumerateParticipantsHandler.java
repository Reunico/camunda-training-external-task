package com.cbr.bpm.lottery.handler;

import com.cbr.bpm.lottery.constant.BpmnErrorConstant;
import com.cbr.bpm.lottery.constant.ProcessVariableConstant;
import com.cbr.bpm.lottery.model.Participant;
import com.cbr.bpm.lottery.service.ParticipantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
@ExternalTaskSubscription("numerate-participants")
public class NumerateParticipantsHandler implements ExternalTaskHandler{

    private final ParticipantService participantService;

    public NumerateParticipantsHandler(ParticipantService participantService) {
        this.participantService = participantService;
    }


    @SneakyThrows
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            List<Participant> participants = externalTask.getVariable(ProcessVariableConstant.PARTICIPANTS);
            participants = participantService.numerate(participants);
            HashMap<String, Object> variableMap = new HashMap<>();
            variableMap.put("participants", participants);
            externalTaskService.complete(externalTask, variableMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            externalTaskService.handleBpmnError(externalTask, BpmnErrorConstant.EXTERNAL_TASK_ERROR);
        }
    }
}
