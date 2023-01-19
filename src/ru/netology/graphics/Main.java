package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextColorSchemaImpl;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterImpl;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {

        TextGraphicsConverter converter = new TextGraphicsConverterImpl(); // Создайте тут объект вашего класса конвертера
        converter.setMaxRatio(4);
        converter.setMaxWidth(400);
        converter.setMaxHeight(400);
        TextColorSchema schema = new TextColorSchemaImpl();
        converter.setTextColorSchema(schema);


        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
         String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
//         String imgTxt = converter.convert(url);
//         System.out.println(imgTxt);
    }
}
