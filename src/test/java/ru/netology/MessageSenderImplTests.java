package ru.netology;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MessageSenderImplTests {

    GeoService geoService;
    LocalizationService localizationService;
    MessageSender messageSender;

    @BeforeAll
    public static void startedAll() {
        System.out.println("Все тесты стартовали!");

    }

    @BeforeEach
    public void test_started_checkIp() {
        geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location("New York", Country.USA, null, 0));

        localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        messageSender = new MessageSenderImpl(geoService, localizationService);
        System.out.println("Данный тест стартовал");
    }

    @ParameterizedTest
    @MethodSource("hashMapRU")
    public void test_sendRussianIp(Map<String, String> headers) {
        String result = messageSender.send(headers);

        String expected = "Добро пожаловать";

        assertEquals(expected, result);
    }

    @Test
    public static Stream<Map<String, String>> hashMapRU() {
        return Stream.of(
                Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19"));
    }

    @ParameterizedTest
    @MethodSource("hashMapUSA")
    public void test_sendAmericanIp(Map<String, String> headers) {
        String result = messageSender.send(headers);

        String expected = "Welcome";

        assertEquals(expected, result);
    }

    @Test
    public static Stream<Map<String, String>> hashMapUSA() {
        return Stream.of(
                Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149"));
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
