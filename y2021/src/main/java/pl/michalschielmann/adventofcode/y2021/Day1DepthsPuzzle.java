package pl.michalschielmann.adventofcode.y2021;

import java.util.List;
import java.util.Objects;

import static java.lang.System.lineSeparator;
import static java.util.Arrays.stream;

class Day1DepthsPuzzle {
    private final List<Integer> input;

    public Day1DepthsPuzzle(String input) {
        this.input = parseInput(input);
    }

    public Integer getNumberOfIncreasedDepthReadingsForWindowSize(int windowSize) {
        int depthIncreases = 0;
        for (int i = windowSize; i < input.size(); i++) {
            if (input.get(i) > input.get(i - windowSize)) {
                depthIncreases++;
            }
        }
        return depthIncreases;
    }

    private List<Integer> parseInput(String input) {
        return stream(input.split(lineSeparator()))
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(line -> line.length() > 0)
                .map(Integer::parseInt)
                .toList();
    }
}
