package com.hexensemble.mildred.desktop.launcher;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.hexensemble.mildred.states.CreditsState;

/**
 * Represents the about/credits state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since Alpha 1.0.0
 */
public class AboutState extends State {

	private static final long serialVersionUID = 1L;

	private JTextPane text;

	private JPanel buttons;
	private JButton okButton;

	/**
	 * Initialize.
	 * 
	 */
	public AboutState() {
		super();

	}

	@Override
	protected void create() {
		super.create();

		text = new JTextPane();
		text.setContentType("text/html");
		text.setEditable(false);
		text.setText("<div align=center>" + "<h1>" + CreditsState.TITLE + "</h1>" + "<br>" + CreditsState.AUTHOR + "<p>"
				+ CreditsState.VERSION + "<br>" + CreditsState.DATE + "</p>" + "<p><b>" + CreditsState.PROGRAMMING
				+ "</b><br>" + CreditsState.PROGRAMMER + "</p>" + "<p><b>" + CreditsState.ART + "</b><br>"
				+ CreditsState.ARTIST + "</p>" + "<p><b>" + CreditsState.MUSIC + "</b><br>" + CreditsState.MUSICIAN
				+ "</p>" + "<p>" + CreditsState.COPYRIGHT + "<br>" + CreditsState.ART_LICENCE_1 + "<br>"
				+ CreditsState.ART_LICENCE_2 + "</p></div>");

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		okButton = new JButton("OK");
		buttons.add(okButton);

		window.setLayout(new BorderLayout());
		window.add(text, BorderLayout.CENTER);
		window.add(buttons, BorderLayout.SOUTH);
	}

	@Override
	protected void actions() {
		super.actions();

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new MenuState();
			}
		});
	}

}
