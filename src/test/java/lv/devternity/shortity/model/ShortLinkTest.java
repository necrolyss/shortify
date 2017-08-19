package lv.devternity.shortity.model;

import lv.devternity.shortity.application.ValidationException;
import lv.devternity.shortity.utils.SwearwordFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: jurijsgrabovskis
 * Created at: 04/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortLinkTest {

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @Autowired
    private SwearwordFilter swearwordFilter;

    @Test
    public void testValidation() {
        /* Valid case */
        String randomValidPath = RandomStringUtils.random(8, true, true);
        assertValidationCorrect(randomValidPath, "google.com", false);

        assertUserShortLinkIsCorrect();
        assertDestinationIsCorrect(randomValidPath);

        assertUniquenessCheckIsCorrect();
        assertSwearwordCheckingIsCorrect();
    }

    private void assertSwearwordCheckingIsCorrect() {
        assertValidationCorrect("ass", "facebook.com", true);
    }

    private void assertUniquenessCheckIsCorrect() {
        if (!shortLinkRepository.exists(BasicMapId.id("path", "aaa"))) {
            ShortLink shortLink = new ShortLink.Unchecked("aaa", "google.com")
                .buildChecked(shortLinkRepository, swearwordFilter);
            shortLinkRepository.insert(shortLink);
        }

        assertValidationCorrect("aaa", "google.com", true);
    }

    private void assertDestinationIsCorrect(String randomValidPath) {
        assertValidationCorrect(randomValidPath, "", true);
        assertValidationCorrect(randomValidPath, null, true);
        assertValidationCorrect(randomValidPath, " a a a ", true);
        assertValidationCorrect(randomValidPath, "http:///", true);
        assertValidationCorrect(randomValidPath, "vasya.pupkin@gmail.com", true);
        assertValidationCorrect(randomValidPath, "o_0.com", true);
        assertValidationCorrect(randomValidPath, "g\uDE31gle.com", true);
    }

    private void assertUserShortLinkIsCorrect() {
        assertValidationCorrect("", "google.com", true);
        assertValidationCorrect(null, "google.com", true);
        assertValidationCorrect("aaa aaa", "google.com", true);
        assertValidationCorrect("Превед", "google.com", true);
        assertValidationCorrect("Medved!", "google.com", true);
        assertValidationCorrect("VeryVeryVeryLongString", "google.com", true);
        assertValidationCorrect("\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31\uD83D\uDE31", "google.com", true);
    }

    private void assertValidationCorrect(String path, String destination, boolean failExpected) {
        try {
            new ShortLink.Unchecked(path, destination).buildChecked(shortLinkRepository, swearwordFilter);
            if (failExpected) {
                Assert.fail("Expected validation fail did not happened!");
            }
        } catch (ValidationException e) {
            if(!failExpected) {
                Assert.fail("Unexpected fail during shortlink testing: " + e.getMessage());
            }
        }
    }

}