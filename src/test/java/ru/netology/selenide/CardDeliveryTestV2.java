package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTestV2 {
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldReserveTheDeliveryDateAWeekLater() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("кр");
        $$(".menu-item_type_block").filter(Condition.visible).findBy(Condition.text("Краснодар")).click();
        String planningDay = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().press(Keys.DELETE);
        if(!generateDate(3, "MM").equals(generateDate(7, "MM"))) $(".calendar__arrow_direction_right[data-step='1']").click();
        $$(".popup__container .calendar__day").findBy(Condition.text(generateDate(7, "d"))).click();
        $("[data-test-id='name'] input").setValue("Антонов Павел");
        $("[data-test-id='phone'] input").setValue("+79865214852");
        $("[data-test-id='agreement']").click();
        $$("button").filter(Condition.visible).last().click();
        $("[data-test-id='notification']").should(Condition.visible, Duration.ofSeconds(15)).should(Condition.text("Успешно! Встреча успешно забронирована на " + planningDay));
    }
}
