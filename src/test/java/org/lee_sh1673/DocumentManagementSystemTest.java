package org.lee_sh1673;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentManagementSystemTest {
    final static String RESOURCES = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator;

    final static String LETTER = RESOURCES + "test.letter";
    final static String REPORT = RESOURCES + "test.report";
    final static String IMAGE = RESOURCES + "test.jpg";
    
    private final DocumentManagementSystem system = new DocumentManagementSystem();

    @Test public void shouldImportFile() throws Exception {
        system.importFile(LETTER);
        final Document document = onlyDocument();
        assertEquals(document.getAttribute(Attributes.PATH), LETTER);
    }

    private Document onlyDocument() {
        final List<Document> documents = system.contents();
        assertThat(documents, hasSize(1));
        return documents.get(0);
    }
}
