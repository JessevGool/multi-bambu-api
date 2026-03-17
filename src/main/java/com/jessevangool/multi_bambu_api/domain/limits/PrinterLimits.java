package com.jessevangool.multi_bambu_api.domain.limits;

public final class PrinterLimits {

    private PrinterLimits() {
    }

    public static final class FanSpeed {
        public static final int MIN = 0;
        public static final int MAX = 255;

        private FanSpeed() {
        }

        public static boolean isValid(int speed) {
            return speed >= MIN && speed <= MAX;
        }
    }

    public static final class FanIndex {
        public static final int MIN = 1;
        public static final int MAX = 3;

        private FanIndex() {
        }

        public static boolean isValid(int index) {
            return index >= MIN && index <= MAX;
        }
    }
}
