/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.utils;

public class TimerUtils {
    private long lastMS;

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        if ((double)(this.getCurrentMS() - this.lastMS) >= milliseconds) {
            return true;
        }
        return false;
    }

    /**
     * Devides 1000 / d
     */
    public int convertToMS(final int d) {
        return 1000 / d;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float milliSec) {return ((this.getCurrentMS() - this.lastMS) >= milliSec);}


    public double getLastDelay () {
        return getCurrentMS() - getLastMS();
    }

    public long getLastMS() {
        return lastMS;
    }

    public final long getDifference() {
        return getCurrentMS() - lastMS;
    }

    public final boolean hasPassed(long milliseconds) {
        return getCurrentMS() -lastMS > milliseconds;
    }


    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset) reset();
            return true;
        }

        return false;
    }

    public boolean hasTimeElapsed(long time) {
        return System.currentTimeMillis() - lastMS > time;
    }

    public void setTime(long time) {
        lastMS = time;
    }
}

