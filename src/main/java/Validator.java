import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    //sprawdzenie czy podana ścieżka odnosi się do drzewa plików będacych proxy
    private boolean checkIfProxy(String rootFilePath) {
        File parentFile = new File(rootFilePath);
        List<String> files = new ArrayList<>();
        for (File filesInsideRootFile : parentFile.listFiles()) {
            files.add(filesInsideRootFile.getName());
        }
        if (files.contains("pom.xml")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Podany folder, to nie proxy", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    //walidacja wprowadzonych danych
    public boolean validate(String s1, String s2, String s3) {

        if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()
                || s1.equals("ścieżka") || s2.equals("stara nazwa") || s3.equals("nowa nazwa (min5)")) {
            JOptionPane.showMessageDialog(null, "Nie podałeś wszystkich trzech wymaganych argumentów", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Files.notExists(Paths.get(s1)) || !Files.isDirectory(Paths.get(s1))) {
            JOptionPane.showMessageDialog(null, "Wprowadzono niepoprawną ścieżkę", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!checkIfProxy(s1)) {
            return false;
        } else if (s2.equals(s3)) {
            JOptionPane.showMessageDialog(null, "Podane nazwy są identyczne", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (s2.length() < 5 || s3.length() < 5) {
            JOptionPane.showMessageDialog(null, "Zbyt krótka nazwa (min. 5 znaków)", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        //sprawdzenie czy jako newName nie podano (CaseInsensitive) niedozwolonej nazwy
        else if (s3.equalsIgnoreCase("CLOCK$"))
                /*|| s3.equalsIgnoreCase("CON")
                || s3.equalsIgnoreCase("AUX")
                || s3.equalsIgnoreCase("NUL")
                || s3.equalsIgnoreCase("A:-Z")
                || s3.equalsIgnoreCase("COM1")
                || s3.equalsIgnoreCase("COM2")
                || s3.equalsIgnoreCase("COM3")
                || s3.equalsIgnoreCase("COM4")
                || s3.equalsIgnoreCase("LPT1")
                || s3.equalsIgnoreCase("LPT2")
                || s3.equalsIgnoreCase("LPT3"))*/ {
            JOptionPane.showMessageDialog(null, "Wprowadzono niedozwoloną nową nazwą pliku", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        //sprawdzenie czy nie ma "\" albo "/" we wprowadzanej nazwie
        else if (s3.contains("\\") || s3.contains("/")) {
            JOptionPane.showMessageDialog(null, "Wprowadzono niedozowolony znak slash lub backslash", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else
            return true;
    }
}
