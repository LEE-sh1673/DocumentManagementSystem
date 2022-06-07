package org.lee_sh1673;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentManagementSystem {

    private final List<Document> documents
            = new ArrayList<>();
    private final Map<String, Importer> extensionToImporter
            = new HashMap<>();

    public DocumentManagementSystem() {
        //TODO: put invoice importer later
        extensionToImporter.put("report", new ReportImporter());
        extensionToImporter.put("letter", new LetterImporter());
        extensionToImporter.put("jpg", new ImageImporter());
    }

    public void importFile(final String path) throws IOException {
        final File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }

        final int separatorIndex = path.lastIndexOf(".");

        // If path is valid.
        if (separatorIndex != -1) {

            // If path has no extension file.
            if (separatorIndex == path.length()) {
                throw new UnknownFileTypeException("No extension found For file: " + path);
            }
            final String extension = path.substring(separatorIndex + 1);
            final Importer importer = extensionToImporter.get(extension);

            if (importer == null) {
                throw new UnknownFileTypeException("For file: " + path);
            }

            final Document document = importer.importFile(file);
            documents.add(document);
        } else {
            throw new UnknownFileTypeException("No extension found for file: " + path);
        }
    }

    //TODO: This is temp method for test.
    public void showDocumentsInfo() {
        for (final Document document : documents) {

            System.out.println(document);
        }
    }
}
