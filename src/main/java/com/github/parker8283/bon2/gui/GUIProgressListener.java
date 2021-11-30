package com.github.parker8283.bon2.gui;

import com.github.parker8283.bon2.data.IProgressListener;

import javax.swing.*;

public class GUIProgressListener implements IProgressListener {
    private JLabel progressLabel;
    private JProgressBar progressBar;

    public GUIProgressListener(JLabel progressLabel, JProgressBar progressBar) {
        this.progressLabel = progressLabel;
        this.progressBar = progressBar;
    }

    @Override
    public void start(final int max, final String label) {
        SwingUtilities.invokeLater(() -> {
            progressLabel.setText(label);
            if(progressBar.isIndeterminate()) {
                progressBar.setIndeterminate(false);
            }
            if(max >= 0) {
                progressBar.setMaximum(max);
            }
            progressBar.setValue(0);
        });
    }

    @Override
    public void startWithoutProgress(final String label) {
        SwingUtilities.invokeLater(() -> {
            progressLabel.setText(label);
            progressBar.setIndeterminate(true);
        });
    }

    @Override
    public void setProgress(final int value) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(value));
    }

    @Override
    public void setMax(final int max) {
        SwingUtilities.invokeLater(() -> progressBar.setMaximum(max));
    }
    
    @Override
    public void setLabel(String label) {
        SwingUtilities.invokeLater(() -> progressLabel.setText(label));
    }
}
