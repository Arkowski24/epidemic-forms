package pl.edu.agh.ki.covid19tablet.pdfgenetator.containers;

public class LimitValues {

    private static final double maxTemperature = 37.0;

    private static final double minPulseRate = 50;
    private static final double maxPulseRate = 150;

    private static final double minAeration = 90;

    private static final String[] outOfNormBreathRates = {"30-40", ">40"};



    public static double getMaxTemperature() {
        return maxTemperature;
    }

    public static double getMinPulseRate() {
        return minPulseRate;
    }
    public static double getMaxPulseRate() {
        return maxPulseRate;
    }

    public static double getMinAeration() {
        return minAeration;
    }

    public static String[] getOutOfNormBreathRates() {
        return outOfNormBreathRates;
    }
}
