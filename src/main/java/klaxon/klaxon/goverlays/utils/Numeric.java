package klaxon.klaxon.goverlays.utils;

public class Numeric {

    /**
     * Takes log base of num
     *
     * @param num  Number to log
     * @param base Base to log by
     * @return Double representing log base of num
     */
    public static double log(double num, double base) {

        return Math.log(num) / Math.log(base);
    }

    public static double log(long num, long base) {

        return log(num, (double) base);
    }

    public static double log(int num, int base) {

        return log(num, (double) base);
    }

    /**
     * WARNING: THIS IS NOT ACCURATE!
     * Floats and doubles are innacurate by nature, use a dedicated decimal class if you care.
     * I don't, this will be within one of whatever the last decimal is.
     * i.e. if you get 8.67 the correct answer is within +-0.01.
     *
     * @param num
     * @param decimals
     * @return
     */
    public static double round(double num, int decimals) {

        return Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }
}
