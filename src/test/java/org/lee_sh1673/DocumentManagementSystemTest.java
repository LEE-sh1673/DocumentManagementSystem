package org.lee_sh1673;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class DocumentManagementSystemTest {
    final static String RESOURCES = "src/main/resources/";
    final static String[] FILE_NAMES = {"test.jpg", "test.letter", "test.report"};

    @Test void shouldImportFileCorrect() throws IOException {
        DocumentManagementSystem documentManagementSystem
                = new DocumentManagementSystem();

        for (final String fileName : FILE_NAMES) {
            Path path = Paths.get(RESOURCES + fileName);
            documentManagementSystem.importFile(path.toString());
        }
        documentManagementSystem.showDocumentsInfo();
    }
}
