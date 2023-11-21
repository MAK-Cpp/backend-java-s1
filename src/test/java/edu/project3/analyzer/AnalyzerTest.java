package edu.project3.analyzer;

import edu.project3.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static edu.project3.analyzer.TestFilesCreator.combinePath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Test analyzer")
public class AnalyzerTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Path.of(System.getProperty("user.dir")).resolve(".project3"));

    @BeforeAll
    static void setUp() throws IOException {
        CREATOR.createDirectory();
        CREATOR.newTestFile(
            combinePath("logs", "log1"),
            "124.62.158.227 - - [17/Nov/2023:17:35:51 +0000] \"HEAD /open%20architecture/open%20system-Synergistic-radical.php HTTP/1.1\" 200 1494 \"-\" \"Mozilla/5.0 (Windows NT 4.0) AppleWebKit/5320 (KHTML, like Gecko) Chrome/37.0.820.0 Mobile Safari/5320\"\n" +
                "97.194.109.102 - - [17/Nov/2023:17:35:51 +0000] \"DELETE /multi-state_Persistent-open%20architecture-Centralized-Extended.jpg HTTP/1.1\" 200 1163 \"-\" \"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/5360 (KHTML, like Gecko) Chrome/40.0.817.0 Mobile Safari/5360\"\n" +
                "84.18.15.218 - - [17/Nov/2023:17:35:51 +0000] \"DELETE /Horizontal.jpg HTTP/1.1\" 200 1617 \"-\" \"Mozilla/5.0 (Windows NT 5.0; en-US; rv:1.9.0.20) Gecko/1949-11-02 Firefox/36.0\"\n" +
                "236.45.44.131 - - [17/Nov/2023:17:35:51 +0000] \"GET /dedicated/grid-enabled-algorithm.js HTTP/1.1\" 200 2531 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_8_9 rv:6.0) Gecko/1903-20-09 Firefox/35.0\"\n" +
                "184.24.250.128 - - [17/Nov/2023:17:35:51 +0000] \"GET /User-friendly/instruction%20set_executive/client-server.js HTTP/1.1\" 200 1544 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_7_0 rv:7.0) Gecko/1908-30-08 Firefox/35.0\"\n" +
                "148.253.42.228 - - [17/Nov/2023:17:35:51 +0000] \"GET /impactful-user-facing.php HTTP/1.1\" 200 936 \"-\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_4 rv:7.0; en-US) AppleWebKit/534.35.1 (KHTML, like Gecko) Version/4.0 Safari/534.35.1\"\n" +
                "151.199.10.177 - - [18/Nov/2023:17:35:51 +0000] \"GET /Virtual.js HTTP/1.1\" 200 2132 \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 4.0) AppleWebKit/533.26.3 (KHTML, like Gecko) Version/5.0 Safari/533.26.3\"\n" +
                "247.168.243.54 - - [18/Nov/2023:17:35:51 +0000] \"GET /optimal/Decentralized/Monitored-encryption%20stable.htm HTTP/1.1\" 200 2385 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_5_9 rv:5.0; en-US) AppleWebKit/536.8.1 (KHTML, like Gecko) Version/5.1 Safari/536.8.1\"\n" +
                "182.114.121.32 - - [18/Nov/2023:17:35:51 +0000] \"GET /leverage-secured%20line.jpg HTTP/1.1\" 500 95 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_4) AppleWebKit/5330 (KHTML, like Gecko) Chrome/37.0.879.0 Mobile Safari/5330\"\n" +
                "247.228.122.205 - - [18/Nov/2023:17:35:51 +0000] \"GET /methodology/Vision-oriented-leverage/Adaptive.png HTTP/1.1\" 200 1548 \"-\" \"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/5321 (KHTML, like Gecko) Chrome/37.0.892.0 Mobile Safari/5321\"\n" +
                "28.207.163.8 - - [18/Nov/2023:17:35:51 +0000] \"GET /projection/emulation-monitoring_software%20Synchronised.gif HTTP/1.1\" 400 75 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8) AppleWebKit/5331 (KHTML, like Gecko) Chrome/38.0.864.0 Mobile Safari/5331\"\n" +
                "103.201.197.184 - - [18/Nov/2023:17:35:51 +0000] \"PATCH /Multi-channelled/zero%20defect_pricing%20structure/Ameliorated-even-keeled.htm HTTP/1.1\" 200 960 \"-\" \"Mozilla/5.0 (iPad; CPU OS 7_2_3 like Mac OS X; en-US) AppleWebKit/536.12.8 (KHTML, like Gecko) Version/5.0.5 Mobile/8B111 Safari/6536.12.8\"6.244.7.56 - - [19/Nov/2023:17:36:01 +0000] \"GET /Compatible/Automated-local.jpg HTTP/1.1\" 200 1607 \"-\" \"Mozilla/5.0 (Windows 98) AppleWebKit/5342 (KHTML, like Gecko) Chrome/40.0.817.0 Mobile Safari/5342\"\n" +
                "178.198.77.127 - - [18/Nov/2023:17:36:01 +0000] \"GET /monitoring/Organized/neutral.jpg HTTP/1.1\" 200 2338 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/1945-16-04 Firefox/35.0\"\n" +
                "216.72.224.49 - - [19/Nov/2023:17:36:01 +0000] \"GET /contextually-based/Reduced/instruction%20set_holistic-responsive.css HTTP/1.1\" 301 89 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_7) AppleWebKit/5351 (KHTML, like Gecko) Chrome/37.0.815.0 Mobile Safari/5351\"\n" +
                "123.66.243.102 - - [19/Nov/2023:17:36:01 +0000] \"GET /Universal/initiative/Object-based.gif HTTP/1.1\" 200 1832 \"-\" \"Opera/10.77 (Macintosh; Intel Mac OS X 10_5_9; en-US) Presto/2.9.187 Version/12.00\"\n" +
                "92.180.179.125 - - [19/Nov/2023:17:36:01 +0000] \"GET /Visionary.svg HTTP/1.1\" 301 32 \"-\" \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/5322 (KHTML, like Gecko) Chrome/38.0.838.0 Mobile Safari/5322\"\n" +
                "227.92.82.128 - - [19/Nov/2023:17:36:01 +0000] \"GET /Focused-Exclusive.gif HTTP/1.1\" 200 1688 \"-\" \"Mozilla/5.0 (X11; Linux i686) AppleWebKit/5321 (KHTML, like Gecko) Chrome/39.0.855.0 Mobile Safari/5321\"\n" +
                "100.50.48.3 - - [19/Nov/2023:17:36:01 +0000] \"GET /frame/benchmark-Assimilated.hmtl HTTP/1.1\" 200 2342 \"-\" \"Opera/10.59 (X11; Linux i686; en-US) Presto/2.13.237 Version/13.00\"\n" +
                "215.204.156.124 - - [19/Nov/2023:17:36:01 +0000] \"GET /stable.jpg HTTP/1.1\" 200 3052 \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 6.0) AppleWebKit/534.26.6 (KHTML, like Gecko) Version/5.1 Safari/534.26.6\"\n" +
                "114.46.202.74 - - [19/Nov/2023:17:36:01 +0000] \"POST /incremental/zero%20defect/service-desk_Inverse.php HTTP/1.1\" 200 1805 \"-\" \"Mozilla/5.0 (Windows 98; Win 9x 4.90) AppleWebKit/5350 (KHTML, like Gecko) Chrome/40.0.837.0 Mobile Safari/5350\"\n"
        );
        CREATOR.newTestFile(
            combinePath("logs", "log2"),
            "247.166.23.127 - - [15/Nov/2023:21:44:16 +0000] \"PUT /Enhanced.php HTTP/1.1\" 200 2565 \"-\" \"Opera/9.69 (Macintosh; U; Intel Mac OS X 10_5_5; en-US) Presto/2.8.183 Version/10.00\"\n" +
                "170.121.175.50 - - [15/Nov/2023:21:45:17 +0000] \"GET /Optimized%20stable/software/Cloned-archive.php HTTP/1.1\" 200 2536 \"-\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_8_3 rv:7.0; en-US) AppleWebKit/535.45.6 (KHTML, like Gecko) Version/6.2 Safari/535.45.6\"\n" +
                "39.39.68.192 - - [15/Nov/2023:21:46:18 +0000] \"GET /asynchronous.png HTTP/1.1\" 200 3001 \"-\" \"Mozilla/5.0 (iPad; CPU OS 9_3_2 like Mac OS X; en-US) AppleWebKit/531.22.2 (KHTML, like Gecko) Version/5.0.5 Mobile/8B120 Safari/6531.22.2\"\n" +
                "238.227.43.15 - - [15/Nov/2023:21:47:19 +0000] \"DELETE /User-friendly.htm HTTP/1.1\" 200 897 \"-\" \"Opera/10.54 (X11; Linux i686; en-US) Presto/2.8.175 Version/13.00\"\n" +
                "179.103.151.65 - - [15/Nov/2023:21:48:20 +0000] \"POST /process%20improvement/Intuitive/Right-sized.css HTTP/1.1\" 200 2551 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_5 rv:7.0; en-US) AppleWebKit/535.28.7 (KHTML, like Gecko) Version/4.1 Safari/535.28.7\"\n" +
                "94.203.49.85 - - [15/Nov/2023:21:49:21 +0000] \"GET /Re-engineered/Centralized/solution-oriented.js HTTP/1.1\" 200 2328 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_6_10 rv:2.0) Gecko/2007-23-04 Firefox/35.0\"\n" +
                "138.15.6.91 - - [16/Nov/2023:21:50:22 +0000] \"GET /dynamic-dedicated-Reduced.htm HTTP/1.1\" 200 817 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:5.0) Gecko/1957-03-04 Firefox/36.0\"\n" +
                "74.71.181.56 - - [16/Nov/2023:21:51:22 +0000] \"GET /global-toolset.htm HTTP/1.1\" 200 1972 \"-\" \"Mozilla/5.0 (iPhone; CPU iPhone OS 7_2_3 like Mac OS X; en-US) AppleWebKit/536.17.1 (KHTML, like Gecko) Version/5.0.5 Mobile/8B120 Safari/6536.17.1\"\n" +
                "50.242.162.109 - - [16/Nov/2023:21:52:23 +0000] \"DELETE /Focused/national/content-based-hybrid%20dedicated.js HTTP/1.1\" 200 2856 \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/534.12.2 (KHTML, like Gecko) Version/6.0 Safari/534.12.2\"\n" +
                "82.117.143.207 - - [16/Nov/2023:21:53:24 +0000] \"GET /heuristic/Automated-Right-sized%20middleware.png HTTP/1.1\" 200 2669 \"-\" \"Opera/10.64 (Windows 98; en-US) Presto/2.13.335 Version/13.00\"\n" +
                "116.70.209.170 - - [16/Nov/2023:21:54:25 +0000] \"GET /impactful-background.jpg HTTP/1.1\" 200 1032 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_8_2) AppleWebKit/5310 (KHTML, like Gecko) Chrome/40.0.822.0 Mobile Safari/5310\"\n" +
                "101.180.245.12 - - [17/Nov/2023:21:55:26 +0000] \"GET /Quality-focused_contingency.htm HTTP/1.1\" 200 1626 \"-\" \"Mozilla/5.0 (X11; Linux x86_64; rv:6.0) Gecko/1908-27-10 Firefox/35.0\"\n" +
                "209.38.108.24 - - [17/Nov/2023:21:56:27 +0000] \"GET /Multi-tiered-monitoring/product.hmtl HTTP/1.1\" 302 98 \"-\" \"Mozilla/5.0 (Windows; U; Windows 98; Win 9x 4.90) AppleWebKit/531.4.3 (KHTML, like Gecko) Version/5.1 Safari/531.4.3\"\n" +
                "98.120.103.88 - - [17/Nov/2023:21:57:28 +0000] \"PUT /Robust/zero%20defect-Configurable.js HTTP/1.1\" 200 2350 \"-\" \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/5350 (KHTML, like Gecko) Chrome/39.0.876.0 Mobile Safari/5350\"\n" +
                "153.252.115.141 - - [17/Nov/2023:21:58:29 +0000] \"GET /approach/hierarchy/regional-User-centric-software.png HTTP/1.1\" 200 1195 \"-\" \"Mozilla/5.0 (Windows 98; en-US; rv:1.9.3.20) Gecko/1984-13-05 Firefox/36.0\"\n" +
                "47.191.129.195 - - [18/Nov/2023:21:59:30 +0000] \"GET /initiative.hmtl HTTP/1.1\" 200 2906 \"-\" \"Mozilla/5.0 (Windows NT 5.1; en-US; rv:1.9.0.20) Gecko/1981-28-01 Firefox/36.0\"\n" +
                "109.175.168.140 - - [18/Nov/2023:22:00:31 +0000] \"PATCH /product-intangible/demand-driven.htm HTTP/1.1\" 200 3024 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:7.0) Gecko/1994-06-11 Firefox/37.0\"\n" +
                "9.93.89.69 - - [18/Nov/2023:22:01:32 +0000] \"GET /budgetary%20management/exuding_optimal_bifurcated%20Virtual.php HTTP/1.1\" 200 2373 \"-\" \"Mozilla/5.0 (X11; Linux i686) AppleWebKit/5342 (KHTML, like Gecko) Chrome/40.0.896.0 Mobile Safari/5342\"\n" +
                "136.200.162.246 - - [18/Nov/2023:22:02:33 +0000] \"PUT /circuit.js HTTP/1.1\" 200 1302 \"-\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_8_1 rv:6.0) Gecko/1919-19-11 Firefox/36.0\"\n" +
                "159.203.93.254 - - [18/Nov/2023:22:03:34 +0000] \"GET /application_encompassing_projection/Extended/Graphic%20Interface.svg HTTP/1.1\" 200 1999 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_3) AppleWebKit/5311 (KHTML, like Gecko) Chrome/36.0.838.0 Mobile Safari/5311\"\n" +
                "65.184.234.232 - - [18/Nov/2023:22:04:35 +0000] \"GET /Function-based/system-worthy_Down-sized/Inverse.jpg HTTP/1.1\" 200 1845 \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 4.0) AppleWebKit/533.34.5 (KHTML, like Gecko) Version/5.0 Safari/533.34.5\"\n" +
                "118.139.85.173 - - [19/Nov/2023:22:05:36 +0000] \"GET /Persevering.svg HTTP/1.1\" 200 1674 \"-\" \"Opera/10.24 (Macintosh; U; PPC Mac OS X 10_8_1; en-US) Presto/2.12.170 Version/12.00\"\n" +
                "222.62.122.200 - - [19/Nov/2023:22:06:37 +0000] \"HEAD /framework/Synchronised-bifurcated-Synchronised_dedicated.css HTTP/1.1\" 200 1394 \"-\" \"Mozilla/5.0 (iPad; CPU OS 8_2_2 like Mac OS X; en-US) AppleWebKit/533.21.8 (KHTML, like Gecko) Version/5.0.5 Mobile/8B115 Safari/6533.21.8\"\n" +
                "79.250.136.220 - - [19/Nov/2023:22:07:38 +0000] \"GET /Compatible/needs-based.png HTTP/1.1\" 200 2852 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_8 rv:7.0) Gecko/1969-07-02 Firefox/37.0\"\n" +
                "39.96.253.241 - - [19/Nov/2023:22:08:39 +0000] \"GET /composite_collaboration-challenge/tangible.htm HTTP/1.1\" 500 52 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_9_4) AppleWebKit/5342 (KHTML, like Gecko) Chrome/38.0.852.0 Mobile Safari/5342\"\n" +
                "253.22.191.104 - - [19/Nov/2023:22:09:40 +0000] \"GET /background_Reactive-6th%20generation.png HTTP/1.1\" 200 1113 \"-\" \"Mozilla/5.0 (Windows NT 6.2) AppleWebKit/5342 (KHTML, like Gecko) Chrome/39.0.890.0 Mobile Safari/5342\"\n" +
                "45.250.6.167 - - [19/Nov/2023:22:10:41 +0000] \"GET /utilisation/modular%20systemic.gif HTTP/1.1\" 301 102 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_9_7) AppleWebKit/5352 (KHTML, like Gecko) Chrome/38.0.807.0 Mobile Safari/5352\"\n" +
                "112.171.169.135 - - [19/Nov/2023:22:11:42 +0000] \"DELETE /Assimilated%20Streamlined-Intuitive/intermediate.svg HTTP/1.1\" 200 2245 \"-\" \"Mozilla/5.0 (Windows 95; en-US; rv:1.9.3.20) Gecko/2015-06-11 Firefox/35.0\"\n" +
                "101.101.170.46 - - [19/Nov/2023:22:12:43 +0000] \"GET /flexibility-human-resource/workforce.png HTTP/1.1\" 200 1175 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_10 rv:4.0; en-US) AppleWebKit/533.5.6 (KHTML, like Gecko) Version/5.2 Safari/533.5.6\"\n"
        );
        CREATOR.newTestFile(
            combinePath("output", "output1.md"),
            "### Общая информация\n" +
                "|        Метрика        |  Значение  |\n" +
                "|:---------------------:|:----------:|\n" +
                "|       Файл(-ы)        | nginx_logs |\n" +
                "|    Начальная дата     |     -      |\n" +
                "|     Конечная дата     |     -      |\n" +
                "|  Количество запросов  |   51462    |\n" +
                "| Средний размер ответа |  659509b   |\n" +
                "### Запрашиваемые ресурсы\n" +
                "|   Ресурс   | Количество |\n" +
                "|:----------:|:----------:|\n" +
                "| /product_1 |   30285    |\n" +
                "| /product_2 |   21104    |\n" +
                "| /product_3 |     73     |\n" +
                "### Коды ответа\n" +
                "| Код  |          Имя          | Количество |\n" +
                "|:----:|:---------------------:|:----------:|\n" +
                "| 304  |     NOT_MODIFIED      |   13330    |\n" +
                "| 200  |          OK           |    4028    |\n" +
                "| 404  |       NOT_FOUND       |   33876    |\n" +
                "| 206  |    PARTIAL_CONTENT    |    186     |\n" +
                "| 403  |       FORBIDDEN       |     38     |\n" +
                "| 416  | RANGE_NOT_SATISFIABLE |     4      |\n" +
                "### (Дополнительно) Частота встречи команд в запросах\n" +
                "| Команда | Количество |\n" +
                "|:-------:|:----------:|\n" +
                "|   GET   |   51379    |\n" +
                "|  HEAD   |     83     |\n" +
                "### (Дополнительно) Типы Http User Agent\n" +
                "|                                                      Имя                                                       | Количество |\n" +
                "|:--------------------------------------------------------------------------------------------------------------:|:----------:|\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)                                  |    6719    |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)                                  |    1827    |\n" +
                "|                                         Debian APT-HTTP/1.3 (0.8.10.3)                                         |    618     |\n" +
                "|                                                       -                                                        |     14     |\n" +
                "|                                         Debian APT-HTTP/1.3 (0.9.7.9)                                          |   11365    |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)                                  |    5740    |\n" +
                "|                                       Debian APT-HTTP/1.3 (1.0.1ubuntu2)                                       |   11830    |\n" +
                "|                                              Go 1.1 package http                                               |     6      |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)                                  |    3855    |\n" +
                "|                                           urlgrabber/3.9.1 yum/3.4.3                                           |    708     |\n" +
                "|                                Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.20.1)                                 |    592     |\n" +
                "|                                            Wget/1.13.4 (linux-gnu)                                             |    438     |\n" +
                "|                                          urlgrabber/3.9.1 yum/3.2.29                                           |    792     |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.14)                                  |    288     |\n" +
                "|                                  Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.7)                                  |    1255    |\n" +
                "|              Chef Client/11.16.4 (ruby-1.9.3-p547; ohai-7.4.0; x86_64-linux; +http://opscode.com)              |    127     |\n" +
                "|                                  Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.2)                                  |    429     |\n" +
                "|             Chef Client/11.10.4 (ruby-1.9.3-p484; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     34     |\n" +
                "|                                  Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.5)                                  |    175     |\n" +
                "|                                             Wget/1.15 (linux-gnu)                                              |     74     |\n" +
                "|              Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)               |     72     |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.11)                                  |     64     |\n" +
                "|                                  Debian APT-HTTP/1.3 (0.8.16~exp5ubuntu13.7)                                   |     60     |\n" +
                "|      curl/7.22.0 (x86_64-pc-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.1 zlib/1.2.3.4 libidn/1.23 librtmp/2.3       |     34     |\n" +
                "|                                         Debian APT-HTTP/1.3 (0.9.7.8)                                          |    750     |\n" +
                "|                                          Debian Apt-Cacher-NG/0.7.11                                           |    303     |\n" +
                "|                      python-requests/2.0.0 CPython/2.6.6 Linux/2.6.32-358.18.1.el6.x86_64                      |     30     |\n" +
                "|        apt-cacher/1.7.6 libcurl/7.26.0 GnuTLS/2.12.20 zlib/1.2.7 libidn/1.25 libssh2/1.4.2 librtmp/2.3         |     24     |\n" +
                "|              Chef Client/11.6.2 (ruby-1.9.3-p448; ohai-6.18.0; x86_64-linux; +http://opscode.com)              |    279     |\n" +
                "|              Chef Client/11.12.8 (ruby-1.9.3-p484; ohai-7.0.4; x86_64-linux; +http://opscode.com)              |    151     |\n" +
                "|              Chef Client/11.8.2 (ruby-1.9.3-p484; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     30     |\n" +
                "|                                           urlgrabber/3.10 yum/3.4.3                                            |    104     |\n" +
                "|     Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36     |    132     |\n" +
                "|              Chef Client/11.12.2 (ruby-1.9.3-p484; ohai-7.0.2; x86_64-linux; +http://opscode.com)              |     89     |\n" +
                "|                                          urlgrabber/3.1.0 yum/3.2.22                                           |    107     |\n" +
                "|                                                      None                                                      |     11     |\n" +
                "|                                         Debian APT-HTTP/1.3 (0.9.12.1)                                         |     90     |\n" +
                "|             Chef Client/10.32.2 (ruby-1.9.3-p484; ohai-6.22.0; x86_64-linux; +http://opscode.com)              |     1      |\n" +
                "|                               Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)                               |     4      |\n" +
                "|     curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.14.0.0 zlib/1.2.3 libidn/1.18 libssh2/1.4.2     |     15     |\n" +
                "|              Chef Client/12.0.1 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)               |     22     |\n" +
                "|                                     Debian APT-HTTP/1.3 (0.9.9.1~ubuntu1)                                      |    290     |\n" +
                "|                                           Mozilla/4.0 (compatible;)                                            |     3      |\n" +
                "|                                       Apache-HttpClient/4.3.5 (java 1.5)                                       |     4      |\n" +
                "|                                                 Java/1.8.0_25                                                  |     2      |\n" +
                "|              Chef Client/11.16.0 (ruby-1.9.3-p547; ohai-7.4.0; x86_64-linux; +http://opscode.com)              |     27     |\n" +
                "|              Chef Client/11.12.4 (ruby-1.9.3-p484; ohai-7.0.4; x86_64-linux; +http://opscode.com)              |     21     |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.12)                                  |    344     |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.10)                                  |    306     |\n" +
                "|              Chef Client/12.0.0 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)               |     4      |\n" +
                "|             Chef Client/10.18.2 (ruby-1.8.7-p249; ohai-6.16.0; x86_64-linux; +http://opscode.com)              |     5      |\n" +
                "|             Chef Client/10.34.4 (ruby-1.9.3-p547; ohai-6.24.2; x86_64-linux; +http://opscode.com)              |     1      |\n" +
                "|              Chef Client/11.14.6 (ruby-1.9.3-p484; ohai-7.2.4; x86_64-linux; +http://opscode.com)              |     8      |\n" +
                "|                                          urlgrabber/3.10.1 yum/3.4.3                                           |     14     |\n" +
                "|             Chef Client/11.10.0 (ruby-1.9.3-p484; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     12     |\n" +
                "|                                             Wget/1.14 (linux-gnu)                                              |     11     |\n" +
                "|                                         Debian APT-HTTP/1.3 (1.0.9.5)                                          |     19     |\n" +
                "|                                          urlgrabber/3.1.0 yum/3.2.19                                           |     5      |\n" +
                "|                                                 Java/1.7.0_09                                                  |     3      |\n" +
                "|              Chef Client/11.16.2 (ruby-1.9.3-p547; ohai-7.4.0; x86_64-linux; +http://opscode.com)              |     34     |\n" +
                "|      curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.15.3 zlib/1.2.3 libidn/1.18 libssh2/1.4.2      |     11     |\n" +
                "|             Chef Client/11.10.2 (ruby-1.9.3-p484; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     2      |\n" +
                "|              Chef Client/11.8.2 (ruby-1.9.3-p484; ohai-6.22.0; x86_64-linux; +http://opscode.com)              |     1      |\n" +
                "|                                           Debian Apt-Cacher-NG/0.5.1                                           |     43     |\n" +
                "|              Chef Client/11.6.0 (ruby-1.9.3-p429; ohai-6.18.0; x86_64-linux; +http://opscode.com)              |     2      |\n" +
                "|              Chef Client/11.4.4 (ruby-1.9.3-p286; ohai-6.16.0; x86_64-linux; +http://opscode.com)              |     18     |\n" +
                "|                                      Debian APT-HTTP/1.3 (1.0.9.2ubuntu2)                                      |    143     |\n" +
                "|                                    Ubuntu APT-HTTP/1.3 (0.7.25.3ubuntu9.14)                                    |     20     |\n" +
                "|                                          Wget/1.11.4 Red Hat modified                                          |     2      |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.19)                                  |     84     |\n" +
                "|                                             Wget/1.12 (linux-gnu)                                              |     11     |\n" +
                "|                                   Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10)                                   |     75     |\n" +
                "|                                           Debian Apt-Cacher-NG/0.7.2                                           |     72     |\n" +
                "|              Chef Knife/11.10.4 (ruby-1.9.3-p484; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     2      |\n" +
                "|     Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36     |     1      |\n" +
                "|                                                 Java/1.7.0_65                                                  |     10     |\n" +
                "|                               Homebrew 0.9.5 (Ruby 2.0.0-481; Mac OS X 10.10.1)                                |     1      |\n" +
                "|                                                   dnf/0.5.4                                                    |     4      |\n" +
                "|              Chef Knife/11.10.4 (ruby-2.0.0-p481; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     3      |\n" +
                "|                                          Debian Apt-Cacher-NG/0.7.26                                           |     1      |\n" +
                "|              Chef Client/11.8.0 (ruby-1.9.3-p448; ohai-6.20.0; x86_64-linux; +http://opscode.com)              |     2      |\n" +
                "|                                      Debian APT-HTTP/1.3 (0.9.7.7ubuntu4)                                      |     85     |\n" +
                "|              Chef Client/11.14.2 (ruby-1.9.3-p484; ohai-7.2.0; x86_64-linux; +http://opscode.com)              |     9      |\n" +
                "|                  Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0                  |     1      |\n" +
                "|                                          Debian APT-HTTP/1.3 (1.0.6)                                           |     18     |\n" +
                "|                                    Debian APT-HTTP/1.3 (0.8.13.2ubuntu4.6)                                     |     36     |\n" +
                "|                                    Debian APT-HTTP/1.3 (0.8.13.2ubuntu4.3)                                     |     24     |\n" +
                "|            Chef Client/11.12.8 (ruby-2.1.3-p242; ohai-7.0.4; x86_64-linux-gnu; +http://opscode.com)            |     1      |\n" +
                "|                                                Axel 2.4 (Linux)                                                |     6      |\n" +
                "|                                         Debian APT-HTTP/1.3 (1.0.9.2)                                          |     10     |\n" +
                "|                                          Debian Apt-Cacher-NG/0.7.27                                           |     75     |\n" +
                "|             Chef Client/11.10.4 (ruby-1.9.3-p484; ohai-6.16.0; x86_64-linux; +http://opscode.com)              |     1      |\n" +
                "|                                     Mozilla/5.0 Gecko/20100115 Firefox/3.6                                     |     10     |\n" +
                "|  Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36  |     3      |\n" +
                "|                                 Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.15)                                  |     49     |\n" +
                "|                                         Debian APT-HTTP/1.3 (1.0.9.4)                                          |     13     |\n" +
                "|             Chef Client/10.26.0 (ruby-1.9.3-p286; ohai-6.16.0; x86_64-linux; +http://opscode.com)              |     1      |\n" +
                "|                                         Debian APT-HTTP/1.3 (0.7.20.2)                                         |     20     |\n" +
                "|  Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36  |     1      |\n" +
                "|                                         Debian APT-HTTP/1.3 (1.0.9.3)                                          |     40     |\n" +
                "|                                           Debian Apt-Cacher-NG/0.8.0                                           |     2      |\n" +
                "|                     apt-cacher/1.6.12 libcurl/7.21.0 GnuTLS/2.8.6 zlib/1.2.3.4 libidn/1.15                     |     3      |\n" +
                "|                      Mozilla/5.0 (X11; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0                      |     5      |\n" +
                "|             Chef Client/10.18.2 (ruby-1.8.7-p352; ohai-6.16.0; x86_64-linux; +http://opscode.com)              |     2      |\n" +
                "|                                   Ubuntu APT-HTTP/1.3 (0.7.25.3ubuntu9.17.1)                                   |     10     |\n" +
                "|                                          urlgrabber/3.9.1 yum/3.2.27                                           |     1      |\n" +
                "|                                                 Java/1.7.0_55                                                  |     2      |\n" +
                "|                                    Ubuntu APT-HTTP/1.3 (0.7.25.3ubuntu9.13)                                    |     6      |\n" +
                "|                                                  curl/7.29.0                                                   |     2      |\n" +
                "|                                                 Java/1.8.0_20                                                  |     3      |\n" +
                "|                        python-requests/2.2.1 CPython/2.6.6 Linux/2.6.32-431.el6.x86_64                         |     4      |\n" +
                "|                                    Ubuntu APT-HTTP/1.3 (0.7.25.3ubuntu9.15)                                    |     6      |\n" +
                "|                                                ansible-httpget                                                 |     1      |\n" +
                "|                                                 Java/1.7.0_51                                                  |     1      |\n" +
                "|                                                 Java/1.6.0_31                                                  |     1      |\n" +
                "|                                     Debian APT-HTTP/1.3 (0.9.7.5ubuntu5.1)                                     |     27     |\n" +
                "|               Chef Client/11.4.0 (ruby-1.9.3-p0; ohai-6.16.0; x86_64-linux; +http://opscode.com)               |     1      |\n" +
                "|              Chef Client/11.8.2 (ruby-1.9.3-p484; ohai-6.14.0; x86_64-linux; +http://opscode.com)              |     3      |\n" +
                "|                      Mozilla/5.0 (X11; Linux x86_64; rv:25.0) Gecko/20100101 Firefox/25.0                      |     1      |\n" +
                "|                                  Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.3)                                  |     37     |\n" +
                "|              Chef Knife/11.8.2 (ruby-1.9.3-p484; ohai-6.14.0; x86_64-linux; +http://opscode.com)               |     1      |\n" +
                "|                                     Debian APT-CURL/1.0 (0.9.9.1~ubuntu1)                                      |     28     |\n" +
                "| curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.16.1 Basic ECC zlib/1.2.3 libidn/1.18 libssh2/1.4.2 |     3      |\n" +
                "|                                               Python-urllib/2.7                                                |     1      |\n" +
                "|                                                libwww-perl/6.05                                                |     6      |\n" +
                "|                                 ZYpp 10.4.5 (curl 7.22.0) openSUSE-12.1-x86_64                                 |     8      |\n" +
                "|                                                      Ruby                                                      |     1      |\n" +
                "|              Chef Client/11.14.2 (ruby-1.9.3-p194; ohai-7.2.0; x86_64-linux; +http://opscode.com)              |     1      |\n" +
                "|                    Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0                    |     1      |\n" +
                "|            Chef Client/12.0.1 (ruby-2.1.5-p273; ohai-8.0.1; x86_64-linux-gnu; +http://opscode.com)             |     1      |\n" +
                "|                                                 Twitterbot/1.0                                                 |     1      |\n" +
                "|                                                 Java/1.7.0_71                                                  |     2      |\n" +
                "|               Chef Client/11.16.4 (ruby-1.9.3-p547; ohai-7.4.0; i686-linux; +http://opscode.com)               |     1      |\n" +
                "|                                     Ubuntu APT-HTTP/1.3 (0.7.9ubuntu17.6)                                      |     4      |\n" +
                "|                                               libwww-perl/5.836                                                |     2      |\n" +
                "|     Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36     |     1      |\n"
        );
        CREATOR.newTestFile(
            combinePath("output", "output2.adoc"),
            "=== Общая информация\n" +
                "|=============================================\n" +
                "|        Метрика       |       Значение      \n" +
                "|       Файл(-ы)       |      log2; log1     \n" +
                "|    Начальная дата    | 2023-11-16T21:00:00Z\n" +
                "|     Конечная дата    | 2023-11-18T21:00:00Z\n" +
                "|  Количество запросов |          21         \n" +
                "| Средний размер ответа|        1661b        \n" +
                "|=============================================\n" +
                "=== Запрашиваемые ресурсы\n" +
                "|=================================================================\n" +
                "|                       Ресурс                       | Количество\n" +
                "|                  /initiative.hmtl                  |     1     \n" +
                "|                 /demand-driven.htm                 |     1     \n" +
                "|      /exuding_optimal_bifurcated%20Virtual.php     |     1     \n" +
                "|                     /circuit.js                    |     1     \n" +
                "|              /Graphic%20Interface.svg              |     1     \n" +
                "|                    /Inverse.jpg                    |     1     \n" +
                "|                  /Persevering.svg                  |     1     \n" +
                "| /Synchronised-bifurcated-Synchronised_dedicated.css|     1     \n" +
                "|                  /needs-based.png                  |     1     \n" +
                "|                    /tangible.htm                   |     1     \n" +
                "|      /background_Reactive-6th%20generation.png     |     1     \n" +
                "|               /modular%20systemic.gif              |     1     \n" +
                "|                  /intermediate.svg                 |     1     \n" +
                "|                   /workforce.png                   |     1     \n" +
                "|     /instruction%20set_holistic-responsive.css     |     1     \n" +
                "|                  /Object-based.gif                 |     1     \n" +
                "|                   /Visionary.svg                   |     1     \n" +
                "|               /Focused-Exclusive.gif               |     1     \n" +
                "|             /benchmark-Assimilated.hmtl            |     1     \n" +
                "|                     /stable.jpg                    |     1     \n" +
                "|              /service-desk_Inverse.php             |     1     \n" +
                "|=================================================================\n" +
                "=== Коды ответа\n" +
                "|=========================================\n" +
                "| Код |          Имя         | Количество\n" +
                "| 200 |          OK          |     17    \n" +
                "| 500 | INTERNAL_SERVER_ERROR|     1     \n" +
                "| 301 |   MOVED_PERMANENTLY  |     3     \n" +
                "|=========================================\n" +
                "=== (Дополнительно) Частота встречи команд в запросах\n" +
                "|=====================\n" +
                "| Команда| Количество\n" +
                "|   GET  |     16    \n" +
                "|  PATCH |     1     \n" +
                "|   PUT  |     1     \n" +
                "|  HEAD  |     1     \n" +
                "| DELETE |     1     \n" +
                "|  POST  |     1     \n" +
                "|=====================\n" +
                "=== (Дополнительно) Типы Http User Agent\n" +
                "|========================================================================================================================================================\n" +
                "|                                                                    Имя                                                                    | Количество\n" +
                "|                               Mozilla/5.0 (Windows NT 5.1; en-US; rv:1.9.0.20) Gecko/1981-28-01 Firefox/36.0                              |     1     \n" +
                "|                                    Mozilla/5.0 (X11; Linux i686; rv:7.0) Gecko/1994-06-11 Firefox/37.0                                    |     1     \n" +
                "|                  Mozilla/5.0 (X11; Linux i686) AppleWebKit/5342 (KHTML, like Gecko) Chrome/40.0.896.0 Mobile Safari/5342                  |     1     \n" +
                "|                           Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_8_1 rv:6.0) Gecko/1919-19-11 Firefox/36.0                          |     1     \n" +
                "|           Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_3) AppleWebKit/5311 (KHTML, like Gecko) Chrome/36.0.838.0 Mobile Safari/5311          |     1     \n" +
                "|               Mozilla/5.0 (Windows; U; Windows NT 4.0) AppleWebKit/533.34.5 (KHTML, like Gecko) Version/5.0 Safari/533.34.5               |     1     \n" +
                "|                            Opera/10.24 (Macintosh; U; PPC Mac OS X 10_8_1; en-US) Presto/2.12.170 Version/12.00                           |     1     \n" +
                "| Mozilla/5.0 (iPad; CPU OS 8_2_2 like Mac OS X; en-US) AppleWebKit/533.21.8 (KHTML, like Gecko) Version/5.0.5 Mobile/8B115 Safari/6533.21.8|     1     \n" +
                "|                            Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_8 rv:7.0) Gecko/1969-07-02 Firefox/37.0                            |     1     \n" +
                "|           Mozilla/5.0 (Macintosh; PPC Mac OS X 10_9_4) AppleWebKit/5342 (KHTML, like Gecko) Chrome/38.0.852.0 Mobile Safari/5342          |     1     \n" +
                "|                   Mozilla/5.0 (Windows NT 6.2) AppleWebKit/5342 (KHTML, like Gecko) Chrome/39.0.890.0 Mobile Safari/5342                  |     1     \n" +
                "|         Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_9_7) AppleWebKit/5352 (KHTML, like Gecko) Chrome/38.0.807.0 Mobile Safari/5352         |     1     \n" +
                "|                                 Mozilla/5.0 (Windows 95; en-US; rv:1.9.3.20) Gecko/2015-06-11 Firefox/35.0                                |     1     \n" +
                "|      Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_10 rv:4.0; en-US) AppleWebKit/533.5.6 (KHTML, like Gecko) Version/5.2 Safari/533.5.6     |     1     \n" +
                "|          Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_7) AppleWebKit/5351 (KHTML, like Gecko) Chrome/37.0.815.0 Mobile Safari/5351         |     1     \n" +
                "|                             Opera/10.77 (Macintosh; Intel Mac OS X 10_5_9; en-US) Presto/2.9.187 Version/12.00                            |     1     \n" +
                "|                 Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/5322 (KHTML, like Gecko) Chrome/38.0.838.0 Mobile Safari/5322                 |     1     \n" +
                "|                  Mozilla/5.0 (X11; Linux i686) AppleWebKit/5321 (KHTML, like Gecko) Chrome/39.0.855.0 Mobile Safari/5321                  |     1     \n" +
                "|                                     Opera/10.59 (X11; Linux i686; en-US) Presto/2.13.237 Version/13.00                                    |     1     \n" +
                "|               Mozilla/5.0 (Windows; U; Windows NT 6.0) AppleWebKit/534.26.6 (KHTML, like Gecko) Version/5.1 Safari/534.26.6               |     1     \n" +
                "|              Mozilla/5.0 (Windows 98; Win 9x 4.90) AppleWebKit/5350 (KHTML, like Gecko) Chrome/40.0.837.0 Mobile Safari/5350              |     1     \n" +
                "|========================================================================================================================================================\n"
        );
    }

    public static Stream<Arguments> testAnalyzerTest() {
        return Stream.of(
            Arguments.of(
                new String[] {
                    "--path", ".project3/logs/*",
                    "--out", ".project3",
                    "--from", "2023-11-17",
                    "--to", "2023-11-19",
                    "--format", "adoc"
                },
                CREATOR.root().resolve("output").resolve("output2.adoc"),
                CREATOR.root().resolve("report.adoc")
            ),
            Arguments.of(
                new String[] {
                    "--path",
                    "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
                    "--out", ".project3"
                },
                CREATOR.root().resolve("output").resolve("output1.md"),
                CREATOR.root().resolve("report.md")
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAnalyzerTest(String[] args, Path output, Path result) throws IOException {
        Main.main(args);
        assertThat(Files.mismatch(result, output)).isEqualTo(-1L);
    }

    @AfterAll
    static void tearDown() throws IOException {
        CREATOR.deleteDirectory();
    }
}
