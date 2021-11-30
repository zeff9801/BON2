package com.github.parker8283.bon2.gui;

import com.github.parker8283.bon2.BON2Gui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BrowseListener extends MouseAdapter {
    private BON2Gui parent;
    private boolean isOpen;
    private JTextField field;
    private JFileChooser fileChooser;

    public BrowseListener(BON2Gui parent, boolean isOpen, JTextField field) {
        this.parent = parent;
        this.isOpen = isOpen;
        this.field = field;
        this.fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileHidingEnabled(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String fileName = f.getName();
                return f.isDirectory() || fileName.endsWith(".jar");
            }

            @Override
            public String getDescription() {
                return "JAR mods only";
            }
        });
        String key = isOpen ? BON2Gui.PREFS_KEY_OPEN_LOC : BON2Gui.PREFS_KEY_SAVE_LOC;
        String savedDir = parent.prefs.get(key, Paths.get("").toAbsolutePath().toString());
        File currentDir = new File(savedDir);
        if(!Paths.get(savedDir).getRoot().toFile().exists()){
            currentDir = Paths.get("").toAbsolutePath().toFile();
        }
        while (!currentDir.isDirectory()) {
            currentDir = currentDir.getParentFile();
        }
        fileChooser.setCurrentDirectory(currentDir);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int returnState;
        if(isOpen) {
            returnState = fileChooser.showOpenDialog(parent);
        } else {
            returnState = fileChooser.showSaveDialog(parent);
        }
        if(returnState == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = null; 
            try {
                path = file.getCanonicalPath();
            } catch (IOException ex) {
                path = file.getAbsolutePath();
            }
            
            field.setText(path);
            
            String parentFolder = file.getParentFile().getAbsolutePath();
            if (isOpen) {
                parent.getOutputField().setText(path.replace(".jar", "-deobf.jar"));
                parent.prefs.put(BON2Gui.PREFS_KEY_OPEN_LOC, parentFolder);
            }
            parent.prefs.put(BON2Gui.PREFS_KEY_SAVE_LOC, parentFolder);
        }
    }
}
