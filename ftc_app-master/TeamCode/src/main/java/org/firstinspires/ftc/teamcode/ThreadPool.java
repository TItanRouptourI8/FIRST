package org.firstinspires.ftc.teamcode;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool implements Runnable {

    public boolean killed;
    ExecutorService executorService;
    MovesHandler movesHandler;
    AscenseurHandler ascenseurHandler;

    public ThreadPool(MovesHandler movesHandler, AscenseurHandler ascenseurHandler) {
        Thread thread = new Thread(this);
        this.ascenseurHandler = ascenseurHandler;
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.movesHandler = movesHandler;
        thread.start();

    }

    @Override
    public void run() {
        while (!killed)
        {
        executorService.execute(movesHandler);
        executorService.execute(ascenseurHandler);
        }
    }
}
