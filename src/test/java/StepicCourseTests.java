import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class StepicCourseTests extends TestBase {
    static Stream<Arguments> checkSearchButtonLanguage() {
        return Stream.of(
                Arguments.of("en", "Search"),
                Arguments.of("ru", "Искать"),
                Arguments.of("be", "Шукаць"),
                Arguments.of("de", "Suche"),
                Arguments.of("es", "Buscar"),
                Arguments.of("pt", "Procurar"),
                Arguments.of("uk", "Шукати"),
                Arguments.of("zh-hans", "搜索")
        );
    }

    @ValueSource(strings = {
            "Java. Collections Framework.",
            "Тестирование ПО с нуля. Теория + Практика"
    })
    @ParameterizedTest(name = "Проверка добавления курса в список желаний.")
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


    @MethodSource
    @ParameterizedTest(name = "Проверка смены названия кнопки при изменении языковых настроек.")
    void checkSearchButtonLanguage(String language, String buttonText) {
        //добавление
        $(".navbar__submenu-toggler").click();
        $(".menu_theme_popup-dark").$("[data-lang = " + language + "]").click();
        $(".search-form__submit").shouldHave(text(buttonText));
        closeWindow();
    }

    @CsvSource(value = {
            "courses-active, Курсы",
            "courses-favorites, Избранные курсы",
            "courses-wishlist, Хочу пройти",
            "courses-archive, Архив"
    })
    @ParameterizedTest(name = "Проверка отображения заголовков в пунктах меню Моё обучение.")
    void checkAccountMenu(String hrefMenu, String headerMenu) {
        open("/learn/courses");
        $(".nav-menu__menu").$("[data-item=" + hrefMenu + "]").click();
        $(".marco-layout__header h1").shouldHave(text(headerMenu));
        closeWindow();
    }
}
