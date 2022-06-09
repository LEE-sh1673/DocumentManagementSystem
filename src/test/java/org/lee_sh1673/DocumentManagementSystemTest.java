package org.lee_sh1673;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.lee_sh1673.Attributes.*;

class DocumentManagementSystemTest {
    private final static String RESOURCES = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator;

    private final static String LETTER = RESOURCES + "test.letter";
    private final static String REPORT = RESOURCES + "test.report";
    private final static String MEDICINE = RESOURCES + "test.medicine";
    private final static String IMAGE = RESOURCES + "test.jpg";

    private static final String JOE_BLOGGS = "Joe Bloggs";

    private final DocumentManagementSystem system = new DocumentManagementSystem();

    @Test public void shouldImportFile() throws Exception {
        system.importFile(LETTER);
        final Document document = onlyDocument();
        assertAttributeEquals(document, PATH, LETTER);
    }

    @Test public void shouldImportImageAttributes() throws Exception {
        system.importFile(IMAGE);
        final Document document = onlyDocument();
        assertAttributeEquals(document, WIDTH, "640");
        assertAttributeEquals(document, HEIGHT, "430");
        assertTypeIs("IMAGE", document);
    }

    @Test public void shouldImportLetterAttributes() throws Exception {
        system.importFile(LETTER);

        final Document document = onlyDocument();

        assertAttributeEquals(document, PATIENT, JOE_BLOGGS);
        assertAttributeEquals(document, ADDRESS,
                "123 Fake Street\n" +
                        "Westminster\n" +
                        "London\n" +
                        "United Kingdom");
        assertAttributeEquals(document, BODY,
                "We are writing to you to confirm the re-scheduling of your appointment\n" +
                        "with Dr, Avaj from 29th December 2016 to 5th January 2017.");
        assertTypeIs("LETTER", document);
    }

    @Test public void shouldImportMedicineAttributes() throws Exception {
        system.importFile(MEDICINE);

        final Document document = onlyDocument();

        assertAttributeEquals(document, PATIENT, JOE_BLOGGS);
        assertAttributeEquals(document, Attributes.MEDICINE, "headache pill");
        assertAttributeEquals(document, AMOUNT, "$100");
        assertAttributeEquals(document, DATE, "30-01-2022");
        assertAttributeEquals(document, CONDITION, "Twice on a day");
        assertTypeIs("MEDICINE", document);
    }

    @Test public void shouldNotImportMissingFile() throws Exception {
        assertThrows(FileNotFoundException.class, () -> {
            system.importFile("NonExistingFile.txt");
        });
    }

    @Test public void shouldNotImportUnknownFile() throws Exception {
        assertThrows(UnknownFileTypeException.class, () -> {
            system.importFile(RESOURCES + "unknown.txt");
        });
    }

    @Test public void shouldSearchDocumentsCorrectly() throws IOException {
        system.importFile(REPORT);
        system.importFile(MEDICINE);

        final String query = "patient:Joe Bloggs,amount:$100,date:30-01-2022";
        final List<Document> documents = system.search(query);

        assertThat(documents, hasSize(1));

        final Document result = documents.get(0);

        assertAttributeEquals(result, AMOUNT, "$100");
        assertAttributeEquals(result, CONDITION, "Twice on a day");
        assertAttributeEquals(result, PATIENT, "Joe Bloggs");
        assertAttributeEquals(result, Attributes.MEDICINE, "headache pill");
        assertTypeIs("MEDICINE", result);
    }

    private Document onlyDocument() {
        final List<Document> documents = system.contents();
        assertThat(documents, hasSize(1));
        return documents.get(0);
    }

    private void assertTypeIs(final String type, final Document document) {
        assertAttributeEquals(document, TYPE, type);
    }

    private void assertAttributeEquals(
            final Document document,
            final String attributeName,
            final String expectedValue) {

        assertEquals(
                expectedValue,
                document.getAttribute(attributeName),
                "Document has the wrong value for " + attributeName);
    }
}
