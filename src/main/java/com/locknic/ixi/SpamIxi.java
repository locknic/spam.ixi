package com.locknic.ixi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iota.ict.ixi.Ixi;
import org.iota.ict.ixi.IxiModule;
import org.iota.ict.ixi.context.IxiContext;
import org.iota.ict.model.transaction.Transaction;
import org.iota.ict.model.transaction.TransactionBuilder;
import org.iota.ict.network.gossip.GossipEvent;
import org.iota.ict.network.gossip.GossipListener;

import java.util.Random;

public class SpamIxi extends IxiModule {

    private static final Logger LOGGER = LogManager.getLogger("Spammer");
    private final SpamIxiContext context = new SpamIxiContext();

    public SpamIxi(Ixi ixi) {
        super(ixi);
    }

    public void run() {
        Random rand = new Random();
        while(isRunning()) {
            // Submit and print a random spam transaction
            String message = System.currentTimeMillis() + "spam" + rand.nextInt();
            TransactionBuilder builder = new TransactionBuilder();
            builder.asciiMessage(message);
            Transaction toSubmit = builder.build();
            ixi.submit(toSubmit);
            LOGGER.info("Sent random spam: " + message);

            // Sleep for configured seconds
            try {
                int intervalMs = context.getIntervalMs();
                if (intervalMs > 0) {
                    Thread.sleep(intervalMs);
                }
            } catch (InterruptedException e) {
                LOGGER.error("SpamModule encountered an error.", e);
            }
        }
    }

    @Override
    public IxiContext getContext() {
        return context;
    }
}

