package org.lee_sh1673;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.lee_sh1673.Attributes.*;

public class LetterImporter implements Importer {
    private static final String NAME_PREFIX = "Dear ";

    @Override public Document importFile(File file) throws IOException {
        final TextFile textFile = new TextFile(file);

        textFile.addLineSuffix(NAME_PREFIX, PATIENT);

        // extract lineNumber of letter-address area.
        final int lineNumber = textFile.addLines(2, String::isEmpty, ADDRESS);

        // extract lineNumber of letter contents.
        textFile.addLines(lineNumber + 1, (line) -> line.startsWith("regards,"), BODY);
        
        final Map<String, String> attributes = textFile.getAttributes();
        attributes.put(TYPE, "LETTER");

        return new Document(attributes);
    }
}
