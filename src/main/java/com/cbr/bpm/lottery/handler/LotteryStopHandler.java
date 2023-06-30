package com.cbr.bpm.lottery.handler;

import com.cbr.bpm.lottery.constant.BpmnErrorConstant;
import com.cbr.bpm.lottery.service.LotteryService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.context.annotation.Configuration;

@Configuration
@ExternalTaskSubscription("stop-register")
public class LotteryStopHandler implements ExternalTaskHandler{

    private final LotteryService lotteryService;

    public LotteryStopHandler(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            lotteryService.stop();
            externalTaskService.complete(externalTask);
        } catch (Exception e) {
            e.printStackTrace();
            externalTaskService.handleBpmnError(externalTask, BpmnErrorConstant.EXTERNAL_TASK_ERROR);
        }
    }
}
