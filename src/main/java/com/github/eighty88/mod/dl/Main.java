package com.github.eighty88.mod.dl;

import com.github.eighty88.api.downloader.Downloader;
import com.github.eighty88.mod.dl.config.Config;
import com.github.eighty88.mod.dl.config.ConfigLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Main  extends JFrame implements ActionListener, MouseListener {
    static Config config;

    private JLabel versionInfo = null;
    private JLabel labelFolder = null;

    private JPanel panelCenter = null;
    private JPanel panelBottom = null;
    private JPanel totalContentPane = null;


    private JTextField textFieldFolderLocation = null;
    private JButton buttonChooseFolder = null;

    private JButton buttonInstall = null;
    private JButton buttonOpenFolder = null;
    private JButton buttonClose = null;

    private static final int TOTAL_HEIGHT = 150;
    private static final int TOTAL_WIDTH = 404;

    private int x = 0;
    private int y = 0;

    private int w = TOTAL_WIDTH;
    private int h;

    public Main() {
        try {
            setName("InstantModDownloader");
            setTitle(config.getTitle());
            setResizable(false);
            setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
            setContentPane(getPanelContentPane());

            getButtonFolder().addActionListener(this);
            getButtonInstall().addActionListener(this);
            getButtonOpenFolder().addActionListener(this);
            getButtonClose().addActionListener(this);

            pack();
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            getButtonInstall().setEnabled(true);
            getButtonInstall().requestFocus();
        } catch (Exception ex) {
            showErrorPopup(ex);
        }
    }

    public static void main(String[] args) {
        config = new ConfigLoader().getConfig();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Main main = new Main();

        main.centerFrame(main);
        main.setVisible(true);
    }


    private JPanel getPanelContentPane() {
        if (totalContentPane == null) {
            try {
                totalContentPane = new JPanel();
                totalContentPane.setName("PanelContentPane");
                totalContentPane.setLayout(new BorderLayout(5, 5));
                totalContentPane.setPreferredSize(new Dimension(TOTAL_WIDTH, TOTAL_HEIGHT));
                totalContentPane.add(getPanelCenter(), "Center");
                totalContentPane.add(getPanelBottom(), "South");
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return totalContentPane;
    }

    private JPanel getPanelCenter() {
        if (panelCenter == null) {
            try {
                (panelCenter = new JPanel()).setName("PanelCenter");
                panelCenter.setLayout(null);
                panelCenter.add(getVersionInfo(), getVersionInfo().getName());
                panelCenter.add(getLabelFolder(), getLabelFolder().getName());
                panelCenter.add(getFieldFolder(), getFieldFolder().getName());
                panelCenter.add(getButtonFolder(), getButtonFolder().getName());
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return panelCenter;
    }



    private JLabel getVersionInfo() {
        if (versionInfo == null) {
            try {
                h = 25;

                versionInfo = new JLabel();
                versionInfo.setName("LabelMcVersion");
                versionInfo.setBounds(x, y, w, h);
                versionInfo.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
                versionInfo.setHorizontalAlignment(SwingConstants.CENTER);
                versionInfo.setPreferredSize(new Dimension(w, h));

                y += h;
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return versionInfo;
    }

    private JLabel getLabelFolder() {
        if (labelFolder == null) {
            h = 16;
            w = 30;

            x += 10; // Padding

            try {
                labelFolder = new JLabel();
                labelFolder.setName("LabelFolder");
                labelFolder.setBounds(x, y+2, w, h);
                labelFolder.setPreferredSize(new Dimension(w, h));
                labelFolder.setText("");
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }

            x += w;
        }
        return labelFolder;
    }

    private JTextField getFieldFolder() {
        if (textFieldFolderLocation == null) {
            h = 20;
            w = 287;

            try {
                textFieldFolderLocation = new JTextField();
                textFieldFolderLocation.setName("FieldFolder");
                textFieldFolderLocation.setBounds(x, y, w, h);
                textFieldFolderLocation.setEditable(false);
                textFieldFolderLocation.setPreferredSize(new Dimension(w, h));
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }

            x += w;
        }
        return textFieldFolderLocation;
    }

    private JButton getButtonFolder() {
        if (buttonChooseFolder == null) {
            h = 20;
            w = 25;

            x += 10; // Padding

            try {
                BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream("assets/folder.png"), "Folder icon not found."));
                Image scaled = myPicture.getScaledInstance(w-8, h-6, Image.SCALE_SMOOTH);
                buttonChooseFolder = new JButton(new ImageIcon(scaled));
                buttonChooseFolder.setName("ButtonFolder");
                buttonChooseFolder.setBounds(x, y, w, h);
                buttonChooseFolder.setPreferredSize(new Dimension(w, h));
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return buttonChooseFolder;
    }

    private JPanel getPanelBottom() {
        if (panelBottom == null) {
            try {
                panelBottom = new JPanel();
                panelBottom.setName("PanelBottom");
                panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
                panelBottom.setPreferredSize(new Dimension(100, 55));
                panelBottom.add(getButtonInstall(), getButtonInstall().getName());
                panelBottom.add(getButtonClose(), getButtonClose().getName());
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return panelBottom;
    }

    private JButton getButtonInstall() {
        if (buttonInstall == null) {
            w = 100;
            h = 26;

            try {
                buttonInstall = new JButton();
                buttonInstall.setName("ButtonInstall");
                buttonInstall.setPreferredSize(new Dimension(w, h));
                buttonInstall.setText("Download");
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return buttonInstall;
    }

    private JButton getButtonOpenFolder() {
        if (buttonOpenFolder == null) {
            w = 130;
            h = 26;

            try {
                buttonOpenFolder = new JButton();
                buttonOpenFolder.setName("ButtonOpenFolder");
                buttonOpenFolder.setPreferredSize(new Dimension(w, h));
                buttonOpenFolder.setText("Open Folder");
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return buttonOpenFolder;
    }

    private JButton getButtonClose() {
        if (buttonClose == null) {
            w = 100;
            h = 26;

            try {
                (buttonClose = new JButton()).setName("ButtonClose");
                buttonClose.setPreferredSize(new Dimension(w, h));
                buttonClose.setText("Cancel");
            } catch (Throwable ivjExc) {
                showErrorPopup(ivjExc);
            }
        }
        return buttonClose;
    }

    public void onFolderSelect() {
        File currentDirectory = new File(getFieldFolder().getText());

        JFileChooser jFileChooser = new JFileChooser(currentDirectory);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        if (jFileChooser.showOpenDialog(this) == 0) {
            File newDirectory = jFileChooser.getSelectedFile();
            getFieldFolder().setText(newDirectory.getPath());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getButtonClose()) {
            dispose();
            System.exit(0);
        }
        if (e.getSource() == getButtonFolder()) {
            onFolderSelect();
        }
        if (e.getSource() == getButtonInstall()) {
            onInstall();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void onInstall() {
        try {
            File modsFolder = new File(getFieldFolder().getText());
            if (!modsFolder.exists()) {
                showErrorMessage("Folder not found.");
                return;
            }
            if (!modsFolder.isDirectory()) {
                showErrorMessage("Not a folder.");
                return;
            }
            tryInstall(modsFolder);
        } catch (Exception e) {
            showErrorPopup(e);
        }
    }

    private void tryInstall(File modsFolder) {
        List<String> urls = null;
        int i = 0;
        try {
            urls = readURL();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for(String s: Objects.requireNonNull(urls)) {
            setTitle(config.getTitle() + " (" + i + "/" + (urls.size() + 1) + ")");
            try {
                Downloader downloader = new Downloader(s);
                downloader.DownloadTo(modsFolder.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                i++;
            }
        }
        setTitle(config.getTitle());
    }

    public void centerFrame(JFrame frame) {
        Rectangle rectangle = frame.getBounds();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);

        int newX = screenRectangle.x + (screenRectangle.width - rectangle.width) / 2;
        int newY = screenRectangle.y + (screenRectangle.height - rectangle.height) / 2;

        if (newX < 0) newX = 0;
        if (newY < 0) newY = 0;

        frame.setBounds(newX, newY, rectangle.width, rectangle.height);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static String getStacktraceText(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString().replace("\t", "  ");
    }

    private static void showErrorPopup(Throwable ex) {
        ex.printStackTrace();

        JTextArea textArea = new JTextArea(getStacktraceText(ex));
        textArea.setEditable(false);
        Font currentFont = textArea.getFont();
        Font newFont = new Font(Font.MONOSPACED, currentFont.getStyle(), currentFont.getSize());
        textArea.setFont(newFont);

        JScrollPane errorScrollPane = new JScrollPane(textArea);
        errorScrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, errorScrollPane, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private List<String> readURL() throws Exception {
        String urlString = config.getJsonURL();

        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                builder.append(chars, 0, read);

            java.lang.reflect.Type ListType = new TypeToken<List<String>>(){}.getType();
            return new Gson().fromJson(builder.toString(), ListType);
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
