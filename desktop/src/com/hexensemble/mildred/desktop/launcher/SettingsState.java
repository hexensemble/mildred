package com.hexensemble.mildred.desktop.launcher;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the settings state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class SettingsState extends State {

	private static final long serialVersionUID = 1L;

	private File settingsFile;
	private Properties properties;

	private JPanel content;
	private JLabel resolution;
	private Choice resolutionChoice;
	private JCheckBox fullscreen;
	private JCheckBox vSync;
	private JCheckBox fps;
	private String sfx;
	private String music;

	private JPanel buttons;
	private JButton cancelButton;
	private JButton okButton;

	/**
	 * Initialize.
	 */
	public SettingsState() {
		super();

	}

	@Override
	protected void create() {
		super.create();

		settingsFile = new File(DesktopSettings.SETTINGS_FILE);
		properties = new Properties();

		content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(100, 225, 100, 225));

		resolution = new JLabel("Resolution");
		content.add(resolution);
		resolutionChoice = new Choice();
		resolutionChoice.add("640x480");
		resolutionChoice.add("800x600");
		resolutionChoice.add("1024x768");
		resolutionChoice.add("1152x720");
		resolutionChoice.add("1280x800");
		resolutionChoice.add("1440x900");
		resolutionChoice.add("1650x1050");
		resolutionChoice.add("2048x1280");
		resolutionChoice.add("2560x1600");
		if (settingsFile.exists()) {
			resolutionChoice.select(getResolution());
		} else {
			resolutionChoice.select(DesktopSettings.RESOLUTION_DEFAULT);
		}
		content.add(resolutionChoice);

		content.add(Box.createRigidArea(new Dimension(0, 25)));

		fullscreen = new JCheckBox("Fullscreen");
		if (settingsFile.exists()) {
			try {
				if (DesktopSettings.load("fullscreen").equals("true")) {
					fullscreen.setSelected(true);
				} else {
					fullscreen.setSelected(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			fullscreen.setSelected(DesktopSettings.FULLSCREEN_DEFAULT);
		}
		content.add(fullscreen);

		content.add(Box.createRigidArea(new Dimension(0, 25)));

		vSync = new JCheckBox("vSync Enabled");
		if (settingsFile.exists()) {
			try {
				if (DesktopSettings.load("vsync").equals("true")) {
					vSync.setSelected(true);
				} else {
					vSync.setSelected(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			vSync.setSelected(DesktopSettings.VSYNC_DEFAULT);
		}
		content.add(vSync);

		content.add(Box.createRigidArea(new Dimension(0, 25)));

		fps = new JCheckBox("Show FPS");
		if (settingsFile.exists()) {
			try {
				if (DesktopSettings.load("fps").equals("true")) {
					fps.setSelected(true);
				} else {
					fps.setSelected(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			fps.setSelected(DesktopSettings.FPS_DEFAULT);
		}
		content.add(fps);

		content.add(Box.createRigidArea(new Dimension(0, 25)));

		if (settingsFile.exists()) {
			try {
				sfx = DesktopSettings.load("sfx");
				music = DesktopSettings.load("music");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			sfx = DesktopSettings.SFX_DEFAULT;
			music = DesktopSettings.MUSIC_DEFAULT;
		}

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);
		okButton = new JButton("OK");
		buttons.add(okButton);

		window.setLayout(new BorderLayout());
		window.add(content, BorderLayout.NORTH);
		window.add(buttons, BorderLayout.SOUTH);
	}

	@Override
	protected void actions() {
		super.actions();

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
				try {
					Integer resolutionWidth = getResolutionWidth();
					DesktopSettings.save(properties, "width", resolutionWidth.toString());

					Integer resolutionHeight = getResolutionHeight();
					DesktopSettings.save(properties, "height", resolutionHeight.toString());

					if (fullscreen.isSelected()) {
						DesktopSettings.save(properties, "fullscreen", "true");
					} else {
						DesktopSettings.save(properties, "fullscreen", "false");
					}

					if (vSync.isSelected()) {
						DesktopSettings.save(properties, "vsync", "true");
					} else {
						DesktopSettings.save(properties, "vsync", "false");
					}

					if (fps.isSelected()) {
						DesktopSettings.save(properties, "fps", "true");
					} else {
						DesktopSettings.save(properties, "fps", "false");
					}

					DesktopSettings.save(properties, "sfx", sfx);
					DesktopSettings.save(properties, "music", music);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				dispose();

				new MenuState();
			}
		});
	}

	private int getResolutionWidth() {
		int resolutionWidth = 0;

		if (resolutionChoice.getSelectedItem().equals("640x480")) {
			resolutionWidth = 640;
		} else if (resolutionChoice.getSelectedItem().equals("800x600")) {
			resolutionWidth = 800;
		} else if (resolutionChoice.getSelectedItem().equals("1024x768")) {
			resolutionWidth = 1024;
		} else if (resolutionChoice.getSelectedItem().equals("1152x720")) {
			resolutionWidth = 1152;
		} else if (resolutionChoice.getSelectedItem().equals("1280x800")) {
			resolutionWidth = 1280;
		} else if (resolutionChoice.getSelectedItem().equals("1440x900")) {
			resolutionWidth = 1440;
		} else if (resolutionChoice.getSelectedItem().equals("1650x1050")) {
			resolutionWidth = 1650;
		} else if (resolutionChoice.getSelectedItem().equals("2048x1280")) {
			resolutionWidth = 2048;
		} else if (resolutionChoice.getSelectedItem().equals("2560x1600")) {
			resolutionWidth = 2560;
		} else
			resolutionWidth = DesktopSettings.WIDTH_DEFAULT;

		return resolutionWidth;
	}

	private int getResolutionHeight() {
		int resolutionHeight = 0;

		if (resolutionChoice.getSelectedItem().equals("640x480")) {
			resolutionHeight = 480;
		} else if (resolutionChoice.getSelectedItem().equals("800x600")) {
			resolutionHeight = 600;
		} else if (resolutionChoice.getSelectedItem().equals("1024x768")) {
			resolutionHeight = 768;
		} else if (resolutionChoice.getSelectedItem().equals("1152x720")) {
			resolutionHeight = 720;
		} else if (resolutionChoice.getSelectedItem().equals("1280x800")) {
			resolutionHeight = 800;
		} else if (resolutionChoice.getSelectedItem().equals("1440x900")) {
			resolutionHeight = 900;
		} else if (resolutionChoice.getSelectedItem().equals("1650x1050")) {
			resolutionHeight = 1050;
		} else if (resolutionChoice.getSelectedItem().equals("2048x1280")) {
			resolutionHeight = 1280;
		} else if (resolutionChoice.getSelectedItem().equals("2560x1600")) {
			resolutionHeight = 1600;
		} else
			resolutionHeight = DesktopSettings.HEIGHT_DEFAULT;

		return resolutionHeight;
	}

	private String getResolution() {
		String resolution = null;

		try {
			if (DesktopSettings.load("width").equals("640")) {
				resolution = "640x480";
			} else if (DesktopSettings.load("width").equals("800")) {
				resolution = "800x600";
			} else if (DesktopSettings.load("width").equals("1024")) {
				resolution = "1024x768";
			} else if (DesktopSettings.load("width").equals("1152")) {
				resolution = "1152x720";
			} else if (DesktopSettings.load("width").equals("1280")) {
				resolution = "1280x800";
			} else if (DesktopSettings.load("width").equals("1440")) {
				resolution = "1440x900";
			} else if (DesktopSettings.load("width").equals("1650")) {
				resolution = "1650x1050";
			} else if (DesktopSettings.load("width").equals("2048")) {
				resolution = "2048x1280";
			} else if (DesktopSettings.load("width").equals("2560")) {
				resolution = "2560x1600";
			} else
				resolution = DesktopSettings.RESOLUTION_DEFAULT;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resolution;
	}

}
