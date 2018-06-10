import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.FileAlreadyExistsException;

public class GUI {

    private JTextField oldText;
    private JTextField newText;
    private JTextField filePath;
    private JFileChooser pathChooser = new JFileChooser();
    private Changer changer = new Changer();
    private Validator val = new Validator();

    public void makeGUI() {
        JFrame ramka = new JFrame("Zamiania nazw dla proxy");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        JButton sendButton = new JButton("Zamień");
        sendButton.addActionListener(new SendButtonListener());
        JButton pathButton = new JButton("path");
        pathButton.addActionListener(new PathButtonListener());
        oldText = new JTextField("stara nazwa (min5)", 20);
        oldText.addMouseListener(new TextFiledListener());
        newText = new JTextField("nowa nazwa (min5)", 20);
        newText.addMouseListener(new TextFiledListener());
        filePath = new JTextField("ścieżka", 20);
        filePath.addMouseListener(new TextFiledListener());
        panel2.add(oldText, panel2);
        panel2.add(newText, panel2);
        mainPanel.add(panel2, BorderLayout.CENTER);
        panel3.add(filePath, panel3);
        panel3.add(pathButton, panel3);
        mainPanel.add(panel3, BorderLayout.PAGE_START);
        mainPanel.add(sendButton, BorderLayout.PAGE_END);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ramka.getContentPane().add(BorderLayout.CENTER, mainPanel);
        ramka.setSize(400, 150);
        ramka.setVisible(true);
        ramka.setLocationRelativeTo(null);
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            String path = filePath.getText();
            String oldName = oldText.getText();
            String newName = newText.getText();
            try {
                if (val.validate(path, oldName, newName)) {
                    if (changer.copyTreeFiles(path, oldName, newName)) {
                        changer.change(oldText.getText(), newText.getText());
                        JOptionPane.showMessageDialog(null,
                                "Zmieniono nazw: " + changer.getFnc().getChangeNameCount() + "\n" + "Zmieniono w plikach: " + changer.getFnc().getInFileCh().getInFileCount());
                        changer.getFnc().setChangeNameCount(0);
                        changer.getFnc().getInFileCh().setInFileCount(0);
                    }
                }
            } catch (FileAlreadyExistsException ex) {
                JOptionPane.showMessageDialog(null, "Nazwy zostały już zmienione/istniały wcześniej", "error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Coś nie halo ;)", "error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public class PathButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = pathChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                filePath.setText(pathChooser.getSelectedFile().getPath());
                oldText.setText(pathChooser.getSelectedFile().getName());
            }
        }
    }

    public class TextFiledListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource().equals(filePath)) {
                if (filePath.getText().equals("ścieżka"))
                    filePath.setText("");
            } else if (e.getSource().equals(oldText)) {
                if (oldText.getText().equals("stara nazwa (min5)"))
                    oldText.setText("");
            } else if (e.getSource().equals(newText)) {
                if (newText.getText().equals("nowa nazwa (min5)"))
                    newText.setText("");
            }
        }
    }
}
