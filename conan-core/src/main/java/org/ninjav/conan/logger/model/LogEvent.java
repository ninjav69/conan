package org.ninjav.conan.logger.model;

import org.ninjav.conan.logger.Logger;

import java.util.Date;

/**
 *
 * @author ninjav
 */
public class LogEvent {

    private Logger.LogLevel level;
    private Date occurredAt = new Date();
    private String payload;

    public Logger.LogLevel getLevel() {
        return level;
    }

    public void setLevel(Logger.LogLevel level) {
        this.level = level;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
