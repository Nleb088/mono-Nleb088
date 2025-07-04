package com.interco.domain.ports.usecases;

import java.util.UUID;

public interface DeleteTask {
    public void execute(UUID id);
}
