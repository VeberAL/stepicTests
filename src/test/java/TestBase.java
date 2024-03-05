import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {
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
}
