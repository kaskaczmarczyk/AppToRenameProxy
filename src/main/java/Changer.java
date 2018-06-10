import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Changer {

    private NamesChanger fnc = new NamesChanger();
    private String newPath;

        public boolean copyTreeFiles(String rootfilepath, String oldName, String newName) throws IOException {
           String parentPath = Paths.get(rootfilepath).getParent().toString();
           String name = Paths.get(rootfilepath).getFileName().toString();
           name = name.replace(oldName, newName);
           newPath = Paths.get(parentPath,name).toString();
           if (rootfilepath.equals(newPath)) {
               newPath = newPath + " — kopia";
           }
            File srcDir = new File(rootfilepath);
            File destDir = new File(newPath);
            if(Files.exists(Paths.get(newPath))) {
                JOptionPane.showMessageDialog(null, "Folder docelowy już istnieje", "error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                FileUtils.copyDirectory(srcDir, destDir);
                return true;
            }
        }

    public void change (String oldname, String newname) throws IOException {
            fnc.changeName(newPath,oldname,newname);
    }

    public NamesChanger getFnc() {
        return fnc;
    }
}
