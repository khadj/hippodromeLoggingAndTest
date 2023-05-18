import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


class HorseTest {

    @Test
    void testFirstParameterIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse = new Horse(null,
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE),
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
                });
    }

    @Test
    void testNameCanNotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse = new Horse(null,
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE),
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Name cannot be null.";
        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "  \t\n\r"})
    void testConstructorWithEmptyOrSpaceName(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse = new Horse(name,
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE),
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "  \t\n\r"})
    void testNameCanNotBeBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse = new Horse(name,
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE),
                            Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Name cannot be blank.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testWithNegativeSpeed() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse =
                            new Horse("anotherName", -1, Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
                });
    }

    @Test
    void testSpeedCanNotBeNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse =
                            new Horse("anotherName", -1, Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Speed cannot be negative.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testWithNegativeDistance() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Horse horse =
                            new Horse("anotherName", Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE), -1);
                });
    }

    @Test
    void testDistanceCanNotBeNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> {
                            Horse horse =
                                    new Horse("anotherName", Horse.getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE), -1);
                        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Distance cannot be negative.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetName() {
        Horse horse = new Horse("anotherName", 1, 2);
        String actualMessage = horse.getName();
        String expectedMessage = "anotherName";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetSpeed() {
        Horse horse = new Horse("anotherName", 1, 2);
        double actualSpeed = horse.getSpeed();
        double expectedSpeed = 1.0;
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void testGetDistance() {
        Horse horse = new Horse("anotherName", 1, 2);
        double actualSpeed = horse.getDistance();
        double expectedSpeed = 2;
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void testGetDistanceReturnZeroWithoutThirdParameter() {
        Horse horse = new Horse("anotherName", 1);
        double actualSpeed = horse.getDistance();
        double expectedSpeed = 0;
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void testMoveCallsGetRandomDoubleWithExpectedParameters() {
        double expectedParam1 = 0.2;
        double expectedParam2 = 0.9;

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("anotherName", 1);
            horse.move();

            // Проверка вызова статического метода getRandomDouble с ожидаемыми параметрами
            mockedStatic.verify(() -> Horse.getRandomDouble(expectedParam1, expectedParam2));
        }
    }

    @Test
    void testMoveReturnDistanceValueByFormula() {
        double speed = 1.0;
        double randomDouble = 0.5;
        double distance = 10.0;

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);

            Horse horse = new Horse("anotherName", speed, distance);
            horse.move();

            double expectedDistance = distance + speed * randomDouble;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

}