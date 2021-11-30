package com.github.parker8283.bon2.gui;

import com.github.parker8283.bon2.BON2Gui;
import com.github.parker8283.bon2.BON2Impl;
import com.github.parker8283.bon2.data.MappingVersion;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class StartListener extends MouseAdapter {
    private BON2Gui parent;
    private Thread run = null;
    private JTextField input;
    private JTextField output;
    private JComboBox<MappingVersion> forgeVer;
    private JLabel progressLabel;
    private JProgressBar progressBar;

    public StartListener(BON2Gui parent, JTextField input, JTextField output, JComboBox<MappingVersion> forgeVer, JLabel progressLabel, JProgressBar progressBar) {
        this.parent = parent;
        this.input = input;
        this.output = output;
        this.forgeVer = forgeVer;
        this.progressLabel = progressLabel;
        this.progressBar = progressBar;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!input.getText().endsWith(".jar") || !output.getText().endsWith(".jar")) {
            JOptionPane.showMessageDialog(parent, "Nice try, but only JAR mods work.", BON2Gui.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
        }
        if(run != null && run.isAlive()) {
            return;
        }
        run = new Thread("BON2 Remapping Thread") {
            @Override
            public void run() {
                try {
                    BON2Impl.remap(new File(input.getText()), new File(output.getText()), forgeVer.getItemAt(forgeVer.getSelectedIndex()), new GUIErrorHandler(parent), new GUIProgressListener(progressLabel, progressBar));
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(parent, "There was an error.\n" + ex.toString() + "\n" + getFormattedStackTrace(ex.getStackTrace()), BON2Gui.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        run.start();
    }

    private String getFormattedStackTrace(StackTraceElement[] stacktrace) {
        StringBuilder sb = new StringBuilder();
        for(StackTraceElement element : stacktrace) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
