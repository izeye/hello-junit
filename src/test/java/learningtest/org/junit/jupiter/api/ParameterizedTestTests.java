package learningtest.org.junit.jupiter.api;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests for {@link ParameterizedTest}.
 *
 * @author Johnny Lim
 */
class ParameterizedTestTests {

    @ParameterizedTest
    @MethodSource("argumentsStream")
    void test(String input, String output) {
        System.out.printf("input: '%s', output: '%s'%n", input, output);
    }

    static Stream<Arguments> argumentsStream() {
        return Stream.of(arguments("input1", "output1"), arguments("input2", "output2"));
    }

}
