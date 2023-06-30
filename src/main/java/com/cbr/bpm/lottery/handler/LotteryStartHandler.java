package com.cbr.bpm.lottery.handler;

import com.cbr.bpm.lottery.constant.BpmnErrorConstant;
import com.cbr.bpm.lottery.service.LotteryService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.context.annotation.Configuration;

@Configuration
@ExternalTaskSubscription("open-register")
public class LotteryStartHandler implements ExternalTaskHandler{

    private final LotteryService lotteryService;

    public LotteryStartHandler(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            lotteryService.start();
            externalTaskService.complete(externalTask);
        } catch (Exception e) {
            externalTaskService.handleBpmnError(externalTask, BpmnErrorConstant.EXTERNAL_TASK_ERROR);
        }
    }
}
