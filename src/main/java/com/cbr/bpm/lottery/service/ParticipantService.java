package com.cbr.bpm.lottery.service;

import com.cbr.bpm.lottery.model.Participant;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ParticipantService {
    List<Participant> get();
    List<Participant> numerate(List<Participant> participants) throws JsonProcessingException;
}
