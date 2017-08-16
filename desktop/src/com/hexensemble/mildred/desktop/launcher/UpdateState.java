package com.hexensemble.mildred.desktop.launcher;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hexensemble.mildred.system.CoreSettings;

/**
 * Represents the update check state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class UpdateState extends State {

	private static final long serialVersionUID = 1L;

	private static final String UPDATER_JAR = "MildredUpdater.jar";
	private static final String UPDATER_EXE = "MildredUpdater.exe";

	private JScrollPane content;
	private JTextArea text;

	private JPanel buttons;
	private JButton cancelButton;
	private JButton updateButton;

	/**
	 * Initialize.
	 */
	public UpdateState() {
		super();

	}

	@Override
	protected void create() {
		super.create();

		content = new JScrollPane();
		text = new JTextArea();
		try {
			text.setText(Updater.getVersion());
			text.append("\n" + Updater.getDate());
			text.append("\n" + Updater.getChanges());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		text.setBorder(
				BorderFactory.createCompoundBorder(text.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		text.setEditable(false);
		content.setViewportView(text);

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);
		updateButton = new JButton("Update");
		try {
			if (!Updater.getVersion().equals(CoreSettings.VERSION)) {
				updateButton.setEnabled(true);
			} else {
				updateButton.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		buttons.add(updateButton);

		window.setLayout(new BorderLayout());
		window.add(content, BorderLayout.CENTER);
		window.add(buttons, BorderLayout.SOUTH);
	}

	@Override
	protected void actions() {
		super.actions();

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File settingsFile = new File(DesktopSettings.SETTINGS_FILE);
				if (settingsFile.exists()) {
					dispose();

					new MenuState();
				} else {
					dispose();

					new SettingsState();
				}
			}
		});

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File updaterFile = new File(UPDATER_JAR);
					if (!updaterFile.exists()) {
						updaterFile = new File(UPDATER_EXE);
						Desktop.getDesktop().open(new File(UPDATER_EXE));
					} else {
						Desktop.getDesktop().open(new File(UPDATER_JAR));
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				System.exit(0);
			}
		});
	}

}
