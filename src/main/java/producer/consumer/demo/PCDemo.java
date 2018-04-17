package producer.consumer.demo;

import java.util.LinkedList;
import java.util.Queue;

public class PCDemo {

    private static final String USAGE_MESSAGE =
        "Usage: java -jar producer-consumer-demo.jar [CONSUMER_THREAD_COUNT] [PRODUCER_PERIOD_IN_MS]";

    public static void main(String[] args) {
        validateInputs(args);

        int consumerThreadCount = parseIntArg(args[0]);
        int producerPeriod = parseIntArg(args[1]);

        countdown(consumerThreadCount, producerPeriod);

        Queue<Integer> queue = new LinkedList<>();

        IntConsumerThreadPool intConsumerThreadPool = new IntConsumerThreadPool(queue, consumerThreadCount);
        Thread producerThread = new Thread(new IntProducerRandom(queue, producerPeriod), "Producer-Thread-0");

        intConsumerThreadPool.start();
        producerThread.start();
    }

    private static void countdown(int consumerThreadCount, int producerPeriod) {
        System.out.println("Starting producer-consumer-demo with: " + consumerThreadCount + " CONSUMERS, " + producerPeriod
            + " PRODUCER PERIOD (MS)");
        for (int i = 5; i >= 0; --i) {
            try {
                Thread.sleep(1000L);
                System.out.println(i + "...");
            } catch (InterruptedException e) {
            }
        }
    }

    private static void validateInputs(String[] args) {
        if (args.length != 2) {
            System.out.println(USAGE_MESSAGE);
            System.exit(0);
        }
    }

    private static Integer parseIntArg(String arg) {
        Integer integer = null;
        try {
            integer = Integer.parseInt(arg);
            if (integer < 0) {
                exitOnInvalidInput();
            }
        } catch (NumberFormatException e) {
            exitOnInvalidInput();
        }
        return integer;
    }

    private static void exitOnInvalidInput() {
        System.out.println(USAGE_MESSAGE);
        System.exit(0);
    }

}
