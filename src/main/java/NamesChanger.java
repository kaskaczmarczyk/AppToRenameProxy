import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class NamesChanger {

    private InFileChanger inFileCh = new InFileChanger();
    private int changeNameCount = 0;

    public void changeName(String rootPath, String oldName, String newName) throws IOException {
        File rootFile = new File(rootPath);
        for (File file : Objects.requireNonNull(rootFile.listFiles())) {
            String path = file.getPath();
            String parentPath = Paths.get(path).getParent().toString();
            String name = Paths.get(path).getFileName().toString();
            if (file.isDirectory()) {
                if (name.contains(oldName)) {
                    name = name.replace(oldName, newName);
                    path = Paths.get(parentPath, name).toString();
                    File newFile = new File(path);
                    FileUtils.moveDirectory(file, newFile);
                    changeNameCount++;
                }
                changeName(path, oldName, newName);
            } else {
                if (name.contains(oldName)) {
                    name = name.replace(oldName, newName);
                    path = Paths.get(parentPath, name).toString();
                    File newFile = new File(path);
                    FileUtils.moveFile(file, newFile);
                    changeNameCount++;
                }
                inFileCh.replaceText(path, oldName, newName);
            }
        }
    }

    public int getChangeNameCount() {
        return changeNameCount;
    }

    public void setChangeNameCount(int changeNameCount) {
        this.changeNameCount = changeNameCount;
    }

    public InFileChanger getInFileCh() {
        return inFileCh;
    }
}

