package me.n1ar4.jar.analyzer.plugins.repeater;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class HttpUtilForm {
    public JPanel httpUtilPanel;
    private JTextArea reqArea;
    private JTextArea respArea;
    private JButton reqButton;
    private JTextField ipText;
    private JTextField portText;
    private JLabel reqLabel;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JPanel ipPanel;
    private JScrollPane reqScroll;
    private JScrollPane respScroll;

    private void initLang() {
        reqLabel.setText("  Request");
        ipLabel.setText("  Target IP");
        portLabel.setText("  Target Port");
        reqButton.setText("Send");
    }

    public HttpUtilForm() {
        initLang();
        reqButton.addActionListener(e -> {
            String ip = ipText.getText().trim();
            String port = portText.getText().trim();
            String req = reqArea.getText().trim();
            if (!StringUtil.notEmpty(ip) || !StringUtil.notEmpty(port)) {
                JOptionPane.showMessageDialog(this.httpUtilPanel, "need ip port");
                return;
            }
            int portInt = Integer.parseInt(port);
            new Thread(() -> {
                String finalReq = req + "\r\n\r\n";
                String resp = SocketUtil.sendRaw(ip, portInt, finalReq);
                respArea.setText(resp);
                respArea.setCaretPosition(0);
            }).start();
        });
    }

    public static void start() {
        JFrame frame = new JFrame("Jar Analyzer V2 - Repeater");
        frame.setContentPane(new HttpUtilForm().httpUtilPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        httpUtilPanel = new JPanel();
        httpUtilPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        httpUtilPanel.setBackground(new Color(-1120293));
        ipPanel = new JPanel();
        ipPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        ipPanel.setBackground(new Color(-1120293));
        httpUtilPanel.add(ipPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ipLabel = new JLabel();
        ipLabel.setText("  目标IP");
        ipPanel.add(ipLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ipText = new JTextField();
        ipPanel.add(ipText, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        portLabel = new JLabel();
        portLabel.setText("  目标端口");
        ipPanel.add(portLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        portText = new JTextField();
        ipPanel.add(portText, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        reqLabel = new JLabel();
        reqLabel.setText("请求");
        ipPanel.add(reqLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqButton = new JButton();
        reqButton.setEnabled(true);
        reqButton.setText("发送");
        ipPanel.add(reqButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reqScroll = new JScrollPane();
        reqScroll.setBackground(new Color(-1120293));
        reqScroll.setToolTipText("");
        httpUtilPanel.add(reqScroll, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(500, 600), new Dimension(500, 600), new Dimension(500, 600), 0, false));
        reqScroll.setBorder(BorderFactory.createTitledBorder(null, "request", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        reqArea = new JTextArea();
        reqArea.setBackground(new Color(-853761));
        Font reqAreaFont = this.$$$getFont$$$("Consolas", -1, 18, reqArea.getFont());
        if (reqAreaFont != null) reqArea.setFont(reqAreaFont);
        reqArea.setLineWrap(true);
        reqArea.setText("");
        reqScroll.setViewportView(reqArea);
        respScroll = new JScrollPane();
        respScroll.setBackground(new Color(-1120293));
        httpUtilPanel.add(respScroll, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(500, 600), new Dimension(500, 600), new Dimension(500, 600), 0, false));
        respScroll.setBorder(BorderFactory.createTitledBorder(null, "response", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        respArea = new JTextArea();
        respArea.setBackground(new Color(-853761));
        Font respAreaFont = this.$$$getFont$$$("Consolas", -1, 18, respArea.getFont());
        if (respAreaFont != null) respArea.setFont(respAreaFont);
        respArea.setLineWrap(true);
        respArea.setText("");
        respArea.setWrapStyleWord(false);
        respScroll.setViewportView(respArea);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return httpUtilPanel;
    }

}
