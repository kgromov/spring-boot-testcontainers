package org.kgromov.migrations;

import org.flywaydb.core.api.callback.BaseCallback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogCallback extends BaseCallback {
    private static final Logger log = LoggerFactory.getLogger(LogCallback.class);

    @Override
    public void handle(Event event, Context context) {
        log.info("Event: {}", event);
    }
}
