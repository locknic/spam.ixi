package com.locknic.ixi;

import org.iota.ict.ixi.context.ConfigurableIxiContext;
import org.json.JSONObject;

public class SpamIxiContext extends ConfigurableIxiContext {

    private static final String FIELD_INTERVAL_MS = "interval_ms";

    private static final int DEFAULT_INTERVAL_MS = 10000;

    private int intervalMs = DEFAULT_INTERVAL_MS;

    public SpamIxiContext() {
        super(new JSONObject()
                .put(FIELD_INTERVAL_MS, DEFAULT_INTERVAL_MS));
    }

    public int getIntervalMs() {
        return intervalMs;
    }

    @Override
    protected void validateConfiguration(JSONObject newConfiguration) {
        validateIntervalMs(newConfiguration);
    }

    private void validateIntervalMs(JSONObject newConfiguration) {
        if(!newConfiguration.has(FIELD_INTERVAL_MS)) {
            throw new IllegalPropertyException(FIELD_INTERVAL_MS, "not defined (please enter a number)");
        }
        if(!(newConfiguration.get(FIELD_INTERVAL_MS) instanceof String)) {
            throw new IllegalPropertyException(FIELD_INTERVAL_MS, "not a valid interval (a number representing milliseconds)");
        }
        String intervalMsString = newConfiguration.getString(FIELD_INTERVAL_MS);
        if (intervalMsString.length() < 1) {
            throw new IllegalPropertyException(FIELD_INTERVAL_MS, "not defined (please enter a number)");
        }

        boolean force = false;
        if (intervalMsString.substring(intervalMsString.length() - 1).equals("f")) {
            force = true;
            intervalMsString = intervalMsString.substring(0, intervalMsString.length() - 1);
        }

        int interval = 0;
        try {
            interval = Integer.parseInt(intervalMsString);
        } catch (NumberFormatException e) {
            throw new IllegalPropertyException(FIELD_INTERVAL_MS, "not a valid interval (please enter a number)");
        }
        if (interval < 0) {
            throw new IllegalPropertyException(FIELD_INTERVAL_MS, "not a valid interval (please enter a number greater than 0)");
        }
        if (!force && interval < 1000) {
            throw new IllegalPropertyException(FIELD_INTERVAL_MS, "interval under 1000ms is unsafe, add 'f' to the end of your interval to force it (e.g. 100f)");
        }
    }

    private class IllegalPropertyException extends IllegalArgumentException {
        private IllegalPropertyException(String field, String cause) {
            super("Invalid property '" + field + "': " + cause + ".");
        }
    }

    @Override
    protected void applyConfiguration() {
        applyIntervalMs(configuration.getString(FIELD_INTERVAL_MS));
    }

    private void applyIntervalMs(String intervalMsString) {
        if (intervalMsString.substring(intervalMsString.length() - 1).equals("f")) {
            intervalMsString = intervalMsString.substring(0, intervalMsString.length() - 1);
        }

        intervalMs = Integer.parseInt(intervalMsString);
    }
}
