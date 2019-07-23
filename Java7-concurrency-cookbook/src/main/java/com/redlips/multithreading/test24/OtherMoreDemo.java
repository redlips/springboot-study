package com.redlips.multithreading.test24;

import java.util.concurrent.*;

/**
 * @author qiaotong
 * @create 2019-07-18 16:01
 * @description
 * 既然CompletableFuture类实现了CompletionStage接口，首先我们需要理解这个接口的契约。它代表了一个特定的计算的阶段，可以同步或者异步的被完成。你可以把它看成一个计算流水线上的一个单元，最终会产生一个最终结果，这意味着几个CompletionStage可以串联起来，一个完成的阶段可以触发下一阶段的执行，接着触发下一次，接着……
 *
 * 除了实现CompletionStage接口， CompletableFuture也实现了future接口, 代表一个未完成的异步事件。CompletableFuture提供了方法，能够显式地完成这个future,所以它叫CompletableFuture。
 */
public class OtherMoreDemo {


    public static void completedFutureExample() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.get());
        System.out.println(cf.isDone());
        System.out.println("message".equals(cf.getNow(null)));
    }

    public static void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("线程" + getCurrentName() + "是否是守护线程：" + Thread.currentThread().isDaemon());
            sleep(1);
        });

        System.out.println("线程" + getCurrentName() + cf.isDone());
        sleep(2);

        System.out.println("线程" + getCurrentName() + cf.isDone());
    }


    /**
     * 在前一个阶段上应用函数：
     * 注意thenApply方法名称代表的行为。
     * then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
     * Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
     * 函数的执行会被阻塞，这意味着getNow()只有大写操作被完成后才返回。
     */
    public static void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            System.out.println("线程" + getCurrentName() + "是否是守护线程：" + Thread.currentThread().isDaemon());
            System.out.println("线程" + getCurrentName() + "处理任务 将message变为");
            sleep(2);
            return s.toUpperCase();
        });

        System.out.println("MESSAGE".equals(cf.getNow(null)));
        System.out.println("xxx");
    }

    /**
     * 前一阶段异步执行应用函数：
     * 通过调用异步方法(方法后边加Async后缀)，串联起来的CompletableFuture可以异步地执行（使用ForkJoinPool.commonPool()）。
     */
    public static void thenApplyAsyncExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            System.out.println("线程" + getCurrentName() + "是否是守护线程：" + Thread.currentThread().isDaemon());
            System.out.println("线程" + getCurrentName() + "处理任务 将message变为");
            sleep(2);
            return s.toUpperCase();
        });

        System.out.println("MESSAGE".equals(cf.getNow(null)));
    }

    static ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "customer-executor-" + count++);
        }
    });

    /**
     * 使用定制的Executor在前一个阶段上异步应用函数
     */
    public static void thenApplyAsyncWithExecutorExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            System.out.println("线程" + getCurrentName() + "是否是守护线程：" + Thread.currentThread().isDaemon());
            sleep(2);
            return s.toUpperCase();
        }, executorService);

        System.out.println("MESSAGE".equals(cf.getNow(null)));
        System.out.println("线程" + getCurrentName() + "比较结果:" + "MESSAGE".equals(cf.join()));
    }

    /**
     * 消费前一阶段的结果：
     * 如果下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void)，
     * 那么它可以不应用一个函数，而是一个消费者， 调用方法也变成了thenAccept:
     * 本例中消费者同步地执行，所以我们不需要在CompletableFuture调用join方法。
     */
    public static void thenAcceptExample() {
        StringBuilder sb = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("then Accept message")
                .thenAccept(sb::append);

        System.out.println("Result is Empty : " + (sb.length() > 0));
    }

    /**
     * 异步消费前一阶段的结果：
     * 同样，可以使用thenAcceptAsync方法， 串联的CompletableFuture可以异步地执行。
     */
    public static void thenAcceptAsyncExample() {
        StringBuilder sb = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("then Accept Async message")
                .thenAcceptAsync(s -> {
                    System.out.println("线程" + getCurrentName() + "是否是守护线程：" + Thread.currentThread().isDaemon());
                    sb.append(s);
                });
        cf.join();
        System.out.println("Result is Empty : " + (sb.length() > 0));
    }

    public static void completeExceptionallyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync(s -> {
                    System.out.println("线程" + getCurrentName() + "延迟执行异步任务");
                    sleep(2);
                    return s.toUpperCase();
                });

        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> {
            return (th != null) ? "message upon cancel" : "xx";
        });

        cf.completeExceptionally(new RuntimeException("completed exception"));

        System.out.println("线程" + getCurrentName() + "执行，异步任务是否异常" + cf.isCompletedExceptionally());

        try {
            System.out.println(exceptionHandler.join());
            System.out.println(cf.join());
        } catch (CompletionException e) {
            System.out.println("线程" + getCurrentName() + "出现异常" + e.getCause().getMessage());
        }

    }

    public static void testException() {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 7/0)
                .thenApply(r -> r * 10)
                .exceptionally(e -> 0); // 链式异常处理， 相当于catch， 下面的catch不再生效
        // handle类似try{}catch{}finally{}中的finally,总会执行
        CompletableFuture<String> handle = cf.handle((s, sh) -> (s == null) ? "异常为0" : "xxx");
        // completeExceptionally 如果上面没对异常进行处理，直接抛出异常
        cf.completeExceptionally(new RuntimeException("抛出异常"));

        try {
            System.out.println(handle.join());
            System.out.println(cf.join());
        } catch (CompletionException e) {
            System.out.println("线程" + getCurrentName() + "出现异常" + e.getCause().getMessage());
        }
    }

    /**
     * 取消计算:
     * 和完成异常类似，我们可以调用cancel(boolean mayInterruptIfRunning)取消计算。对于CompletableFuture类，布尔参数并没有被使用，
     * 这是因为它并没有使用中断去取消操作，相反，cancel等价于completeExceptionally(new CancellationException())。
     */
    public static void cancelExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync(var0 -> {
                    sleep(2);
                    return var0.toUpperCase();
                });

        CompletableFuture<String> cf2 = cf.exceptionally(able -> "cancel message");

        System.out.println("取消计算" + cf.cancel(true));
        System.out.println("是否取消了计算" + cf.isCompletedExceptionally());
        System.out.println(cf.join());
        System.out.println(cf2.join());
    }

    /**
     * 在两个完成的阶段其中之一上应用函数:
     * 下面的例子创建了CompletableFuture, applyToEither处理两个阶段， 在其中之一上应用函数(包保证哪一个被执行)。
     * 本例中的两个阶段一个是应用大写转换在原始的字符串上， 另一个阶段是应用小些转换。
     */
    public static void applyToEitherExample() {
        String original = "Message";

        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s));
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original)
                        .thenApplyAsync(OtherMoreDemo::delayedLowCase),
                s -> s + "from applyToEither");

        System.out.println(cf2.join());
    }

    /**
     * 在两个完成的阶段其中之一上调用消费函数：
     * 和前一个例子很类似了，只不过我们调用的是消费者函数 (Function变成Consumer)
     */
    public static void acceptEitherExample() {
        String original = "Message";
        StringBuilder sb = new StringBuilder();
        CompletableFuture cf1 = CompletableFuture
                .completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                .acceptEither(CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedLowCase(s)), s -> sb.append(s).append("acceptEither"));

        System.out.println(cf1.join());
        System.out.println(sb.toString());
    }

    /**
     * 在两个阶段都执行完后运行一个 Runnable：
     * 这个例子演示了依赖的CompletableFuture如果等待两个阶段完成后执行了一个Runnable。
     * 注意下面所有的阶段都是同步执行的，第一个阶段执行大写转换，第二个阶段执行小写转换。
     */
    static void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        System.out.println("Result was empty " + (result.length() > 0));
        System.out.println(result);
    }

    /**
     * 使用BiConsumer处理两个阶段的结果
     */
    public static void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder sb = new StringBuilder();

        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
                .thenAcceptBoth(CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                        (s1, s2) -> sb.append(s1).append(s2));

        System.out.println(sb.toString());
    }


    private static String delayedLowCase(String s) {
        sleep(1);
        return s.toLowerCase();
    }

    private static String delayedUpperCase(String s) {
        sleep(1);
        return s.toUpperCase();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        thenApplyExample();
    }
    private static void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentName() {
        return Thread.currentThread().getName() + "::";
    }
}
