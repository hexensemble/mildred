package com.hexensemble.mildred.desktop.launcher;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.hexensemble.mildred.desktop.DesktopLauncher;

/**
 * Abstract class for all states.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public abstract class State extends JFrame {

	private static final long serialVersionUID = 1L;

	protected JPanel window;

	/**
	 * Initialize.
	 */
	public State() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		window = new JPanel();

		setTitle(DesktopLauncher.TITLE);
		setSize(DesktopLauncher.WIDTH, DesktopLauncher.HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);

		create();
		actions();

		setVisible(true);
	}

	protected void create() {

	}

	protected void actions() {

	}

}
