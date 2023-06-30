package com.cbr.bpm.lottery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    private String name;
    private Instant createdDate;
    private String chatId;
    private Long number;
}
