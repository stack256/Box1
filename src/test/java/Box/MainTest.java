package Box;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static Box.Base.*;
import static Box.About.*;
import static Box.Users.*;

public class MainTest {

    @BeforeMethod
    public void setUp() {
        current_login = null;
        doc = new HashMap<String, String[]>();
        items = new HashMap<String, HashMap<String, String[]>>();
        item = new HashMap<String, String[]>();
        errands = new HashMap<String, HashMap<String, String[]>>();
        errand = new HashMap<String, String[]>();
        usersinitial();
        timeoutlnseconds = 30;
        Allure.LIFECYCLE.addListener(About.AllureStepListener.getInstance());
        stack = new ArrayList<About.Stack>();
        removedoc = new ArrayList<>();
        stack.add(new About.Stack());



        if (!System.getProperty("remote.grid").equals(null)) {
            DesiredCapabilities capability = DesiredCapabilities.chrome();
            try {
                driver = new RemoteWebDriver(new URL(System.getProperty("remote.grid")), capability);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            driver = new ChromeDriver();
        }
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        String baseUrl = System.getProperty("stend.url");
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tiredDown() {
        doc.clear();
        items.clear();
        item.clear();
        errands.clear();
        errand.clear();
        stack.clear();
        removedoc.clear();
        users.clear();
        driver.quit();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Входящий")
    @Stories("Жизненный цикл")
    @Title("Создать входящий документ")
    @Test
    public void test1() {
        User user = getuserbyroles("СЭД. Регистраторы");

        doc.put("document", new String[]{"incoming"});
        doc.put("Заголовок", new String[]{"Заголовок"});
        doc.put("Вид документа", new String[]{"Запрос"});
        doc.put("Способ доставки", new String[]{"Личный прием"});
        doc.put("Корреспондент", new String[]{"AT_Organization"});
        doc.put("Корреспондент Тип", new String[]{"Внутренний контрагент"});
        doc.put("Корреспондент Наименование", new String[]{"AT_Organization"});
        doc.put("Представитель корреспондента", new String[]{getuserbylogin("denisov").full});
        doc.put("Получатель", new String[]{"Сотрудник",getuserbylogin("filippova").full});
        //doc.put("В ответ на", new String[]{"Исходящий документ: А1 ЭП только Прочее, № ИСХ-01035/17 от 24.10.2017"});
        //doc.put("В ответ на Номер", new String[]{"1035"});
        doc.put("Исходящий номер", new String[]{"Outgoing-number"});
        doc.put("Исходящий от", new String[]{"21.12.2019"});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        //doc.put("Номер дела", new String[]{"2666","123","прпу-Это дело"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Срок исполнения", new String[]{"21.12.2019"});
        doc.put("На контроле", new String[]{"Да"});
        doc.put("Нерегистрируемый", new String[]{"Да"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createincoming(doc);
        readincoming(doc);
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Внутренний")
    @Stories("Жизненный цикл")
    @Title("Создать внутренний документ")
    @Test
    public void test2() {
        User user = getuserbyroles("Внутренние. Создатели");

        doc.put("document", new String[]{"internal"});
        doc.put("Составитель", new String[]{user.full});
        doc.put("Исполнитель", new String[]{user.full});
        doc.put("Заголовок", new String[]{"Заголовок"});
        doc.put("Вид документа", new String[]{"Аналитическая записка"});
        doc.put("Срок ответа", new String[]{"21.12.2019"});
        doc.put("Получатель", new String[]{"Сотрудник",getuserbylogin("filippova").full});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Подписано на бумажном носителе", new String[]{"Да"});
        doc.put("Подписанты", new String[]{getuserbylogin("fomin").full, getuserbylogin("kozlov").full});
        doc.put("Дата подписания", new String[]{"21.12.2019"});
        //doc.put("В ответ на", new String[]{"Внутренний документ: 1234567890, № NA-00094 от 28.09.2018"});
        //doc.put("В ответ на Номер", new String[]{"00094"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        //doc.put("Номер дела", new String[]{"2666","123","прпу-Это дело"});
        doc.put("Примечание", new String[]{"21"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createinternal(doc);
        readinternal(doc);
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Исходящий")
    @Stories("Жизненный цикл")
    @Title("Создать исходящий документ")
    @Test
    public void test3() {
        User user = getuserbyroles("Исходящие. Создатели");

        doc.put("document", new String[]{"outgoing"});
        doc.put("Составитель", new String[]{user.full});
        doc.put("Исполнитель", new String[]{user.full});
        doc.put("Заголовок", new String[]{"Заголовок"});
        doc.put("Вид документа", new String[]{"Информационное письмо"});
        doc.put("Способ доставки", new String[]{"Личный прием"});
        doc.put("Корреспондент", new String[]{"AT_Organization"});
        doc.put("Корреспондент Тип", new String[]{"Внутренний контрагент"});
        doc.put("Корреспондент Наименование", new String[]{"AT_Organization"});
        doc.put("Адресат корреспондента", new String[]{getuserbylogin("denisov").full});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Подписано на бумажном носителе", new String[]{"Да"});
        doc.put("Подписанты", new String[]{getuserbylogin("fomin").full, getuserbylogin("kozlov").full});
        doc.put("Дата подписания", new String[]{"21.12.2019"});
        //doc.put("В ответ на", new String[]{"Внутренний документ: 1234567890, № NA-00094 от 28.09.2019"});
        //doc.put("В ответ на Номер", new String[]{"00094"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        //doc.put("Номер дела", new String[]{"2666","123","прпу-Это дело"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Завершающий", new String[]{"Нет"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createoutgoing(doc);
        readoutgoing(doc);
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Нормативные документы")
    @Stories("Жизненный цикл")
    @Title("Создать нормативный документ")
    @Test
    public void test4() {
        User user = getuserbyroles("НД. Создатели");

        doc.put("document", new String[]{"nd"});
        doc.put("Составитель", new String[]{user.full});
        doc.put("Исполнитель", new String[]{user.full});
        doc.put("Заголовок", new String[]{"Заголовок"});
        doc.put("Вид документа", new String[]{"Категории подписываемых вложений"});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Подписано на бумажном носителе", new String[]{"Да"});
        doc.put("Подписанты", new String[]{getuserbylogin("fomin").full, getuserbylogin("kozlov").full});
        doc.put("Дата подписания", new String[]{"21.12.2019"});
        doc.put("Номер дела", new String[]{"2018","Управление","Рабочее подразделение","ALL-Дело для всех типов документов"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        doc.put("Подразделения", new String[]{"AT_Organization"});
        doc.put("Бессрочный", new String[]{"Нет"});
        doc.put("Период действия С", new String[]{"01.01.2019"});
        doc.put("Период действия По", new String[]{"21.12.2019"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        creatend(doc);
        readnd(doc);
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Организационно-распорядительный документ")
    @Stories("Жизненный цикл")
    @Title("Создать организационно-распорядительный документ")
    @Test
    public void test5() {
        User user = getuserbyroles("ОРД. Создатели");

        doc.put("document", new String[]{"ord"});
        doc.put("Составитель", new String[]{user.full});
        doc.put("Исполнитель", new String[]{user.full});
        doc.put("Вид документа", new String[]{"Категории подписываемых вложений"});
        doc.put("Заголовок", new String[]{"Заголовок"});
        doc.put("Срок исполнения", new String[]{"21.12.2019"});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Подписано на бумажном носителе", new String[]{"Да"});
        doc.put("Подписанты", new String[]{getuserbylogin("fomin").full, getuserbylogin("kozlov").full});
        doc.put("Дата подписания", new String[]{"21.12.2019"});
        doc.put("Контролёр", new String[]{getuserbylogin("denisov").full});
        doc.put("Подтверждать завершение работы по документу", new String[]{"Да"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        doc.put("Номер дела", new String[]{"2018","Управление","Рабочее подразделение","ALL-Дело для всех типов документов"});
        //doc.put("Отменяемые документы", new String[]{"Внутренний документ: 1234567890, № NA-00094 от 28.09.2019"});
        //doc.put("Отменяемые документы Номер", new String[]{"00094"});
        //doc.put("Принимаемые документы", new String[]{"Внутренний документ: 1234567890, № NA-00094 от 28.09.2019"});
        //doc.put("Принимаемые документы Номер", new String[]{"00094"});

        item = new HashMap<String, String[]>();
        item.put("document", new String[]{"ord"});
        item.put("Пункты Заголовок", new String[]{"Заголовок"});
        item.put("Пункты Автор", new String[]{user.full});
        item.put("Пункты Содержание", new String[]{"Содержание"});
        item.put("Пункты Исполнитель", new String[]{getuserbylogin("denisov").full});
        item.put("Пункты Соисполнители", new String[]{getuserbylogin("kozlov").full});
        item.put("Пункты Срок исполнения", new String[]{"Без срока"});
        item.put("Пункты Требуется отчет", new String[]{"Да"});
        item.put("Пункты Контролер", new String[]{getuserbylogin("denisov").full});
        item.put("Пункты Тематика", new String[]{"Доставка воды"});
        items.put("1", item);

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createord(doc, items);
        readord(doc);
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Поручение")
    @Stories("Жизненный цикл")
    @Title("Создать поручение")
    @Test
    public void test6() {
        User user = getuserbyroles("Поручения. Инициатор");

        doc.put("document", new String[]{"errand"});
        doc.put("Тип поручения", new String[]{"На исполнение (неконтрольное)"});
        doc.put("Заголовок", new String[]{"Ознакомить подчиненных"});
        doc.put("Текст поручения", new String[]{"Заголовок"});
        doc.put("Исполнитель", new String[]{getuserbylogin("kozlov").full});
        doc.put("Соисполнители", new String[]{getuserbylogin("denisov").full});
        doc.put("Срок исполнения", new String[]{"21.12.2019"});
        doc.put("Направлять периодически", new String[]{"Да"});
        doc.put("Повторять", new String[]{"Ежедневно"});
        doc.put("Начало повторений", new String[]{"21.12.2018"});
        doc.put("Окончание повторений", new String[]{"21.12.2019"});
        doc.put("Контролер", new String[]{getuserbylogin("denisov").full});
        doc.put("Требуется отчет", new String[]{"Да"});
        doc.put("Получатель отчета", new String[]{"Контролер"});
        doc.put("Важное", new String[]{"Да"});
        doc.put("Тематика", new String[]{"Доставка воды"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createerrand(doc, "Сохранить черновик");
        readerrand(doc);
        doc.put("Запись в бж",new String[]{historystandartcreateerrand(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }
/*


    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Резолюция")
    @Stories("Жизненный цикл")
    @Title("Создать резолюцию")
    @Test
    public void test7() {
        User user = getuserbyroles("Резолюции Создатель");

        doc.put("document", new String[]{"resolutions"});
        doc.put("Утверждено вне системы", new String[]{"Да"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        doc.put("Автор", new String[]{getuserbylogin(user.login).full});
        doc.put("Контролер", new String[]{getuserbylogin("denisov").full});
        doc.put("Завершающий", new String[]{"Контролер"});
        doc.put("Контроль", new String[]{"Да"});
        doc.put("Срок исполнения", new String[]{"1 календарный день"});

        errand = new HashMap<String, String[]>();
        errand.put("Тип поручения", new String[]{"На исполнение (неконтрольное)"});
        errand.put("Заголовок", new String[]{"Ознакомить подчиненных"});
        errand.put("Исполнитель", new String[]{getuserbylogin("kozlov").full});
        errand.put("Соисполнители", new String[]{getuserbylogin("denisov").full});
        errand.put("Контролер", new String[]{getuserbylogin("denisov").full});
        errand.put("Срок исполнения", new String[]{"Без срока"});
        errand.put("Требуется отчет", new String[]{"Да"});
        errands.put("1",errand);
        errand = new HashMap<String, String[]>();

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createresolutions(doc, "Сохранить черновик");
        readresolutions(doc);
        doc.put("Запись в бж",new String[]{historystandartcreateerrand(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }*/
}