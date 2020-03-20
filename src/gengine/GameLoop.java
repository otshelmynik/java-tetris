package gengine;

class GameLoop implements Runnable {
    public static final long NANO_IN_SECOND = 1_000_000_000;
    private Thread thread;

    private float fps = 60.0f;
    private float secondsPerFrame = (float)NANO_IN_SECOND / fps;
    private boolean running = false;

    private float realFps;

    public float getRealFps() {
        return realFps;
    }

    public GameLoop() {
        thread = new Thread(this);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void update(float dt) {}

    void render() {}

    @Override
    public void run() {
        float dt = 0.0f; // delta time
        long lastTime = System.nanoTime();
        long nowTime;

        // for any calculation to second
        float calcFps = 0.0f; // for fps calculation
        long elapsedTime = 0;

        while (running) {
            nowTime = System.nanoTime();
            elapsedTime += (nowTime - lastTime);
            dt += (nowTime - lastTime) / secondsPerFrame;
            lastTime = nowTime;

            while (dt > 1) {
                update(dt);
                render();
                dt--;
                calcFps++;
            }

            if (elapsedTime >= NANO_IN_SECOND) {
                realFps = calcFps;
                calcFps = 0;
                elapsedTime -= NANO_IN_SECOND;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

