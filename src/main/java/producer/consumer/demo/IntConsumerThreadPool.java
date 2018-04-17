package producer.consumer.demo;

import java.util.Queue;

class IntConsumerThreadPool {

    private final Thread[] consumerThreads;

    public IntConsumerThreadPool(Queue<Integer> queue, int numThreads) {
        this.consumerThreads = new Thread[numThreads];

        for (int i = 0; i < numThreads; ++i) {
            consumerThreads[i] = new Thread(new IntConsumer(queue), "Consumer-Thread-" + i);
        }
    }

    public void start() {
        for (Thread thread : consumerThreads) {
            thread.start();
        }
    }

}
