package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


class RegistrationTest {

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }



    @Test
    void shouldValidDataTest() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").sendKeys("Москва");
        String planDate = generateDate(4, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE).setValue(planDate);
        $("[data-test-id='name'] input").sendKeys("Коновалов Евгений");
        $("[data-test-id='phone'] input").sendKeys("+71231231212");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();

        $("[data-test-id='notification']").should(Condition.text("Встреча успешно забронирована на " + planDate), Duration.ofSeconds(15)).should(Condition.visible);



    }
}
