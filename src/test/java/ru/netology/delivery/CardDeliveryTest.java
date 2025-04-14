package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private static final String SUCCESS_MESSAGE = "Встреча успешно запланирована на ";
    private static final String REPLAN_MESSAGE = "У вас уже запланирована встреча на другую дату. Перепланировать?";

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitValidForm() {
        String meetingDate = DataGenerator.generateDate(4);

        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(meetingDate);
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText(SUCCESS_MESSAGE + meetingDate));
    }

    @Test
    void shouldReplanMeeting() {
        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(5);

        // Первое заполнение формы
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText(SUCCESS_MESSAGE + firstDate));

        // Изменение даты
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondDate);
        $(".button").click();

        $("[data-test-id=replan-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(REPLAN_MESSAGE));

        $("[data-test-id=replan-notification] button").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(SUCCESS_MESSAGE + secondDate));
    }
}
