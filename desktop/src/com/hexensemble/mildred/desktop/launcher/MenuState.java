package com.hexensemble.mildred.desktop.launcher;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.hexensemble.mildred.Mildred;
import com.hexensemble.mildred.system.CoreSettings;

/**
 * Represents the main menu state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class MenuState extends State {

	private static final long serialVersionUID = 1L;

	private BufferedInputStream imageFile;
	private BufferedImage image;
	private JLabel imageLabel;

	private JPanel buttons;
	private JButton playButton;
	private JButton updateButton;
	private JButton settingsButton;
	private JButton modsButton;
	private JButton aboutButton;
	private JButton quitButton;

	/**
	 * Initialize.
	 */
	public MenuState() {
		super();

	}

	@Override
	protected void create() {
		super.create();

		imageFile = new BufferedInputStream(MenuState.class.getResourceAsStream("launcher-background.jpg"));
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageLabel = new JLabel(new ImageIcon(image));

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		playButton = new JButton("Play");
		buttons.add(playButton);
		updateButton = new JButton("Update");
		try {
			if (Updater.isReachable()) {
				if (!Updater.getVersion().equals(CoreSettings.VERSION)) {
					updateButton.setEnabled(true);
				} else {
					updateButton.setEnabled(false);
				}
			} else {
				updateButton.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		buttons.add(updateButton);
		settingsButton = new JButton("Settings");
		buttons.add(settingsButton);
		modsButton = new JButton("Mods");
		buttons.add(modsButton);
		aboutButton = new JButton("About");
		buttons.add(aboutButton);
		quitButton = new JButton("Quit");
		buttons.add(quitButton);

		window.setLayout(new BorderLayout());
		window.add(imageLabel, BorderLayout.CENTER);
		window.add(buttons, BorderLayout.SOUTH);
	}

	@Override
	protected void actions() {
		super.actions();

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				DesktopSettings.loadScreenSettings();

				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
				config.title = CoreSettings.TITLE;
				config.width = DesktopSettings.width;
				config.height = DesktopSettings.height;
				config.fullscreen = DesktopSettings.fullscreen;
				config.vSyncEnabled = DesktopSettings.vsync;

				new LwjglApplication(new Mildred(), config);
			}
		});

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new UpdateState();
			}
		});

		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new SettingsState();
			}
		});

		modsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new ModsState();
			}
		});

		aboutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new AboutState();
			}
		});

		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

}
