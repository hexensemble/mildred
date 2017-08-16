package com.hexensemble.mildred.desktop.launcher;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import com.hexensemble.mildred.system.CoreSettings;

/**
 * Represents the mod loader.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Beta 2.0.0
 */
public class ModsState extends State {

	private static final long serialVersionUID = 1L;

	private JPanel content;
	private JButton customLevels;
	private JButton clear;
	private JFileChooser fileChooser;
	private ArrayList<String> fileList;
	private int numberOfFiles;

	private JScrollPane scroll;
	private JTextArea text;

	private JPanel buttons;
	private JButton cancelButton;
	private JButton okButton;

	/**
	 * Initialize.
	 * 
	 */
	public ModsState() {

	}

	@Override
	protected void create() {
		super.create();

		content = new JPanel();
		content.setLayout(new FlowLayout());
		content.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		customLevels = new JButton("Load custom level(s)");
		content.add(customLevels);
		clear = new JButton("Clear list");
		content.add(clear);
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Load custom level(s)");
		fileChooser.setApproveButtonText("Load");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith(".tmx");
				}
			}

			@Override
			public String getDescription() {
				return "Tiled map files (*.tmx)";
			}
		});
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileList = new ArrayList<String>();
		if (CoreSettings.customLevelList != null && !CoreSettings.customLevelList.isEmpty()) {
			for (String s : CoreSettings.customLevelList) {
				fileList.add(s);
			}
		}
		numberOfFiles = fileList.size();

		scroll = new JScrollPane();
		text = new JTextArea(numberOfFiles + " file(s) loaded.");
		for (String s : fileList) {
			text.append("\n" + s);
		}
		text.setBorder(
				BorderFactory.createCompoundBorder(text.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		text.setEditable(false);
		scroll.setViewportView(text);

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);
		okButton = new JButton("OK");
		buttons.add(okButton);

		window.setLayout(new BorderLayout());
		window.add(content, BorderLayout.NORTH);
		window.add(scroll, BorderLayout.CENTER);
		window.add(buttons, BorderLayout.SOUTH);
	}

	@Override
	protected void actions() {
		super.actions();

		customLevels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
					for (File f : fileChooser.getSelectedFiles()) {
						fileList.add(f.toString());
					}
					numberOfFiles = fileList.size();
					text.setText(numberOfFiles + " file(s) loaded.");
					for (String s : fileList) {
						text.append("\n" + s);
					}
				}
			}
		});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setSelectedFiles(null);
				fileList.clear();
				numberOfFiles = fileList.size();
				text.setText(numberOfFiles + " file(s) loaded.");
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new MenuState();
			}
		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileList.size() == 0) {
					CoreSettings.customLevel = false;
					if (CoreSettings.customLevelList != null) {
						CoreSettings.customLevelList.clear();
					}
				} else {
					CoreSettings.customLevel = true;
					CoreSettings.customLevelList = new ArrayList<String>();
					for (String s : fileList) {
						CoreSettings.customLevelList.add(s);
					}
				}

				dispose();

				new MenuState();
			}
		});
	}

}
