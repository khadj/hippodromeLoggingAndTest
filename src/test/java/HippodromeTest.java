import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class HippodromeTest {
    @Test
    void testConstructorNullParameter() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Hippodrome hippodrome = new Hippodrome(null);
                });
    }

    @Test
    void testHorsesCanNotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    Hippodrome horse = new Hippodrome(null);
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Horses cannot be null.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConstructorWithEmptyList() {
        List<Horse> horseList = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,
                () -> {Hippodrome hippodrome = new Hippodrome(horseList);});
    }

    @Test
    void testHorsesCanNotBeEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {Hippodrome hippodrome = new Hippodrome(new ArrayList<>());});
        String actualMessage = exception.getMessage();
        String expectedMessage = "Horses cannot be empty.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetHorses() {
        List<Horse> horseList = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            horseList.add(new Horse("Horse " + i, 1));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);
        List<Horse> returnedList = hippodrome.getHorses();

        assertEquals(horseList.size(), returnedList.size(), "Size mismatch");

        for (int i = 0; i < horseList.size(); i++) {
            assertSame(horseList.get(i), returnedList.get(i),
                    "Mismatch at index " + i + ": expected " + horseList.get(i) + ", actual " + returnedList.get(i));
        }
    }

    @Test
    void testMove() {
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            mockHorses.add(horse);
        }

        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();

        for (Horse horse : mockHorses) {
            verify(horse).move();
        }
    }

    @Test
    void testGetWinner() {
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Horse 1", 1, 10));
        horses.add(new Horse("Horse 2", 2, 20));
        horses.add(new Horse("Horse 3", 3, 30));

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();

        double maxDistance = Double.MIN_VALUE;
        Horse expectedWinner = null;
        for (Horse horse : horses) {
            double distance = horse.getDistance();
            if (distance > maxDistance) {
                maxDistance = distance;
                expectedWinner = horse;
            }
        }

        assertEquals(expectedWinner, winner, "Mismatched winner");
    }

}