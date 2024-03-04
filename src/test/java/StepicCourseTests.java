import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class StepicCourseTests {
    @BeforeAll
    static void resolutionAndUrl() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://stepik.org";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
        Configuration.timeout = 20000;
    }
    @BeforeEach
    void signUpStepic() {
        open("/catalog?auth=login");
        $("[name=login]").setValue("qa.guru.test@list.ru");
        $("[name=password]").setValue("12832156ф");
        $(".sign-form__btn").click();
        sleep(1000);
    }
    @ValueSource(strings = {
            "Java. Collections Framework.",
            "Тестирование ПО с нуля. Теория + Практика"
    })
    @ParameterizedTest
    @DisplayName("Проверка добавления курса в список желаний.")
    void addedCourseInWishList(String searchCourse) {
        //добавление
        $(".search-form__input").setValue(searchCourse).pressEnter();
        $$("ul.course-cards li").findBy(text(searchCourse)).click();
        $("button.course-promo-enrollment__wishlist-btn").click();
        $$(".navbar__links li").findBy(text("Моё обучение")).click();
        $("[data-item=courses-wishlist]").click();
        //проверка и удаление
        $(".marco-layout__content").shouldHave(text(searchCourse));
        $$(".learn-course-tile").findBy(text(searchCourse)).click();
        $("button.course-promo-enrollment__wishlist-btn").click();
        closeWindow();
    }

}
