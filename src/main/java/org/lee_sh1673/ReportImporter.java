package org.lee_sh1673;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.lee_sh1673.Attributes.*;

public class ReportImporter implements Importer {
    private static final String NAME_PREFIX = "Patient: ";

    @Override public Document importFile(File file) throws IOException {
        final TextFile textFile = new TextFile(file);

        textFile.addLineSuffix(NAME_PREFIX, PATIENT);

        // extract lineNumber of letter contents.
        textFile.addLines(2, String::isEmpty, BODY);

        final Map<String, String> attributes = textFile.getAttributes();
        attributes.put(TYPE, "LETTER");

        return new Document(attributes);
    }
}
