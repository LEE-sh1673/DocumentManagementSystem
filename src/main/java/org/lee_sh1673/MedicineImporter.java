package org.lee_sh1673;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.lee_sh1673.Attributes.*;

public class MedicineImporter implements Importer {
    private static final String NAME_PREFIX = "Patient: ";
    private static final String MEDICINE_PREFIX = "Medicine: ";
    private static final String AMOUNT_PREFIX = "Amount: ";
    private static final String DATE_PREFIX = "Date: ";
    private static final String CONDITION_PREFIX = "Condition: ";

    @Override public Document importFile(File file) throws IOException {
        final TextFile textFile = new TextFile(file);

        textFile.addLineSuffix(NAME_PREFIX, PATIENT);
        textFile.addLineSuffix(MEDICINE_PREFIX, MEDICINE);
        textFile.addLineSuffix(AMOUNT_PREFIX, AMOUNT);
        textFile.addLineSuffix(DATE_PREFIX, DATE);
        textFile.addLineSuffix(CONDITION_PREFIX, CONDITION);

        final Map<String, String> attributes = textFile.getAttributes();
        attributes.put(TYPE, "MEDICINE");

        return new Document(attributes);
    }
}
