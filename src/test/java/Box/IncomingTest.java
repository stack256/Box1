package Box;

import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static Box.Base.*;
import static Box.Users.*;

@Title("Входящий документ")
public class IncomingTest extends About {

    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Входящий")
    @Stories("Жизненный цикл")
    @Title("Создать входящий документ")
    @Test(retryAnalyzer = About.Retry.class)
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
        doc.put("Запись в бж",new String[]{historystandartcreate(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Входящий")
    @Stories("Жизненный цикл")
    @Title("Направить на регистрацию черновик")
    @Test(retryAnalyzer = About.Retry.class)
    public void test11() {
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
        doc.put("Нерегистрируемый", new String[]{"Нет"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createincoming(doc);
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{historystandartcreate(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //выполнить действие Направить на регистрацию
        righactionexecute("Направить на регистрацию","ОК","На обработке регистратором",doc);
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{historystandartchangestatus(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Входящий")
    @Stories("Зарегистрировать черновик")
    @Title("Проверка отправки уведомлений корреспонденту, если не заполнены данные по исходящему документу")
    @Test(retryAnalyzer = About.Retry.class)
    public void test12() {
        User user = getuserbyroles("СЭД. Регистраторы");

        doc.put("document", new String[]{"incoming"});
        doc.put("Заголовок", new String[]{"Заголовок"});
        doc.put("Вид документа", new String[]{"Запрос"});
        doc.put("Способ доставки", new String[]{"Личный прием"});
        doc.put("Корреспондент", new String[]{"ООО Ромашка"});
        doc.put("Корреспондент Тип", new String[]{"Внешний контрагент"});
        doc.put("Корреспондент Наименование", new String[]{"ООО Ромашка"});
        //doc.put("Представитель корреспондента", new String[]{getuserbylogin("denisov").full});
        doc.put("Получатель", new String[]{"Сотрудник",getuserbylogin("filippova").full});
        //doc.put("В ответ на", new String[]{"Исходящий документ: А1 ЭП только Прочее, № ИСХ-01035/17 от 24.10.2017"});
        //doc.put("В ответ на Номер", new String[]{"1035"});
        //doc.put("Исходящий номер", new String[]{"Outgoing-number"});
        //doc.put("Исходящий от", new String[]{"21.12.2019"});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        //doc.put("Номер дела", new String[]{"2666","123","прпу-Это дело"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Срок исполнения", new String[]{"21.12.2019"});
        doc.put("На контроле", new String[]{"Да"});
        doc.put("Нерегистрируемый", new String[]{"Нет"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createincoming(doc);
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{historystandartcreate(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //выполнить действие Направить на регистрацию
        righactionexecute("Зарегистрировать","ОК","Зарегистрирован",doc);
        doc.put("Дата регистрации",doc.get("Дата"));
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{current_user + " перевел(а) документ \"" + incoming_header(doc.get("Вид документа"), new String[]{"Не присвоено"}) + "\" в статус \"" + doc.get("Статус")[0] + "\""});
        readhistory(doc.get("Запись в бж"),doc);
        //ReadEmail("itsarkova", "12345","Добрый день! Ваше письмо " + doc.get("Заголовок")[0] + " № б/н от дата не указана, поступившее " + doc.get("Дата")[0] + ", зарегистрировано " + doc.get("Дата")[0] + " под номером " + doc.get("Номер")[0] + ".");
        ReadEmail("itsarkova", "12345","Добрый день! Ваше письмо " + doc.get("Заголовок")[0] + " б/н дата не указана, поступившее " + doc.get("Дата")[0] + ", зарегистрировано " + doc.get("Дата")[0] + " под номером " + doc.get("Номер")[0] + ".");
        //if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Входящий")
    @Stories("Зарегистрировать черновик")
    @Title("Проверка отправки уведомлений корреспонденту, если заполнены данные по исходящему документу")
    @Test(retryAnalyzer = About.Retry.class)
    public void test13() {
        User user = getuserbyroles("СЭД. Регистраторы");

        doc.put("document", new String[]{"incoming"});
        doc.put("Заголовок", new String[]{"Тест"});
        doc.put("Вид документа", new String[]{"Запрос"});
        doc.put("Способ доставки", new String[]{"Курьерская служба"});
        doc.put("Корреспондент", new String[]{"ООО Ромашка"});
        doc.put("Корреспондент Тип", new String[]{"Внешний контрагент"});
        doc.put("Корреспондент Наименование", new String[]{"ООО Ромашка"});
        //doc.put("Представитель корреспондента", new String[]{getuserbylogin("denisov").full});
        doc.put("Получатель", new String[]{"Сотрудник",getuserbylogin("filippova").full});
        //doc.put("В ответ на", new String[]{"Исходящий документ: А1 ЭП только Прочее, № ИСХ-01035/17 от 24.10.2017"});
        //doc.put("В ответ на Номер", new String[]{"1035"});
        doc.put("Исходящий номер", new String[]{"78/2"});
        doc.put("Исходящий от", new String[]{currentdate(-1)});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        //doc.put("Номер дела", new String[]{"2666","123","прпу-Это дело"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Срок исполнения", new String[]{"21.12.2019"});
        doc.put("На контроле", new String[]{"Да"});
        doc.put("Нерегистрируемый", new String[]{"Нет"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createincoming(doc);
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{historystandartcreate(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //выполнить действие Направить на регистрацию
        righactionexecute("Зарегистрировать","ОК","Зарегистрирован",doc);
        doc.put("Дата регистрации",doc.get("Дата"));
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{current_user + " перевел(а) документ \"" + incoming_header(doc.get("Вид документа"), new String[]{"Не присвоено"}) + "\" в статус \"" + doc.get("Статус")[0] + "\""});
        readhistory(doc.get("Запись в бж"),doc);
        //ReadEmail("itsarkova", "12345","Добрый день! Ваше письмо " + doc.get("Заголовок")[0] + " № " + doc.get("Исходящий номер")[0] + " от " + doc.get("Исходящий от")[0] + ", поступившее " + doc.get("Дата")[0] + ", зарегистрировано " + doc.get("Дата")[0] + " под номером " + doc.get("Номер")[0] + ".");
        ReadEmail("itsarkova", "12345","Добрый день! Ваше письмо " + doc.get("Заголовок")[0] + " " + doc.get("Исходящий номер")[0] + " " + doc.get("Исходящий от")[0] + ", поступившее " + doc.get("Дата")[0] + ", зарегистрировано " + doc.get("Дата")[0] + " под номером " + doc.get("Номер")[0] + ".");
//if (!stack.get(0).value)
        removedocs();
    }



    @Description("Какое то описание")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Входящий")
    @Stories("Зарегистрировать черновик")
    @Title("Проверка отображения записи БЖ при отсутствии е мэйла у корреспондента")
    @Test(retryAnalyzer = About.Retry.class)
    public void test14() {
        User user = getuserbyroles("СЭД. Регистраторы");

        doc.put("document", new String[]{"incoming"});
        doc.put("Заголовок", new String[]{"Тест"});
        doc.put("Вид документа", new String[]{"Запрос"});
        doc.put("Способ доставки", new String[]{"Личный прием"});
        doc.put("Корреспондент", new String[]{"ООО Тюльпан"});
        doc.put("Корреспондент Тип", new String[]{"Внешний контрагент"});
        doc.put("Корреспондент Наименование", new String[]{"ООО Тюльпан"});
        //doc.put("Представитель корреспондента", new String[]{getuserbylogin("denisov").full});
        doc.put("Получатель", new String[]{"Сотрудник",getuserbylogin("filippova").full});
        //doc.put("В ответ на", new String[]{"Исходящий документ: А1 ЭП только Прочее, № ИСХ-01035/17 от 24.10.2017"});
        //doc.put("В ответ на Номер", new String[]{"1035"});
        //doc.put("Исходящий номер", new String[]{"78/2"});
        //doc.put("Исходящий от", new String[]{currentdate(-1)});
        doc.put("Содержание", new String[]{"21.12.2019"});
        doc.put("Количество листов", new String[]{"21"});
        doc.put("Тематика", new String[]{"Доставка воды"});
        //doc.put("Номер дела", new String[]{"2666","123","прпу-Это дело"});
        doc.put("Примечание", new String[]{"21"});
        doc.put("Срок исполнения", new String[]{"21.12.2019"});
        doc.put("На контроле", new String[]{"Да"});
        doc.put("Нерегистрируемый", new String[]{"Нет"});

        //авторизоваться
        auth(user.famio,user.login,user.pass);
        //создать входящий документ
        createincoming(doc);
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{historystandartcreate(doc)});
        readhistory(doc.get("Запись в бж"),doc);
        //выполнить действие Направить на регистрацию
        righactionexecute("Зарегистрировать","ОК","Зарегистрирован",doc);
        doc.put("Дата регистрации",doc.get("Дата"));
        //проверить атрибуты и их значения на форме просмотра
        readincoming(doc);
        //проверить наличие записи в бж
        doc.put("Запись в бж",new String[]{
                current_user + " перевел(а) документ \"" + incoming_header(doc.get("Вид документа"), new String[]{"Не присвоено"}) + "\" в статус \"" + doc.get("Статус")[0] + "\"",
                "Корреспонденту ООО Тюльпан не было направлено уведомление по причине отсутствия адреса электронной почты"
        });
        readhistory(doc.get("Запись в бж"),doc);
        //if (!stack.get(0).value)
        removedocs();
    }
}