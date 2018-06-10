import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InFileChanger {

    private int inFileCount = 0;

    public void replaceText(String filePath, String in, String out) throws IOException {
        List<String> newLines = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filePath))) {
            if (line.contains(in)) {
                newLines.add(line.replace(in, out));
                inFileCount++;
            } else {
                newLines.add(line);
            }
        }
        Files.write(Paths.get(filePath), newLines);
    }

    public int getInFileCount() {
        return inFileCount;
    }

    public void setInFileCount(int inFileCount) {
        this.inFileCount = inFileCount;
    }
}