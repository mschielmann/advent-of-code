package pl.michalschielmann.adventofcode.y2021;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Arrays.stream;

public class Day2DivePuzzle {

    private final List<NavigationCommand> navigationCommands;

    public Day2DivePuzzle(String input) {
        navigationCommands = parseInput(input);
    }

    public int getDistanceOnXAxis() {
        return navigationCommands.stream()
                .filter(command -> NavigationCommand.Direction.FORWARD.equals(command.direction))
                .map(NavigationCommand::distance)
                .reduce(Math::addExact).orElse(0);
    }

    public int getIncreaseInDepth() {
        int increase = navigationCommands.stream()
                .filter(command -> NavigationCommand.Direction.DOWN.equals(command.direction))
                .map(NavigationCommand::distance)
                .reduce(Math::addExact).orElse(0);

        int decrease = navigationCommands.stream()
                .filter(command -> NavigationCommand.Direction.UP.equals(command.direction))
                .map(NavigationCommand::distance)
                .reduce(Math::addExact).orElse(0);

        return increase - decrease;
    }

    public int getDistanceMetric() {
        return getIncreaseInDepth() * getDistanceOnXAxis();
    }

    public int getDistanceMetricUsingAim() {
        int aim = 0;
        int distanceOnXAxis = 0;
        int depth = 0;
        for (NavigationCommand command : navigationCommands) {
            if (command.direction.equals(NavigationCommand.Direction.DOWN)) {
                aim += command.distance;
            } else if (command.direction.equals(NavigationCommand.Direction.UP)) {
                aim -= command.distance;
            } else {
                distanceOnXAxis += command.distance;
                depth += command.distance * aim;
            }
        }
        return depth * distanceOnXAxis;
    }

    private List<NavigationCommand> parseInput(String input) {
        return stream(input.split(System.lineSeparator()))
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(line -> line.length() > 0)
                .map(this::parseNavigationCommand)
                .toList();

    }

    private NavigationCommand parseNavigationCommand(String commandInString) {
        String[] commandParts = commandInString.split(" ");
        NavigationCommand.Direction direction = NavigationCommand.Direction.valueOf(commandParts[0].toUpperCase(Locale.ROOT));
        int distance = Integer.parseInt(commandParts[1]);

        return new NavigationCommand(direction, distance);
    }

    private record NavigationCommand(Direction direction, int distance) {
        private enum Direction {
            FORWARD,
            UP,
            DOWN;
        }
    }
}
