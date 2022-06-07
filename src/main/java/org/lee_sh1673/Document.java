package org.lee_sh1673;

import java.util.Map;

public class Document {
    private final Map<String, String> attributes;

    Document(final Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(final String attributeName) {
        return attributes.get(attributeName);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, String> attribute : attributes.entrySet()) {
            sb.append("[" + attribute.getKey() + "]: " + attribute.getValue() + "\n");
        }
        return sb.toString();
    }
}
