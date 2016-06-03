/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.logger;

import org.ninjav.conan.logger.model.LogEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Monostate - our first proud implementation
 *
 * @author ninjav
 */
public class LogEventUseCase implements LogEventPort {

    private static final List<LoggerListener> listeners = new ArrayList<>();

    @Override
    public void addListener(LoggerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(LoggerListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(LogEvent event) {
        listeners.stream().forEach((l) -> {
            l.eventLogged(createPresentableLogEvent(event));
        });
    }

    private PresentableLogEvent createPresentableLogEvent(LogEvent event) {
        PresentableLogEvent p = new PresentableLogEvent();
        p.level = event.getLevel();
        p.date = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss").format(event.getOccurredAt());
        p.line = event.getPayload();
        return p;
    }

    @Override
    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }

    @Override
    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    @Override
    public void logSuccess(String message) {
        log(LogLevel.SUCCESS, message);
    }

    @Override
    public void logDefault(String message) {
        log(LogLevel.INFO, message);
    }

    @Override
    public void log(LogLevel level, String message) {
        LogEvent event = createLogEvent(level, message);
        notifyListeners(event);
    }
    
    private LogEvent createLogEvent(Logger.LogLevel level, String text) {
        LogEvent event = new LogEvent();
        event.setLevel(level);
        event.setOccurredAt(new Date());
        event.setPayload(text);
        return event;
    }
}
