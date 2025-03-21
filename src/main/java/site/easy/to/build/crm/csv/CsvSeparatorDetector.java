package site.easy.to.build.crm.csv;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class CsvSeparatorDetector {
    private static final char[] SEPARATOR_CANDIDATES = {',', ';', '\t', '|', ':'};
    public static char detectSeparator(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<Character, Integer> separatorCounts = new HashMap<>();

            int linesToRead = 10;
            int lineCount = 0;

            while ((line = reader.readLine()) != null && lineCount < linesToRead) {
                for (char separator : SEPARATOR_CANDIDATES) {
                    int count = line.length() - line.replace(String.valueOf(separator), "").length();
                    separatorCounts.put(separator, separatorCounts.getOrDefault(separator, 0) + count);
                }
                lineCount++;
            }

            return separatorCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new IllegalArgumentException("No separator detected"));
        }
    }
}
