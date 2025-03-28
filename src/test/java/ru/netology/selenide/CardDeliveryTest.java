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

public class CardDeliveryTest {
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldReserveTheDeliveryDate() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кемерово");
        String planningDay = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().press(Keys.DELETE).setValue(planningDay);
        $("[data-test-id='name'] input").setValue("Павлов Антон");
        $("[data-test-id='phone'] input").setValue("+75412369852");
        $("[data-test-id='agreement']").click();
        $$("button").filter(Condition.visible).last().click();
        $("[data-test-id='notification']").should(Condition.visible, Duration.ofSeconds(15)).should(Condition.text("Успешно! Встреча успешно забронирована на " + planningDay));
    }
}
