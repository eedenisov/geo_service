package ru.netology;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GeoServiceImplTests {

    static GeoService sut;

    @BeforeEach
    public void started() {
        System.out.println("Данный тест стартовал");
    }

    @BeforeAll
    public static void startedAll() {
        System.out.println("Все тесты стартовали!");
        sut = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("source")
    public void test_byIp(String ip, Location expected) {

        Location result = sut.byIp(ip);

        assertEquals(expected.getCity(), result.getCity());
        assertEquals(expected.getCountry(), result.getCountry());
    }

    @Test
    public static Stream<Arguments> source() {
        return Stream.of(
                arguments("172.0.32.11",
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                arguments("96.44.183.149",
                        new Location("New York", Country.USA, " 10th Avenue", 32))
        );
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("Все тесты закончены!");
    }

    @AfterEach
    public void finished() {
        System.out.println("Данный тест закончен");
    }

    @Test
    public void test_byCoordinates() {
        double a = 5, b = 4;
        var expected = RuntimeException.class;

        Executable action = () -> sut.byCoordinates(a, b);

        assertThrows(expected, action);
    }

}
