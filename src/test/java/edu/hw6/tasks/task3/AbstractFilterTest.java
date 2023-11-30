package edu.hw6.tasks.task3;

import edu.testFileCreator.TestFilesCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static edu.testFileCreator.TestFilesCreator.combinePath;
import static edu.hw6.tasks.task3.AbstractFilter.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 3")
class AbstractFilterTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Paths.get(System.getProperty("user.dir")).resolve(".AbstractFilter"));

    @BeforeAll
    static void setUp() throws IOException {
        CREATOR.createDirectory();
        CREATOR.newTestFile(
            "file-1.png",
            new int[] {
                137, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 14, 0, 0, 0, 14, 8, 3, 0, 0, 0,
                40, 150, 221, 227, 0, 0, 2, 136, 80, 76, 84, 69, 255, 255, 255, 255, 255, 204, 255, 255, 153, 255, 255,
                102, 255, 255, 51, 255, 255, 0, 255, 204, 255, 255, 204, 204, 255, 204, 153, 255, 204, 102, 255, 204,
                51, 255, 204, 0, 255, 153, 255, 255, 153, 204, 255, 153, 153, 255, 153, 102, 255, 153, 51, 255, 153, 0,
                255, 102, 255, 255, 102, 204, 255, 102, 153, 255, 102, 102, 255, 102, 51, 255, 102, 0, 255, 51, 255,
                255, 51, 204, 255, 51, 153, 255, 51, 102, 255, 51, 51, 255, 51, 0, 255, 0, 255, 255, 0, 204, 255, 0,
                153, 255, 0, 102, 255, 0, 51, 255, 0, 0, 204, 255, 255, 204, 255, 204, 204, 255, 153, 204, 255, 102,
                204, 255, 51, 204, 255, 0, 204, 204, 255, 204, 204, 204, 204, 204, 153, 204, 204, 102, 204, 204, 51,
                204, 204, 0, 204, 153, 255, 204, 153, 204, 204, 153, 153, 204, 153, 102, 204, 153, 51, 204, 153, 0, 204,
                102, 255, 204, 102, 204, 204, 102, 153, 204, 102, 102, 204, 102, 51, 204, 102, 0, 204, 51, 255, 204, 51,
                204, 204, 51, 153, 204, 51, 102, 204, 51, 51, 204, 51, 0, 204, 0, 255, 204, 0, 204, 204, 0, 153, 204, 0,
                102, 204, 0, 51, 204, 0, 0, 153, 255, 255, 153, 255, 204, 153, 255, 153, 153, 255, 102, 153, 255, 51,
                153, 255, 0, 153, 204, 255, 153, 204, 204, 153, 204, 153, 153, 204, 102, 153, 204, 51, 153, 204, 0, 153,
                153, 255, 153, 153, 204, 153, 153, 153, 153, 153, 102, 153, 153, 51, 153, 153, 0, 153, 102, 255, 153,
                102, 204, 153, 102, 153, 153, 102, 102, 153, 102, 51, 153, 102, 0, 153, 51, 255, 153, 51, 204, 153, 51,
                153, 153, 51, 102, 153, 51, 51, 153, 51, 0, 153, 0, 255, 153, 0, 204, 153, 0, 153, 153, 0, 102, 153, 0,
                51, 153, 0, 0, 102, 255, 255, 102, 255, 204, 102, 255, 153, 102, 255, 102, 102, 255, 51, 102, 255, 0,
                102, 204, 255, 102, 204, 204, 102, 204, 153, 102, 204, 102, 102, 204, 51, 102, 204, 0, 102, 153, 255,
                102, 153, 204, 102, 153, 153, 102, 153, 102, 102, 153, 51, 102, 153, 0, 102, 102, 255, 102, 102, 204,
                102, 102, 153, 102, 102, 102, 102, 102, 51, 102, 102, 0, 102, 51, 255, 102, 51, 204, 102, 51, 153, 102,
                51, 102, 102, 51, 51, 102, 51, 0, 102, 0, 255, 102, 0, 204, 102, 0, 153, 102, 0, 102, 102, 0, 51, 102,
                0, 0, 51, 255, 255, 51, 255, 204, 51, 255, 153, 51, 255, 102, 51, 255, 51, 51, 255, 0, 51, 204, 255, 51,
                204, 204, 51, 204, 153, 51, 204, 102, 51, 204, 51, 51, 204, 0, 51, 153, 255, 51, 153, 204, 51, 153, 153,
                51, 153, 102, 51, 153, 51, 51, 153, 0, 51, 102, 255, 51, 102, 204, 51, 102, 153, 51, 102, 102, 51, 102,
                51, 51, 102, 0, 51, 51, 255, 51, 51, 204, 51, 51, 153, 51, 51, 102, 51, 51, 51, 51, 51, 0, 51, 0, 255,
                51, 0, 204, 51, 0, 153, 51, 0, 102, 51, 0, 51, 51, 0, 0, 0, 255, 255, 0, 255, 204, 0, 255, 153, 0, 255,
                102, 0, 255, 51, 0, 255, 0, 0, 204, 255, 0, 204, 204, 0, 204, 153, 0, 204, 102, 0, 204, 51, 0, 204, 0,
                0, 153, 255, 0, 153, 204, 0, 153, 153, 0, 153, 102, 0, 153, 51, 0, 153, 0, 0, 102, 255, 0, 102, 204, 0,
                102, 153, 0, 102, 102, 0, 102, 51, 0, 102, 0, 0, 51, 255, 0, 51, 204, 0, 51, 153, 0, 51, 102, 0, 51, 51,
                0, 51, 0, 0, 0, 255, 0, 0, 204, 0, 0, 153, 0, 0, 102, 0, 0, 51, 238, 0, 0, 168, 246, 239, 112, 0, 0, 0,
                33, 116, 69, 88, 116, 83, 111, 102, 116, 119, 97, 114, 101, 0, 71, 114, 97, 112, 104, 105, 99, 67, 111,
                110, 118, 101, 114, 116, 101, 114, 32, 40, 73, 110, 116, 101, 108, 41, 119, 135, 250, 25, 0, 0, 0, 33,
                73, 68, 65, 84, 120, 156, 98, 96, 96, 184, 14, 7, 12, 32, 128, 193, 101, 64, 166, 169, 202, 197, 103,
                47, 217, 0, 0, 0, 0, 255, 255, 3, 0, 90, 182, 53, 193, 186, 212, 169, 248, 0, 0, 0, 0, 73, 69, 78, 68,
                174, 66, 96, 130
            }
        );
        CREATOR.newTestFile(
            "file-2.md",
            "# Домашнее задание №6\n" +
                "\n" +
                "## Задание 1\n" +
                "\n" +
                "Реализуйте класс `DiskMap`, который представляет собой ассоциативный массив, хранящий пары \n" +
                "ключ-значение на жестком диске. Класс должен реализовывать интерфейс `Map<String, String>`.\n" +
                "\n" +
                "Ключи и значения должны быть сохранены на жестком диске в файле в формате \"ключ:значение\".\n" +
                "Класс должен поддерживать сохранение и загрузку из файла на диске.\n" +
                "\n" +
                "## Задание 2\n" +
                "\n" +
                "При копировании файла в ту же папку в Проводнике Windows создается его копия,\n" +
                "копия автоматически получает новое имя. Воспроизведём это поведение.\n" +
                "\n" +
                "Напишите функцию `cloneFile(Path path)`, которая создает копию файла с новым именем.\n" +
                "\n" +
                "Например, файл называется **Tinkoff Bank Biggest Secret.txt**. Тогда новые имена\n" +
                "файлов должны выглядеть следующим образом:\n" +
                "```\n" +
                "Tinkoff Bank Biggest Secret.txt\n" +
                "Tinkoff Bank Biggest Secret — копия.txt\n" +
                "Tinkoff Bank Biggest Secret — копия (2).txt\n" +
                "Tinkoff Bank Biggest Secret — копия (3).txt\n" +
                "```\n" +
                "\n" +
                "## Задание 3\n" +
                "\n" +
                "Класс **Files** предоставляет три статических метода для запроса всех записей в каталоге:\n" +
                "```java\n" +
                "newDirectoryStream(Path dir)\n" +
                "newDirectoryStream(Path dir, String glob)\n" +
                "newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter)\n" +
                "```\n" +
                "Результатом всегда является `DirectoryStream<Path>`.\n" +
                "Первый метод не фильтрует результаты, второй метод позволяет использовать glob-строку,\n" +
                "например ***.txt**, а третий метод позволяет использовать произвольный фильтр.\n" +
                "\n" +
                "`java.nio.file.DirectoryStream.Filter<T>` - это интерфейс-предикат, который должны реализовывать фильтры. В JDK объявлен интерфейс, но нет реализаций.\n" +
                "\n" +
                "Напишите реализации для `DirectoryStream.Filter`, которые проверяют:\n" +
                "\n" +
                "* атрибуты (например, readable, writable)\n" +
                "* размер файла\n" +
                "* расширения файлов\n" +
                "* имя файла с помощью регулярных выражений\n" +
                "* магические начальные идентификаторы\n" +
                "\n" +
                "Итоговый API нужно сделать цепочечным, т.е. должна быть возможность объединить все фильтры в один:\n" +
                "```java\n" +
                "public interface AbstractFilter extends DirectoryStream.Filter<Path> {\n" +
                "    // TODO\n" +
                "}\n" +
                "\n" +
                "public static final AbstractFilter regularFile = Files::isRegularFile;\n" +
                "public static final AbstractFilter readable = Files::isReadable;\n" +
                "\n" +
                "\n" +
                "DirectoryStream.Filter<Path> filter = regularFile\n" +
                "    .and(readable)\n" +
                "    .and(largerThan(100_000))\n" +
                "    .and(magicNumber(0x89, 'P', 'N', 'G'))\n" +
                "    .and(globMatches(\"*.png\"))\n" +
                "    .and(regexContains(\"[-]\"));\n" +
                "\n" +
                "try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {\n" +
                "    entries.forEach(System.out::println);\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "## Задание 4\n" +
                "\n" +
                "В этом задании мы будем делать композицию `OutputStream`'ов, стрелка -> указывает, кто куда пишет:\n" +
                "```\n" +
                "PrintWriter -> OutputStreamWriter -> BufferedOutputStream -> CheckedOutputStream -> file OutputStream.\n" +
                "```\n" +
                "\n" +
                "При построении цепочек такого типа всегда следует начинать с самого нижнего уровня:\n" +
                "\n" +
                "1. Создайте файл (`Files.new*(...)`) и получите из него `OutputStream`\n" +
                "2. Добавьте к нему `CheckedOutputStream` для проверки записи при помощи контрольной суммы\n" +
                "3. Для буферизации данных добавьте `BufferedOutputStream`\n" +
                "4. Чтобы не работать с сырыми байтами добавьте `OutputStreamWriter`, \n" +
                "и включите поддержку **UTF-8**.\n" +
                "5. Добавьте финальный `PrintWriter` и запишите в файл текст:\n" +
                "```\n" +
                "Programming is learned by writing programs. ― Brian Kernighan\n" +
                "```\n" +
                "Не забудьте закрыть ресурсы с помощью **try-with-resources**.\n" +
                "\n" +
                "## Задание 5\n" +
                "\n" +
                "Hacker News - это сайт с актуальными обсуждениями технологических тенденций.\n" +
                "Доступ к статьям осуществляется через веб-сервис, а документацию можно найти\n" +
                "на [сайте](https://github.com/HackerNews/API).\n" +
                "\n" +
                "Нас интересуют 2 endpoint'а:\n" +
                "\n" +
                "1. [Endpoint №1](https://hacker-news.firebaseio.com/v0/topstories.json): \n" +
                "возвращает JSON-массив с идентификаторами наиболее обсуждаемых статей.\n" +
                "2. [Endpoint №2](https://hacker-news.firebaseio.com/v0/item/37570037.json): \n" +
                "возвращает JSON-объект, содержащий сообщение с идентификатором 37570037.\n" +
                "\n" +
                "### Задание\n" +
                "\n" +
                "Создайте класс `HackerNews`.\n" +
                "\n" +
                "Реализуйте метод `long[] hackerNewsTopStories()`, который будет\n" +
                "\n" +
                "* делать HTTP-запрос при помощи `HttpClient` к [первому Endpoint'у](https://hacker-news.firebaseio.com/v0/topstories.json)\n" +
                "* конвертировать возвращаемый JSON в `long[]`\n" +
                "\n" +
                "_В общем случае для чтения JSON используются специальные парсеры,\n" +
                "но т.к. структура массива очень простая, то можем обойтись без них.\n" +
                "В реальном приложении, конечно ,мы бы использовали библиотеку, например, Jackson._\n" +
                "\n" +
                "В случае ошибки ввода-вывода должен быть возвращен пустой массив.\n" +
                "\n" +
                "Напишите метод `String news(long id)`, который возвращает название новости.\n" +
                "\n" +
                "Для получения имени новости используйте регулярное выражение.\n" +
                "\n" +
                "Пример:\n" +
                "```java\n" +
                "System.out.println(Arrays.toString(hackerNewsTopStories()));\n" +
                "String newsTitle = news(37570037);\n" +
                "System.out.println(newsTitle);\n" +
                "```\n" +
                "\n" +
                "## Задание 6\n" +
                "\n" +
                "Напишите программу, которая сканирует порты и определяет заняты они или нет.\n" +
                "\n" +
                "Для этого нужно зарегистрировать `ServerSocket` и `DatagramSocket` на всех\n" +
                "**TCP/UDP**-портах от [0 до 49151](https://en.wikipedia.org/wiki/Registered_port).\n" +
                "\n" +
                "В случае успеха порт свободен, в противном случае он занят.\n" +
                "\n" +
                "Дополнительно выведите информацию о потенциальном приложении, которое использует этот\n" +
                "порт, [список известных портов](https://ru.wikipedia.org/wiki/Список_портов_TCP_и_UDP).\n" +
                "\n" +
                "Выберите несколько, не нужно брать всё, [например](https://www.tutorialspoint.com/50-common-ports-you-should-know).\n" +
                "\n" +
                "Пример вывода программы:\n" +
                "```\n" +
                "Протокол  Порт   Сервис\n" +
                "TCP       135    EPMAP\n" +
                "UDP       137    Служба имен NetBIOS\n" +
                "UDP       138    Служба датаграмм NetBIOS\n" +
                "TCP       139    Служба сеансов NetBIOS\n" +
                "TCP       445    Microsoft-DS Active Directory\n" +
                "TCP       843    Adobe Flash\n" +
                "UDP       1900   Simple Service Discovery Protocol (SSDP)\n" +
                "UDP       3702   Динамическое обнаружение веб-служб\n" +
                "TCP       5040   \n" +
                "UDP       5050   \n" +
                "UDP       5353   Многоадресный DNS\n" +
                "UDP       5355   Link-Local Multicast Name Resolution (LLMNR)\n" +
                "TCP       5939   \n" +
                "TCP       6463   \n" +
                "TCP       6942   \n" +
                "TCP       17500  Dropbox\n" +
                "UDP       17500  Dropbox\n" +
                "TCP       17600\n" +
                "TCP       27017  MongoDB\n" +
                "UDP       42420\n" +
                "```\n"
        );
        CREATOR.newTestFile(
            "file-3.png",
            new int[] {
                137, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 10, 0, 0, 0, 10, 8, 2, 0, 0, 0,
                2, 80, 88, 234, 0, 0, 0, 9, 112, 72, 89, 115, 0, 0, 22, 37, 0, 0, 22, 37, 1, 73, 82, 36, 240, 0, 0, 1,
                31, 73, 68, 65, 84, 24, 149, 99, 252, 255, 255, 63, 3, 18, 56, 118, 104, 195, 137, 67, 71, 101, 21, 229,
                148, 53, 180, 245, 13, 108, 24, 145, 165, 159, 61, 60, 189, 168, 161, 70, 86, 94, 146, 131, 245, 231,
                150, 61, 7, 237, 163, 82, 80, 164, 119, 46, 238, 254, 245, 232, 238, 143, 95, 95, 255, 50, 253, 150, 83,
                224, 189, 249, 248, 19, 19, 178, 201, 202, 178, 146, 143, 31, 60, 250, 254, 249, 171, 130, 162, 22, 59,
                59, 191, 181, 133, 45, 84, 247, 239, 223, 191, 239, 220, 189, 189, 115, 94, 19, 235, 203, 151, 95, 126,
                255, 101, 248, 255, 137, 155, 235, 143, 136, 67, 10, 227, 227, 123, 87, 143, 108, 94, 151, 222, 183,
                253, 231, 231, 159, 178, 226, 172, 220, 255, 31, 49, 255, 249, 193, 248, 251, 199, 79, 38, 142, 251,
                159, 56, 152, 53, 133, 126, 252, 253, 240, 82, 88, 84, 72, 85, 94, 236, 195, 79, 129, 191, 172, 66, 31,
                255, 112, 124, 97, 17, 253, 197, 42, 173, 174, 164, 202, 226, 237, 160, 199, 250, 239, 111, 18, 199,
                155, 142, 165, 23, 246, 159, 251, 165, 36, 39, 199, 193, 198, 198, 201, 193, 201, 206, 206, 203, 204,
                252, 135, 209, 199, 74, 86, 71, 94, 244, 230, 221, 251, 199, 31, 177, 242, 138, 105, 241, 112, 178, 8,
                240, 139, 51, 179, 114, 125, 254, 244, 242, 251, 183, 111, 204, 236, 226, 198, 71, 110, 190, 125, 248,
                93, 84, 90, 74, 133, 225, 239, 251, 127, 255, 126, 48, 51, 49, 48, 252, 255, 241, 239, 207, 31, 33, 97,
                49, 0, 217, 27, 129, 153, 102, 101, 27, 105, 0, 0, 0, 0, 73, 69, 78, 68, 174, 66, 96, 130
            }
        );
        CREATOR.newTestFile(
            "file-4.java",
            "public final class Main {\n" +
                "    private Main() {\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, world!\");\n" +
                "    }\n" +
                "}\n"
        );
        CREATOR.newTestFile(
            "file-5.java",
            "package edu.hw6.tasks;\n" +
                "\n" +
                "import java.io.IOException;\n" +
                "import java.nio.ByteBuffer;\n" +
                "import java.nio.channels.FileChannel;\n" +
                "import java.nio.charset.StandardCharsets;\n" +
                "import java.nio.file.Files;\n" +
                "import java.nio.file.Path;\n" +
                "import java.util.*;\n" +
                "import static java.nio.file.StandardOpenOption.CREATE_NEW;\n" +
                "import static java.nio.file.StandardOpenOption.WRITE;\n" +
                "\n" +
                "public record TestFilesCreator(Path root) {\n" +
                "\n" +
                "    public void createDirectory() throws IOException {\n" +
                "        Files.createDirectories(root);\n" +
                "    }\n" +
                "\n" +
                "    public void newTestFile(final Path file, final byte[] bytes) throws IOException {\n" +
                "        final Path path = root.resolve(file);\n" +
                "        if (!Files.exists(path.getParent())) {\n" +
                "            Files.createDirectories(path.getParent());\n" +
                "        }\n" +
                "        FileChannel outChannel = FileChannel.open(path, WRITE, CREATE_NEW);\n" +
                "        outChannel.write(ByteBuffer.wrap(bytes));\n" +
                "        outChannel.close();\n" +
                "    }\n" +
                "\n" +
                "    public void newTestFile(final String file, final byte[] bytes) throws IOException {\n" +
                "        newTestFile(Path.of(file), bytes);\n" +
                "    }\n" +
                "\n" +
                "    public void newTestFile(final Path file, final String text) throws IOException {\n" +
                "        newTestFile(file, text.getBytes(StandardCharsets.UTF_8));\n" +
                "    }\n" +
                "\n" +
                "    public void newTestFile(final String file, final String text) throws IOException {\n" +
                "        newTestFile(Path.of(file), text.getBytes(StandardCharsets.UTF_8));\n" +
                "    }\n" +
                "\n" +
                "    public void newTestFile(final Path file, final int[] bigBytes) throws IOException {\n" +
                "        final List<Byte> bytes = Arrays.stream(bigBytes).mapToObj(x -> (byte) x).toList();\n" +
                "        final byte[] rawBytes = new byte[bytes.size()];\n" +
                "        for (int i = 0; i < rawBytes.length; i++) {\n" +
                "            rawBytes[i] = bytes.get(i);\n" +
                "        }\n" +
                "        newTestFile(file, rawBytes);\n" +
                "    }\n" +
                "\n" +
                "    public void newTestFile(final String file, final int[] bigBytes) throws IOException {\n" +
                "        newTestFile(Path.of(file), bigBytes);\n" +
                "    }\n" +
                "\n" +
                "    public static Path combinePath(final String... paths) {\n" +
                "        Path result = Path.of(\"\");\n" +
                "        for (final String path : paths) {\n" +
                "            result = result.resolve(path);\n" +
                "        }\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    public void deleteDirectory() throws IOException {\n" +
                "        Files.walkFileTree(root, new DeleteRecursiveFileVisitor());\n" +
                "    }\n" +
                "}\n"
        );
        CREATOR.newTestFile(
            combinePath("folder", "file-6.png"),
            new int[] {
                137, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 10, 0, 0, 0, 10, 8, 2, 0, 0, 0,
                2, 80, 88, 234, 0, 0, 0, 9, 112, 72, 89, 115, 0, 0, 22, 37, 0, 0, 22, 37, 1, 73, 82, 36, 240, 0, 0, 1,
                6, 73, 68, 65, 84, 24, 149, 125, 144, 191, 78, 194, 112, 24, 69, 239, 215, 214, 161, 4, 218, 193, 95,
                13, 70, 104, 3, 40, 14, 134, 197, 209, 201, 221, 201, 221, 209, 81, 31, 193, 201, 215, 112, 244, 17, 76,
                220, 132, 96, 194, 38, 155, 131, 64, 99, 248, 51, 152, 150, 150, 70, 90, 105, 11, 244, 115, 32, 198,
                196, 193, 187, 158, 179, 156, 75, 252, 213, 133, 123, 139, 213, 0, 127, 166, 236, 195, 184, 33, 30, 158,
                111, 24, 83, 254, 213, 189, 4, 112, 100, 220, 17, 135, 27, 131, 216, 110, 0, 232, 185, 23, 182, 99, 246,
                236, 15, 0, 245, 90, 177, 182, 51, 170, 27, 247, 0, 36, 166, 92, 171, 127, 253, 216, 217, 114, 189, 196,
                247, 124, 111, 234, 57, 211, 248, 225, 89, 106, 189, 93, 49, 229, 20, 146, 68, 16, 233, 197, 93, 45, 75,
                39, 121, 229, 221, 60, 60, 99, 230, 82, 185, 28, 44, 136, 36, 33, 113, 230, 168, 170, 156, 166, 201,
                122, 25, 98, 237, 185, 227, 246, 204, 119, 226, 56, 81, 85, 153, 51, 71, 34, 142, 171, 98, 80, 40, 104,
                243, 48, 137, 150, 219, 141, 3, 57, 141, 38, 186, 174, 85, 69, 159, 56, 86, 0, 84, 196, 83, 152, 168,
                243, 160, 148, 46, 102, 237, 23, 222, 179, 44, 83, 140, 42, 162, 9, 198, 111, 216, 231, 234, 164, 59,
                60, 5, 232, 216, 106, 106, 74, 231, 39, 236, 223, 91, 190, 1, 187, 205, 127, 105, 93, 135, 56, 43, 0, 0,
                0, 0, 73, 69, 78, 68, 174, 66, 96, 130
            }
        );
        CREATOR.newTestFile(
            combinePath("folder", "file-7.md"),
            "Домашнее задание № 2\n" +
                "====================\n" +
                "**Напоминание: для всех заданий нужно написать тесты (где это возможно)**\n" +
                "\n" +
                "## 1. Калькулятор выражений\n" +
                "Напишите иерархию классов для вычисления математических выражений.\n" +
                "```java\n" +
                "public sealed interface Expr {\n" +
                "    double evaluate();\n" +
                "\n" +
                "    public record Constant implements Expr {}\n" +
                "    public record Negate implements Expr {}\n" +
                "    public record Exponent implements Expr {}\n" +
                "    public record Addition implements Expr {}\n" +
                "    public record Multiplication implements Expr {}\n" +
                "}\n" +
                "```\n" +
                "Разбор строковых представлений выражений не требуется, достаточно чтобы работал код вида\n" +
                "```java\n" +
                "    var two = new Constant(2);\n" +
                "    var four = new Constant(4);\n" +
                "    var negOne = new Negate(new Constant(1));\n" +
                "    var sumTwoFour = new Addition(two, four);\n" +
                "    var mult = new Multiplication(sumTwoFour, negOne);\n" +
                "    var exp = new Exponent(mult, 2);\n" +
                "    var res = new Addition(exp, new Constant(1));\n" +
                "\n" +
                "    System.out.println(res + \" = \" + res.evaluate());\n" +
                "```\n" +
                "\n" +
                "## 2. Квадрат и прямоугольник\n" +
                "Что может быть проще наследования... Думают все начинающие программисты.\n" +
                "\n" +
                "На практике оказывается, что применение наследования очень ограничено и в \n" +
                "реальности **почти всегда** лучше использовать композицию или относительно \n" +
                "\"глупые\" sealed-интерфейсы (ADT).\n" +
                "\n" +
                "Чтобы продемонстрировать утверждение выше, попробуем разрешить классический \n" +
                "парадокс прямоугольника и квадрата.\n" +
                "\n" +
                "Допустим, у вас есть классы **Rectangle** и **Square** с методами **setWidth**, **setHeight** и \n" +
                "**area**:\n" +
                "\n" +
                "```java\n" +
                "public class Rectangle {  \n" +
                "    private int width;  \n" +
                "    private int height;\n" +
                "\n" +
                "    void setWidth(int width) {  \n" +
                "        this.width = width;  \n" +
                "    }  \n" +
                "  \n" +
                "    void setHeight(int height) {  \n" +
                "        this.height = height;  \n" +
                "    }  \n" +
                "  \n" +
                "    double area() {  \n" +
                "        return width * height;  \n" +
                "    }  \n" +
                "}\n" +
                "\n" +
                "public class Square extends Rectangle {  \n" +
                "    @Override  \n" +
                "    void setWidth(int width) {  \n" +
                "        super.setHeight(width);  \n" +
                "        super.setWidth(width);  \n" +
                "    }\n" +
                "\n" +
                "    @Override  \n" +
                "    void setHeight(int height) {  \n" +
                "        super.setHeight(height);  \n" +
                "        super.setWidth(height);  \n" +
                "    }  \n" +
                "}\n" +
                "```\n" +
                "И есть следующий тест:\n" +
                "```java\n" +
                "static Arguments[] rectangles() {  \n" +
                "    return new Arguments[]{\n" +
                "        Arguments.of(new Rectangle()),\n" +
                "        Arguments.of(new Square())\n" +
                "    };  \n" +
                "}\n" +
                "\n" +
                "@ParameterizedTest  \n" +
                "@MethodSource(\"rectangles\")  \n" +
                "void rectangleArea(Rectangle rect) {  \n" +
                "    rect.setWidth(20);  \n" +
                "    rect.setHeight(10);\n" +
                "\n" +
                "    assertThat(rect.area()).isEqualTo(200.0);  \n" +
                "}\n" +
                "```\n" +
                "Если вы запустите этот код, то вы увидите, что один из тестов падает.\n" +
                "\n" +
                "Проблема этого кода заключается в нарушении принципа подстановки Лисков:\n" +
                "\n" +
                "> если можно написать хоть какой-то осмысленный код, в котором замена объекта\n" +
                "> базового класса на объекта класса потомка, его сломает, то тогда не стоит их\n" +
                "> друг от друга-то наследовать.\n" +
                "> \n" +
                "> Мы должны расширять поведение базового класса в потомках, а не существенным \n" +
                "> образом изменять его. Функции, которые используют базовый класс, должны иметь \n" +
                "> возможность использовать объекты подклассов, не зная об этом.\n" +
                "\n" +
                "К сожалению проблемы с LSP встречаются постоянно даже в крупных проектах, например, в JDK:\n" +
                "\n" +
                "```java\n" +
                "static Arguments[] lists() {  \n" +
                "    return new Arguments[]{  \n" +
                "        Arguments.of(new ArrayList<Integer>()),  \n" +
                "        Arguments.of(Collections.unmodifiableList(new ArrayList<Integer>()))  \n" +
                "    };  \n" +
                "}\n" +
                "\n" +
                "@ParameterizedTest  \n" +
                "@MethodSource(\"lists\")  \n" +
                "void addElement(List<Integer> list) {  \n" +
                "    list.add(1);\n" +
                "\n" +
                "    assertThat(list).hasSize(1).containsExactly(1);  \n" +
                "}\n" +
                "```\n" +
                "### Задание\n" +
                "\n" +
                "Найдите решение проблемы, удовлетворяющее следующим ограничениям:\n" +
                "\n" +
                "* Не нарушается математическая логика, то есть квадрат является прямоугольником с точки зрения типизации\n" +
                "* При этом не нарушается принцип подстановки\n" +
                "* Все так же присутствует понятие высоты и ширины, а также операция вычисления площади\n" +
                "* Реализация класса **Rectangle** не должна предполагать существование класса **Square**, иными словами, не нарушен принцип открытости-закрытости\n" +
                "\n" +
                "\n" +
                "Если всё-таки не получится придумать решение самостоятельно, то воспользуйтесь подсказками ниже:\n" +
                "\n" +
                "* подсказка 1: методы **setWidth**/**setHeight** могут что-то возвращать\n" +
                "* подсказка 2: может пригодиться ключевое слово final\n" +
                "\n" +
                "## 3. Удаленный сервер\n" +
                "\n" +
                "Программист Иван хочет выполнять часто используемые консольные команды \n" +
                "на удаленном сервере из Java-программы.\n" +
                "\n" +
                "В распоряжении Ивана следующий набор интерфейсов:\n" +
                "```java\n" +
                "public interface Connection extends AutoCloseable {\n" +
                "    void execute(String command);\n" +
                "}\n" +
                "\n" +
                "public interface ConnectionManager {\n" +
                "    Connection getConnection();\n" +
                "}\n" +
                "\n" +
                "public class ConnectionException extends RuntimeException {}\n" +
                "\n" +
                "public final class PopularCommandExecutor {\n" +
                "    private final ConnectionManager manager;\n" +
                "    private final int maxAttempts;\n" +
                "\n" +
                "    void updatePackages() {\n" +
                "        tryExecute(\"apt update && apt upgrade -y\");\n" +
                "    }\n" +
                "\n" +
                "    private void tryExecute(String command) { ... }\n" +
                "}\n" +
                "```\n" +
                "Пояснение:\n" +
                "\n" +
                "* работа с сервером происходит через **Connection**, у которого есть метод **execute**\n" +
                "* чтобы получить соединение используется **ConnectionManager**\n" +
                "* при выполнении команды может возникнуть исключение **ConnectionException**\n" +
                "\n" +
                "### Задание\n" +
                "\n" +
                "Помогите Ивану и реализуйте:\n" +
                "\n" +
                "* 2 типа соединений: **StableConnection** / **FaultyConnection**, \n" +
                "стабильное соединение работает всегда, проблемное соединение иногда \n" +
                "бросает **ConnectionException**\n" +
                "* **DefaultConnectionManager**, который с некоторой вероятностью \n" +
                "возвращает проблемное соединение\n" +
                "* **FaultyConnectionManager**, который всегда возвращает проблемное соединение\n" +
                "* Метод **tryExecute**, который должен попытаться выполнить \n" +
                "переданную команду **maxAttempts** раз\n" +
                "* Если **tryExecute** не смог выполнить команду (превышено количество попыток), \n" +
                "то нужно вернуть **ConnectionException**, при этом сохранив оригинальное \n" +
                "исключение в параметре cause\n" +
                "* Обратите внимание, что **Connection** требуется закрывать (интерфейс **AutoCloseable**)\n" +
                "\n" +
                "## 4. Кто вызвал функцию?\n" +
                "\n" +
                "Напишите статическую функцию **callingInfo**, которая возвращает\n" +
                "\n" +
                "```java\n" +
                "public record CallingInfo(String className, String methodName) {}\n" +
                "```\n" +
                "Для получения доступа к стеку вызова используйте метод **Throwable#getStackTrace**.\n"
        );
    }

    public static Stream<Arguments> testAbstractFilter() {
        return Stream.of(
            Arguments.of(
                regularFile
                    .and(readable)
                    .and(writable)
                    .and(largerThan(10))
                    .and(magicNumber(0x89, 'P', 'N', 'G'))
                    .and(globMatches("*.png"))
                    .and(regexContains("[-]")),
                Path.of(".AbstractFilter"),
                List.of(
                    combinePath(".AbstractFilter", "file-3.png"),
                    combinePath(".AbstractFilter", "file-1.png")
                )
            ),
            Arguments.of(
                regularFile
                    .and(writable)
                    .and(readable)
                    .and(extension("md"))
                    .and(lessEqThan(100_000))
                    .and(globMatches("file-?.md")),
                Path.of(".AbstractFilter"),
                List.of(
                    combinePath(".AbstractFilter", "file-2.md")
                )
            ),
            Arguments.of(
                regularFile
                    .and(writable)
                    .and(readable)
                    .and(globMatches("*.png")),
                Path.of(".AbstractFilter"),
                List.of(
                    combinePath(".AbstractFilter", "file-3.png"),
                    combinePath(".AbstractFilter", "file-1.png")
                )
            ),
            Arguments.of(
                regularFile
                    .and(writable)
                    .and(readable)
                    .and(globMatches("*.png")),
                combinePath(".AbstractFilter", "folder"),
                List.of(
                    combinePath(".AbstractFilter", "folder", "file-6.png")
                )
            ),
            Arguments.of(
                regularFile
                    .and(extension("java"))
                    .and(AbstractFilter.equals(152)),
                Path.of(".AbstractFilter"),
                List.of(
                    combinePath(".AbstractFilter", "file-4.java")
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAbstractFilter(final AbstractFilter input, final Path files, final List<Path> output) throws IOException {
        ArrayList<Path> filteredFiles = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(files, input)) {
            entries.forEach(filteredFiles::add);
        } catch (IOException e) {
            throw new IOException(e);
        }
        assertThat(filteredFiles).isEqualTo(output);
    }

    @AfterAll
    static void tearDown() throws IOException {
        CREATOR.deleteDirectory();
    }
}
