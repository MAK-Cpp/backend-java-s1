package edu.project3.analyzer;

import edu.project3.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@DisplayName("Test analyzer")
public class AnalyzerTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Path.of(System.getProperty("user.dir")).resolve(".project3"));

    @BeforeAll
    static void setUp() throws IOException {
        CREATOR.createDirectory();
        CREATOR.newTestFile(
            "log1",
            "124.62.158.227 - - [19/Nov/2023:17:35:51 +0000] \"HEAD /open%20architecture/open%20system-Synergistic-radical.php HTTP/1.1\" 200 1494 \"-\" \"Mozilla/5.0 (Windows NT 4.0) AppleWebKit/5320 (KHTML, like Gecko) Chrome/37.0.820.0 Mobile Safari/5320\"\n" +
                "97.194.109.102 - - [19/Nov/2023:17:35:51 +0000] \"DELETE /multi-state_Persistent-open%20architecture-Centralized-Extended.jpg HTTP/1.1\" 200 1163 \"-\" \"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/5360 (KHTML, like Gecko) Chrome/40.0.817.0 Mobile Safari/5360\"\n" +
                "84.18.15.218 - - [19/Nov/2023:17:35:51 +0000] \"DELETE /Horizontal.jpg HTTP/1.1\" 200 1617 \"-\" \"Mozilla/5.0 (Windows NT 5.0; en-US; rv:1.9.0.20) Gecko/1949-11-02 Firefox/36.0\"\n" +
                "236.45.44.131 - - [19/Nov/2023:17:35:51 +0000] \"GET /dedicated/grid-enabled-algorithm.js HTTP/1.1\" 200 2531 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_8_9 rv:6.0) Gecko/1903-20-09 Firefox/35.0\"\n" +
                "184.24.250.128 - - [19/Nov/2023:17:35:51 +0000] \"GET /User-friendly/instruction%20set_executive/client-server.js HTTP/1.1\" 200 1544 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_7_0 rv:7.0) Gecko/1908-30-08 Firefox/35.0\"\n" +
                "148.253.42.228 - - [19/Nov/2023:17:35:51 +0000] \"GET /impactful-user-facing.php HTTP/1.1\" 200 936 \"-\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_4 rv:7.0; en-US) AppleWebKit/534.35.1 (KHTML, like Gecko) Version/4.0 Safari/534.35.1\"\n" +
                "151.199.10.177 - - [19/Nov/2023:17:35:51 +0000] \"GET /Virtual.js HTTP/1.1\" 200 2132 \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 4.0) AppleWebKit/533.26.3 (KHTML, like Gecko) Version/5.0 Safari/533.26.3\"\n" +
                "247.168.243.54 - - [19/Nov/2023:17:35:51 +0000] \"GET /optimal/Decentralized/Monitored-encryption%20stable.htm HTTP/1.1\" 200 2385 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_5_9 rv:5.0; en-US) AppleWebKit/536.8.1 (KHTML, like Gecko) Version/5.1 Safari/536.8.1\"\n" +
                "182.114.121.32 - - [19/Nov/2023:17:35:51 +0000] \"GET /leverage-secured%20line.jpg HTTP/1.1\" 500 95 \"-\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_4) AppleWebKit/5330 (KHTML, like Gecko) Chrome/37.0.879.0 Mobile Safari/5330\"\n" +
                "247.228.122.205 - - [19/Nov/2023:17:35:51 +0000] \"GET /methodology/Vision-oriented-leverage/Adaptive.png HTTP/1.1\" 200 1548 \"-\" \"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/5321 (KHTML, like Gecko) Chrome/37.0.892.0 Mobile Safari/5321\"\n" +
                "28.207.163.8 - - [19/Nov/2023:17:35:51 +0000] \"GET /projection/emulation-monitoring_software%20Synchronised.gif HTTP/1.1\" 400 75 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8) AppleWebKit/5331 (KHTML, like Gecko) Chrome/38.0.864.0 Mobile Safari/5331\"\n" +
                "103.201.197.184 - - [19/Nov/2023:17:35:51 +0000] \"PATCH /Multi-channelled/zero%20defect_pricing%20structure/Ameliorated-even-keeled.htm HTTP/1.1\" 200 960 \"-\" \"Mozilla/5.0 (iPad; CPU OS 7_2_3 like Mac OS X; en-US) AppleWebKit/536.12.8 (KHTML, like Gecko) Version/5.0.5 Mobile/8B111 Safari/6536.12.8\"" +
                "6.244.7.56 - - [19/Nov/2023:17:36:01 +0000] \"GET /Compatible/Automated-local.jpg HTTP/1.1\" 200 1607 \"-\" \"Mozilla/5.0 (Windows 98) AppleWebKit/5342 (KHTML, like Gecko) Chrome/40.0.817.0 Mobile Safari/5342\"\n" +
                "178.198.77.127 - - [19/Nov/2023:17:36:01 +0000] \"GET /monitoring/Organized/neutral.jpg HTTP/1.1\" 200 2338 \"-\" \"Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/1945-16-04 Firefox/35.0\"\n" +
                "216.72.224.49 - - [19/Nov/2023:17:36:01 +0000] \"GET /contextually-based/Reduced/instruction%20set_holistic-responsive.css HTTP/1.1\" 301 89 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_7) AppleWebKit/5351 (KHTML, like Gecko) Chrome/37.0.815.0 Mobile Safari/5351\"\n" +
                "123.66.243.102 - - [19/Nov/2023:17:36:01 +0000] \"GET /Universal/initiative/Object-based.gif HTTP/1.1\" 200 1832 \"-\" \"Opera/10.77 (Macintosh; Intel Mac OS X 10_5_9; en-US) Presto/2.9.187 Version/12.00\"\n" +
                "92.180.179.125 - - [19/Nov/2023:17:36:01 +0000] \"GET /Visionary.svg HTTP/1.1\" 301 32 \"-\" \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/5322 (KHTML, like Gecko) Chrome/38.0.838.0 Mobile Safari/5322\"\n" +
                "227.92.82.128 - - [19/Nov/2023:17:36:01 +0000] \"GET /Focused-Exclusive.gif HTTP/1.1\" 200 1688 \"-\" \"Mozilla/5.0 (X11; Linux i686) AppleWebKit/5321 (KHTML, like Gecko) Chrome/39.0.855.0 Mobile Safari/5321\"\n" +
                "100.50.48.3 - - [19/Nov/2023:17:36:01 +0000] \"GET /frame/benchmark-Assimilated.hmtl HTTP/1.1\" 200 2342 \"-\" \"Opera/10.59 (X11; Linux i686; en-US) Presto/2.13.237 Version/13.00\"\n" +
                "215.204.156.124 - - [19/Nov/2023:17:36:01 +0000] \"GET /stable.jpg HTTP/1.1\" 200 3052 \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 6.0) AppleWebKit/534.26.6 (KHTML, like Gecko) Version/5.1 Safari/534.26.6\"\n" +
                "114.46.202.74 - - [19/Nov/2023:17:36:01 +0000] \"POST /incremental/zero%20defect/service-desk_Inverse.php HTTP/1.1\" 200 1805 \"-\" \"Mozilla/5.0 (Windows 98; Win 9x 4.90) AppleWebKit/5350 (KHTML, like Gecko) Chrome/40.0.837.0 Mobile Safari/5350\"\n" +
                "155.92.103.171 - - [19/Nov/2023:17:36:01 +0000] \"PATCH /website-homogeneous-Enterprise-wide.js HTTP/1.1\" 302 71 \"-\" \"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_8_10 rv:4.0) Gecko/1948-07-08 Firefox/37.0\"\n" +
                "139.36.124.121 - - [19/Nov/2023:17:36:01 +0000] \"POST /time-frame.htm HTTP/1.1\" 200 2619 \"-\" \"Mozilla/5.0 (Windows 95) AppleWebKit/5322 (KHTML, like Gecko) Chrome/38.0.853.0 Mobile Safari/5322\"\n" +
                "177.72.102.122 - - [19/Nov/2023:17:36:01 +0000] \"GET /Exclusive.htm HTTP/1.1\" 200 2345 \"-\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_9_3 rv:7.0) Gecko/1964-30-08 Firefox/37.0\"\n" +
                "25.212.163.202 - - [19/Nov/2023:17:36:01 +0000] \"GET /synergy_bifurcated.hmtl HTTP/1.1\" 200 830 \"-\" \"Mozilla/5.0 (iPad; CPU OS 9_1_2 like Mac OS X; en-US) AppleWebKit/531.38.1 (KHTML"
        );
    }

    public static Stream<Arguments> testAnalyzerTest() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource
    void testAnalyzerTest(String[] args, String result) {
        Main.main(args);

    }

    @AfterAll
    static void tearDown() throws IOException {
//        CREATOR.deleteDirectory();
    }
}
