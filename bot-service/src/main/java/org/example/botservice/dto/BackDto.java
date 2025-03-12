package org.example.botservice.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public class BackDto  {
    private UUID callback;

    private UUID getCallback(){
        return callback;
    }

}
