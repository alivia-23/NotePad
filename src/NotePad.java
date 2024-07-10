import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;


public class NotePad extends JFrame implements ActionListener, WindowListener {
    JTextArea jta = new JTextArea();
    File fnameContainer;

    public NotePad() {
        Font fnt = new Font("Arial", Font.PLAIN, 15);
        Container con = getContentPane();
        JMenuBar jmb = new JMenuBar(); // Menu bar where we'll display different options
        JMenu jmfile = new JMenu("File");
        JMenu jmedit = new JMenu("Edit");
        JMenu jmhelp = new JMenu("Help");

        con.setLayout(new BorderLayout());
        JScrollPane sbrText = new JScrollPane(jta);
        sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sbrText.setVisible(true);

        jta.setFont(fnt);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);

        con.add(sbrText);

        createMenuItem(jmfile, "New");
        createMenuItem(jmfile, "Open");
        createMenuItem(jmfile, "Save");
        jmfile.addSeparator();
        createMenuItem(jmfile, "Exit");

        createMenuItem(jmedit, "Cut");
        createMenuItem(jmedit, "Copy");
        createMenuItem(jmedit, "Paste");

        createMenuItem(jmhelp, "About NotePad");

        jmb.add(jmfile);
        jmb.add(jmedit);
        jmb.add(jmhelp);

        setJMenuBar(jmb);

        setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
        addWindowListener(this);
        setTitle("Untitled.txt - NotePad");
        setVisible(true);

    }

    public void createMenuItem(JMenu jmfile, String text) {
        JMenuItem jmi = new JMenuItem(text);
        jmi.addActionListener(this);
        jmfile.add(jmi);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        if (e.getActionCommand().equals("New")) {
            this.setTitle("Untitled.txt - NotePad");
            jta.setText("");
            fnameContainer = null;
        } else if (e.getActionCommand().equals("Open")) {
            int ret = jfc.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File fyl = jfc.getSelectedFile();
                    OpenFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName() + " - NotePad");
                    fnameContainer = fyl;
                } catch (IOException ioe) {}
            }

        } else if (e.getActionCommand().equals("Save")) {
            if (fnameContainer != null) {
                jfc.setCurrentDirectory(fnameContainer);
                jfc.setSelectedFile(fnameContainer);
            } else {
            jfc.setSelectedFile(new File("Untitled.txt"));
            }
            int ret = jfc.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File fyl = jfc.getSelectedFile();
                    SaveFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName() + " - NotePad");
                    fnameContainer = fyl;
                } catch (Exception ex) {}
            }
        } else if (e.getActionCommand().equals("Exit")) {
            Exiting();
        } else if (e.getActionCommand().equals("Copy")) {
            jta.copy();
        } else if (e.getActionCommand().equals("Paste")) {
            jta.paste();
        } else if (e.getActionCommand().equals("About NotePad")) {
            JOptionPane.showMessageDialog(this, "Created by : Geeks for Geeks","NotePad", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getActionCommand().equals("Cut")) {
            jta.cut();
        }
    }

    private void SaveFile(String fname) throws Exception {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream o = new DataOutputStream(new FileOutputStream(fname));
        o.writeBytes(jta.getText());
        o.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void OpenFile(String fname) throws IOException {
        BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String l;
        jta.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        while ((l = d.readLine()) != null) {
            jta.setText(jta.getText() + l + "\r\n");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        d.close();
    }
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
        Exiting();
    }
    @Override
    public void windowOpened(WindowEvent e) {}
    public void Exiting() {
        System.exit(0);
    }
}
