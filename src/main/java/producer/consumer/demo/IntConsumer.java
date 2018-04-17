package producer.consumer.demo;

import java.util.Queue;

class IntConsumer implements Runnable {

    private final Queue<Integer> queue;

    public IntConsumer(Queue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");

        while (true) {
            synchronized (queue) {
                try {
                    while (queue.isEmpty()) {
                        queue.wait();   // Queue is empty; await notification
                    }

                    Integer integer = queue.remove();
                    System.out.println(Thread.currentThread().getName() + " dequeued: " + integer);
                    queue.notifyAll();  // Notify other threads before releasing monitor (and potentially the CPU)
                } catch (InterruptedException e) {
                    // Notification or other interrupt event
                }
            }   // Release the queue's monitor
        }
        // In cases where the thread doesn't go on forever, we'd empty the queue and call join().
    }

}
