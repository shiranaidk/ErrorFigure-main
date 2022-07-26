//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package errorfigure.utils;

public final class AnimationUtils {
    private static float defaultSpeed = 0.125F;
    private static TimerUtils timerUtil = new TimerUtils();

    public AnimationUtils() {
    }

    public static float calculateCompensation(float target, float current, long delta, double speed) {
        float diff = current - target;
        if (delta < 1L) {
            delta = 1L;
        }

        if (delta > 1000L) {
            delta = 16L;
        }

        double xD;
        if ((double)diff > speed) {
            xD = speed * (double)delta / 16.0 < 0.5 ? 0.5 : speed * (double)delta / 16.0;
            if ((current = (float)((double)current - xD)) < target) {
                current = target;
            }
        } else if ((double)diff < -speed) {
            xD = speed * (double)delta / 16.0 < 0.5 ? 0.5 : speed * (double)delta / 16.0;
            if ((current = (float)((double)current + xD)) > target) {
                current = target;
            }
        } else {
            current = target;
        }

        return current;
    }

    public static float getAnimationState(float animation, float finalState, float speed) {
        float add = 8f * (speed / 1000.0F);
        if (animation < finalState) {
            if (animation + add < finalState) {
                animation += add;
            } else {
                animation = finalState;
            }
        } else if (animation - add > finalState) {
            animation -= add;
        } else {
            animation = finalState;
        }
        return animation;
    }

    public static float smoothAnimation(float ani, float finalState, float speed, float scale) {
        return getAnimationState(ani, finalState, Math.max(10.0F, Math.abs(ani - finalState) * speed) * scale);
    }

    public static double easing(double now, double target, double speed) {
        return Math.abs(target - now) * speed;
    }

    public static float mvoeUD(float current, float end, float minSpeed) {
        return moveUD(current, end, defaultSpeed, minSpeed);
    }

    public static double animate(double target, double current, double speed) {
        if (timerUtil.delay(4.0F)) {
            boolean larger = target > current;
            if (speed < 0.0) {
                speed = 0.0;
            } else if (speed > 1.0) {
                speed = 1.0;
            }

            double dif = Math.max(target, current) - Math.min(target, current);
            double factor = dif * speed;
            if (factor < 0.1) {
                factor = 0.1;
            }

            current = larger ? current + factor : current - factor;
            timerUtil.reset();
        }

        return current;
    }

    public float animate(float target, float current, float speed) {
        if (timerUtil.delay(4.0F)) {
            boolean larger = target > current;
            if (speed < 0.0F) {
                speed = 0.0F;
            } else if ((double)speed > 1.0) {
                speed = 1.0F;
            }

            float dif = Math.max(target, current) - Math.min(target, current);
            float factor = dif * speed;
            if (factor < 0.1F) {
                factor = 0.1F;
            }

            current = larger ? current + factor : current - factor;
            timerUtil.reset();
        }

        return (double)Math.abs(current - target) < 0.2 ? target : current;
    }

    public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = 0.0F;
        if (timerUtil.delay(20.0F)) {
            movement = (end - current) * smoothSpeed;
            if (movement > 0.0F) {
                movement = Math.max(minSpeed, movement);
                movement = Math.min(end - current, movement);
            } else if (movement < 0.0F) {
                movement = Math.min(-minSpeed, movement);
                movement = Math.max(end - current, movement);
            }

            timerUtil.reset();
        }

        return current + movement;
    }
}
