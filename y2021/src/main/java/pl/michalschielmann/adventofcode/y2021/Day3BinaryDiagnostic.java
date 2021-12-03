package pl.michalschielmann.adventofcode.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;

class Day3BinaryDiagnostic {
    public static final char CHAR_0 = '0';
    public static final char CHAR_1 = '1';

    private final List<String> readings;

    Day3BinaryDiagnostic(String input) {
        readings = parseInput(input);
        validateInput();
    }

    private void validateInput() {
        if (readings.size() == 0) {
            throw new IllegalArgumentException("No readings to process");
        }

        if (!readings.stream().allMatch(this::containsOnlyBinaryDigits)) {
            throw new IllegalArgumentException("Strings can contain only 0s and 1s");
        }

        int readingSize = readings.get(0).length();
        if (readings.stream().anyMatch(reading -> reading.length() != readingSize)) {
            throw new IllegalArgumentException("Each reading should have same amount of binary digits");
        }
    }

    private List<String> parseInput(String input) {
        return stream(input.split(System.lineSeparator()))
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(string -> string.length() > 0)
                .toList();
    }

    private boolean containsOnlyBinaryDigits(String string) {
        return string
                .replaceAll(CHAR_0 + "", "")
                .replaceAll(CHAR_1 + "", "")
                .length() == 0;
    }


    @SuppressWarnings("StringConcatenationInLoop")
    public long calculatePowerReadings() {
        String gamaRateString = "";
        String epsilonRateString = "";
        for (int i = 0; i < readings.get(0).length(); i++) {
            int numberOfOnes = 0;
            int numberOfZeros = 0;

            for(String reading : readings) {
                if (reading.charAt(i) == CHAR_0) {
                    numberOfZeros++;
                } else {
                    numberOfOnes++;
                }
            }

            if (numberOfOnes > numberOfZeros) {
                gamaRateString += CHAR_1;
                epsilonRateString += CHAR_0;
            } else {
                gamaRateString += CHAR_0;
                epsilonRateString += CHAR_1;
            }
        }

        return parseLong(gamaRateString, 2) * parseLong(epsilonRateString, 2);
    }

    public long calculateLifeSupportReading() {
        String oxygenReading = calculateOxygenReadings(readings);
        String co2Reading = calculateCo2Readings(readings);
        return parseLong(oxygenReading, 2) * parseLong(co2Reading, 2);
    }

    private String calculateOxygenReadings(List<String> readings) {
        return readLifeSupportReadingsForDominantChar(new ArrayList<>(readings), '1');
    }

    private String calculateCo2Readings(List<String> readings) {
        return readLifeSupportReadingsForDominantChar(new ArrayList<>(readings), '0');
    }

    private String readLifeSupportReadingsForDominantChar(List<String> readings, char dominantChar) {
        char nonDominantChar = dominantChar == CHAR_0 ? CHAR_1 : CHAR_0;
        for (int i = 0; i < readings.get(0).length(); i++) {
            int numberOfOnes = 0;
            int numberOfZeros = 0;
            for(String reading : readings) {
                if (reading.charAt(i) == CHAR_0) {
                    numberOfZeros++;
                } else {
                    numberOfOnes++;
                }
            }
            final int index = i;
            if (numberOfOnes >= numberOfZeros) {
                readings = readings.stream().filter(reading -> reading.charAt(index) == dominantChar).toList();
            } else {
                readings = readings.stream().filter(reading -> reading.charAt(index) == nonDominantChar).toList();
            }
            if (readings.size() == 1) {
                return readings.get(0);
            }
        }
        throw new IllegalStateException("There is no unique reading to return");
    }
}
