package javagraph;

/**
 * 
 * @author Jakub Miętki
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javagraph.nodeManager.Generator;

public class GeneratorTest {

    private Generator generator;

    @BeforeEach
    void beforeEach() {
        generator = new Generator();
    }

    @Test
    void notValidSizeArguments() {
        // given

        int a = -1;
        int b = 2;
        double range_a = 0;
        double range_b = 10;

        // when

        Throwable exception = assertThrows(
                IOException.class, () -> {
                    generator.generate(a, b, range_a, range_b);
                });

        // then

        assertEquals("Bad graph size", exception.getMessage());

    }

    @Test
    void notValidRangeArguments() {
        // given

        int a = 0;
        int b = 2;
        double range_a = -1;
        double range_b = 0;

        double exception_2_range_a = 2;
        double exception_2_range_b = 2;

        // when

        Throwable exception1 = assertThrows(
                IOException.class, () -> {
                    generator.generate(a, b, range_a, range_b);
                });
        Throwable exception2 = assertThrows(
                IOException.class, () -> {
                    generator.generate(a, b, exception_2_range_a, exception_2_range_b);
                });

        // then

        assertEquals("Bad graph weights", exception1.getMessage());
        assertEquals("Bad graph weights", exception2.getMessage());

    }
}
