package edu.hw3.tasks.task1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw3.tasks.task1.AtbashCipher.atbash;
import static org.assertj.core.api.Assertions.assertThat;

public class AtbashCipherTest {
    @ParameterizedTest
    @MethodSource("stringsToEncrypt")
    public void testAtbashCipher(final String toEncrypt, final String result) {
        assertThat(atbash(toEncrypt)).isEqualTo(result);
    }

    private static Stream<Arguments> stringsToEncrypt() {
        return Stream.of(
            Arguments.of("Hello world!", "Svool dliow!"),
            Arguments.of(
                "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler",
                "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
            ),
            Arguments.of(
                "This is a complete guide to the Atbash cipher and the tools you need to decode it. From the very earliest days of encryption, people have been using the Atbash cipher to hide the meaning of their text. It’s one of the simplest ciphers to decode – once you’ve worked out that its Atbash that you’re looking at.",
                "Gsrh rh z xlnkovgv tfrwv gl gsv Zgyzhs xrksvi zmw gsv glloh blf mvvw gl wvxlwv rg. Uiln gsv evib vziorvhg wzbh lu vmxibkgrlm, kvlkov szev yvvm fhrmt gsv Zgyzhs xrksvi gl srwv gsv nvzmrmt lu gsvri gvcg. Rg’h lmv lu gsv hrnkovhg xrksvih gl wvxlwv – lmxv blf’ev dlipvw lfg gszg rgh Zgyzhs gszg blf’iv ollprmt zg."
            ),
            Arguments.of(
                "C++ is one of the main development languages used by many of Google's open-source projects. As every C++ programmer knows, the language has many powerful features, but this power brings with it complexity, which in turn can make code more bug-prone and harder to read and maintain.",
                "X++ rh lmv lu gsv nzrm wvevolknvmg ozmtfztvh fhvw yb nzmb lu Tlltov'h lkvm-hlfixv kilqvxgh. Zh vevib X++ kiltiznnvi pmldh, gsv ozmtfztv szh nzmb kldviufo uvzgfivh, yfg gsrh kldvi yirmth drgs rg xlnkovcrgb, dsrxs rm gfim xzm nzpv xlwv nliv yft-kilmv zmw sziwvi gl ivzw zmw nzrmgzrm."
            ),
            Arguments.of("R dzh vmxlwvw, mld R'n uivv!", "I was encoded, now I'm free!")
        );
    }
}
