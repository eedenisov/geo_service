package ru.netology;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceImplTests {

    static LocalizationService sut;

    @BeforeEach
    public void started() {
        System.out.println("Данный тест стартовал");
    }

    @BeforeAll
    public static void startedAll() {
        System.out.println("Все тесты стартовали!");
        sut = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    public void test_enam(Country country) {
        assertNotNull(country);
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    public void test_localRussian() {
        String expected = "Добро пожаловать";

        String result = sut.locale(Country.RUSSIA);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    public void test_localAmerican() {
        String expected = "Welcome";

        String result = sut.locale(Country.USA);

        assertEquals(expected, result);
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("Все тесты закончены!");
    }

    @AfterEach
    public void finished() {
        System.out.println("Данный тест закончен");
    }

}
