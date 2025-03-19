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

public class CardDeliveryTestV3 {
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldReserveTheDeliveryDateForTheNextMonth() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("ха");
        $$(".menu-item_type_block").filter(Condition.visible).findBy(Condition.text("Хабаровск")).click();
        String planningDay = generateDate(20, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().press(Keys.DELETE);
        if(!generateDate(3, "MM").equals(generateDate(20, "MM"))) $(".calendar__arrow_direction_right[data-step='1']").click();
        $$(".popup__container .calendar__day").findBy(Condition.text(generateDate(20, "d"))).click();
        $("[data-test-id='name'] input").setValue("Петров Алексей");
        $("[data-test-id='phone'] input").setValue("+79852145632");
        $("[data-test-id='agreement']").click();
        $$("button").filter(Condition.visible).last().click();
        $("[data-test-id='notification']").should(Condition.visible, Duration.ofSeconds(15)).should(Condition.text("Успешно! Встреча успешно забронирована на " + planningDay));
    }
}
