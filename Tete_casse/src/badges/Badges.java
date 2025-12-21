package badges;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import outils.OutilsImage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.border.TitledBorder;

import GestionDesFichiers.GestionnaireDesDonnes;

import javax.swing.border.EtchedBorder;

/**
 * Classe représentant les badges.
 * 
 * @author Liangchen Liu
 */
public class Badges extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnReturn;
	private ImageIcon iconeReturn;
	private JLabel lblTitreBadges;
	private JLabel lblBadgeOuvrirJeu1;
	private JLabel lblInfoOuvrirJeu1;
	private JPanel panelBordureOuvrirJeu1;
	private JLabel lblInfoSurvolerTitreBadge2;

	private JPanel panelBordureSurvolerTitreBadge2;
	private JLabel lblBadgeSurvolerTitreBadge2;

	private DonnesBadges donnesBadges;

	private boolean badgeSurvolerTitreBadge2;
	private boolean badgeOuvrirClassement3;
	private boolean badgeOuvrirApropos4;
	private boolean badgeGciruit5;
	private boolean badgeGcurling6;
	private boolean badgeGpeche7;
	private boolean badgeGtank8;

	/** Les instructions lié à la touche "p" du clavier */
	private final String INSTRUCTIONS = "Survolez le badge \"Premiers pas\" pour voir l'effet et le message quand un badge est déja débloqué \n"
			+ "Survolez le badge \"Expert du survol \" pour voir l'effet quand un badge n'est pas débloqué \n"
			+ "Survolez le titre et ensuite resulvoler le badge \"Expert du survol \" pour voir l'effet\n";
	private JLabel lblBadgeOuvrirClassement3;
	private JPanel panelBordureOuvrirClassement3;
	private JLabel lblOuvrirClassement3;
	private JPanel panelBordureOuvrirApropos4;
	private JLabel lblBadgeOuvrirApropos4;
	private JLabel lblOuvrirApropos4;
	private JPanel panelBordureGciruit5;
	private JLabel lblBadgeGciruit5;
	private JLabel lblGciruit5;
	private JPanel panelBordureGcurling6;
	private JLabel lblBadgeGcurling6;
	private JLabel lblGcurling6;
	private JPanel panelBordureGpeche7;
	private JLabel lblBadgeGpeche7;
	private JLabel lblGpeche7;
	private JPanel panelBordureGtank8;
	private JLabel lblBadgeGtank8;
	private JLabel lblGtank8;

	/**
	 * Créer le panel.
	 */
	public Badges() {
		donnesBadges = GestionnaireDesDonnes.getInstance().getDonnesBadges();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					JOptionPane.showMessageDialog(null, INSTRUCTIONS, "Fonctionnalités présentes",
							JOptionPane.INFORMATION_MESSAGE);
					requestFocusInWindow();
				} // fin if

			}// fin méthode

		});// fin listener

		btnReturn = new JButton("");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange("menuAccueil", 1, -1);
			}// fin methode
		});// fin actionListener
		btnReturn.setBounds(63, 40, 47, 47);
		iconeReturn = new ImageIcon(OutilsImage.lireImageEtRedimensionner("returndark.png", 46, 46));
		setLayout(null);
		btnReturn.setIcon(iconeReturn);
		btnReturn.setBackground(new Color(0, 0, 0, 0));
		btnReturn.setOpaque(false);
		btnReturn.setBorder(null);
		add(btnReturn);

		lblTitreBadges = new JLabel("Badges");
		lblTitreBadges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				badgeSurvolerTitreBadge2 = true;
				GestionnaireDesDonnes.getInstance().getDonnesBadges()
						.setBadgeSurvolerTitreBadge2(badgeSurvolerTitreBadge2);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblInfoOuvrirJeu1.setVisible(false);
				panelBordureSurvolerTitreBadge2.setBackground(Color.white);
			}
		});
		lblTitreBadges.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitreBadges.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreBadges.setBounds(553, 61, 193, 56);
		add(lblTitreBadges);

		panelBordureOuvrirJeu1 = new JPanel();
		panelBordureOuvrirJeu1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoOuvrirJeu1.setVisible(true);
				panelBordureOuvrirJeu1.setBackground(Color.CYAN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblInfoOuvrirJeu1.setVisible(false);
				panelBordureOuvrirJeu1.setBackground(Color.white);
			}
		});
		panelBordureOuvrirJeu1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureOuvrirJeu1.setBounds(151, 144, 232, 68);
		add(panelBordureOuvrirJeu1);
		panelBordureOuvrirJeu1.setLayout(null);

		lblBadgeOuvrirJeu1 = new JLabel("Premiers pas");
		lblBadgeOuvrirJeu1.setBounds(6, 15, 220, 47);
		panelBordureOuvrirJeu1.add(lblBadgeOuvrirJeu1);

		lblBadgeOuvrirJeu1.setBackground(Color.WHITE);
		lblBadgeOuvrirJeu1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeOuvrirJeu1.setHorizontalAlignment(SwingConstants.CENTER);

		lblInfoOuvrirJeu1 = new JLabel("Avoir ouvert l'application");
		lblInfoOuvrirJeu1.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoOuvrirJeu1.setVisible(false);
		lblInfoOuvrirJeu1.setBounds(153, 223, 230, 22);
		add(lblInfoOuvrirJeu1);

		panelBordureSurvolerTitreBadge2 = new JPanel();
		panelBordureSurvolerTitreBadge2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeSurvolerTitreBadge2) {
					panelBordureSurvolerTitreBadge2.setBackground(Color.CYAN);
					lblInfoSurvolerTitreBadge2.setVisible(true);
				} else {
					panelBordureSurvolerTitreBadge2.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeSurvolerTitreBadge2) {
					lblInfoSurvolerTitreBadge2.setVisible(false);
					panelBordureSurvolerTitreBadge2.setBackground(Color.white);

				} else {
					panelBordureSurvolerTitreBadge2.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureSurvolerTitreBadge2.setBackground(Color.GRAY);
		panelBordureSurvolerTitreBadge2.setLayout(null);
		panelBordureSurvolerTitreBadge2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureSurvolerTitreBadge2.setBounds(530, 144, 232, 68);
		add(panelBordureSurvolerTitreBadge2);

		lblBadgeSurvolerTitreBadge2 = new JLabel("Expert du survol");
		lblBadgeSurvolerTitreBadge2.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeSurvolerTitreBadge2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeSurvolerTitreBadge2.setBackground(Color.WHITE);
		lblBadgeSurvolerTitreBadge2.setBounds(6, 15, 220, 47);
		panelBordureSurvolerTitreBadge2.add(lblBadgeSurvolerTitreBadge2);

		lblInfoSurvolerTitreBadge2 = new JLabel("Avoir survolé le titre");
		lblInfoSurvolerTitreBadge2.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoSurvolerTitreBadge2.setVisible(false);
		lblInfoSurvolerTitreBadge2.setBounds(530, 226, 232, 22);
		add(lblInfoSurvolerTitreBadge2);

		panelBordureOuvrirClassement3 = new JPanel();
		panelBordureOuvrirClassement3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeOuvrirClassement3) {
					panelBordureOuvrirClassement3.setBackground(Color.CYAN);
					lblOuvrirClassement3.setVisible(true);
				} else {
					panelBordureOuvrirClassement3.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeOuvrirClassement3) {
					lblOuvrirClassement3.setVisible(false);
					panelBordureOuvrirClassement3.setBackground(Color.white);

				} else {
					panelBordureOuvrirClassement3.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureOuvrirClassement3.setLayout(null);
		panelBordureOuvrirClassement3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureOuvrirClassement3.setBackground(Color.GRAY);
		panelBordureOuvrirClassement3.setBounds(913, 144, 232, 68);
		add(panelBordureOuvrirClassement3);

		lblBadgeOuvrirClassement3 = new JLabel("Futur speedrunner");
		lblBadgeOuvrirClassement3.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeOuvrirClassement3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeOuvrirClassement3.setBackground(Color.WHITE);
		lblBadgeOuvrirClassement3.setBounds(6, 15, 220, 47);
		panelBordureOuvrirClassement3.add(lblBadgeOuvrirClassement3);

		lblOuvrirClassement3 = new JLabel("Avoir ouvert les classments");
		lblOuvrirClassement3.setHorizontalAlignment(SwingConstants.CENTER);
		lblOuvrirClassement3.setBounds(913, 226, 232, 23);
		lblOuvrirClassement3.setVisible(false);
		add(lblOuvrirClassement3);

		panelBordureOuvrirApropos4 = new JPanel();
		panelBordureOuvrirApropos4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeOuvrirApropos4) {
					panelBordureOuvrirApropos4.setBackground(Color.CYAN);
					lblOuvrirApropos4.setVisible(true);
				} else {
					panelBordureOuvrirApropos4.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeOuvrirApropos4) {
					lblOuvrirApropos4.setVisible(false);
					panelBordureOuvrirApropos4.setBackground(Color.white);

				} else {
					panelBordureOuvrirApropos4.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureOuvrirApropos4.setLayout(null);
		panelBordureOuvrirApropos4.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureOuvrirApropos4.setBackground(Color.GRAY);
		panelBordureOuvrirApropos4.setBounds(151, 297, 232, 68);
		add(panelBordureOuvrirApropos4);

		lblBadgeOuvrirApropos4 = new JLabel("Merci :)");
		lblBadgeOuvrirApropos4.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeOuvrirApropos4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeOuvrirApropos4.setBackground(Color.WHITE);
		lblBadgeOuvrirApropos4.setBounds(6, 15, 220, 47);
		panelBordureOuvrirApropos4.add(lblBadgeOuvrirApropos4);

		lblOuvrirApropos4 = new JLabel("Avoir lu les crédits");
		lblOuvrirApropos4.setHorizontalAlignment(SwingConstants.CENTER);
		lblOuvrirApropos4.setBounds(151, 376, 232, 21);
		lblOuvrirApropos4.setVisible(false);
		add(lblOuvrirApropos4);

		panelBordureGciruit5 = new JPanel();
		panelBordureGciruit5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeGciruit5) {
					panelBordureGciruit5.setBackground(Color.CYAN);
					lblGciruit5.setVisible(true);
				} else {
					panelBordureGciruit5.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeGciruit5) {
					lblGciruit5.setVisible(false);
					panelBordureGciruit5.setBackground(Color.white);

				} else {
					panelBordureGciruit5.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureGciruit5.setLayout(null);
		panelBordureGciruit5.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureGciruit5.setBackground(Color.GRAY);
		panelBordureGciruit5.setBounds(530, 297, 232, 68);
		add(panelBordureGciruit5);

		lblBadgeGciruit5 = new JLabel("Super circuit!");
		lblBadgeGciruit5.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeGciruit5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeGciruit5.setBackground(Color.WHITE);
		lblBadgeGciruit5.setBounds(6, 15, 220, 47);
		panelBordureGciruit5.add(lblBadgeGciruit5);

		lblGciruit5 = new JLabel("Avoir gagné le jeu de circuit");
		lblGciruit5.setHorizontalAlignment(SwingConstants.CENTER);
		lblGciruit5.setBounds(530, 376, 232, 22);
		lblGciruit5.setVisible(false);
		add(lblGciruit5);

		panelBordureGcurling6 = new JPanel();
		panelBordureGcurling6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeGcurling6) {
					panelBordureGcurling6.setBackground(Color.CYAN);
					lblGcurling6.setVisible(true);
				} else {
					panelBordureGcurling6.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeGcurling6) {
					lblGcurling6.setVisible(false);
					panelBordureGcurling6.setBackground(Color.white);

				} else {
					panelBordureGcurling6.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureGcurling6.setLayout(null);
		panelBordureGcurling6.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureGcurling6.setBackground(Color.GRAY);
		panelBordureGcurling6.setBounds(913, 297, 232, 68);
		add(panelBordureGcurling6);

		lblBadgeGcurling6 = new JLabel("Expert en bowling?");
		lblBadgeGcurling6.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeGcurling6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeGcurling6.setBackground(Color.WHITE);
		lblBadgeGcurling6.setBounds(6, 15, 220, 47);
		panelBordureGcurling6.add(lblBadgeGcurling6);

		lblGcurling6 = new JLabel("Avoir gagné le jeu de curling");
		lblGcurling6.setHorizontalAlignment(SwingConstants.CENTER);
		lblGcurling6.setBounds(913, 376, 232, 21);
		lblGcurling6.setVisible(false);
		add(lblGcurling6);

		panelBordureGpeche7 = new JPanel();
		panelBordureGpeche7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeGpeche7) {
					panelBordureGpeche7.setBackground(Color.CYAN);
					lblGpeche7.setVisible(true);
				} else {
					panelBordureGpeche7.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeGpeche7) {
					lblGpeche7.setVisible(false);
					panelBordureGpeche7.setBackground(Color.white);

				} else {
					panelBordureGpeche7.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureGpeche7.setLayout(null);
		panelBordureGpeche7.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureGpeche7.setBackground(Color.GRAY);
		panelBordureGpeche7.setBounds(151, 460, 232, 68);
		add(panelBordureGpeche7);

		lblBadgeGpeche7 = new JLabel("Ça colle");
		lblBadgeGpeche7.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeGpeche7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeGpeche7.setBackground(Color.WHITE);
		lblBadgeGpeche7.setBounds(6, 15, 220, 47);
		panelBordureGpeche7.add(lblBadgeGpeche7);

		lblGpeche7 = new JLabel("Avoir gagné le jeu de pêche");
		lblGpeche7.setHorizontalAlignment(SwingConstants.CENTER);
		lblGpeche7.setBounds(151, 539, 232, 22);
		lblGpeche7.setVisible(false);
		add(lblGpeche7);

		panelBordureGtank8 = new JPanel();
		panelBordureGtank8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (badgeGtank8) {
					panelBordureGtank8.setBackground(Color.CYAN);
					lblGtank8.setVisible(true);
				} else {
					panelBordureGtank8.setBackground(Color.BLACK);

				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (badgeGtank8) {
					lblGtank8.setVisible(false);
					panelBordureGtank8.setBackground(Color.white);

				} else {
					panelBordureGtank8.setBackground(Color.GRAY);

				}
			}
		});
		panelBordureGtank8.setLayout(null);
		panelBordureGtank8.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBordureGtank8.setBackground(Color.GRAY);
		panelBordureGtank8.setBounds(530, 460, 232, 68);
		add(panelBordureGtank8);

		lblBadgeGtank8 = new JLabel("Sniper");
		lblBadgeGtank8.setHorizontalAlignment(SwingConstants.CENTER);
		lblBadgeGtank8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBadgeGtank8.setBackground(Color.WHITE);
		lblBadgeGtank8.setBounds(6, 15, 220, 47);
		panelBordureGtank8.add(lblBadgeGtank8);

		lblGtank8 = new JLabel("Avoir gagné le jeu de char d'assault");
		lblGtank8.setHorizontalAlignment(SwingConstants.CENTER);
		lblGtank8.setBounds(530, 539, 232, 22);
		lblGtank8.setVisible(false);
		add(lblGtank8);
	}

	/**
	 * Méthode qui met à jour les badges
	 */
	public void majBadges() {
		donnesBadges = GestionnaireDesDonnes.getInstance().getDonnesBadges();

		badgeSurvolerTitreBadge2 = donnesBadges.isBadgeSurvolerTitreBadge2();
		badgeOuvrirClassement3 = donnesBadges.isBadgeOuvrirClassement3();
		badgeOuvrirApropos4 = donnesBadges.isBadgeOuvrirApropos4();
		badgeGciruit5 = donnesBadges.isBadgeGciruit5();
		badgeGcurling6 = donnesBadges.isBadgeGcurling6();
		badgeGpeche7 = donnesBadges.isBadgeGpeche7();
		badgeGtank8 = donnesBadges.isBadgeGtank8();

		if (badgeSurvolerTitreBadge2) {
			panelBordureSurvolerTitreBadge2.setBackground(Color.WHITE);
		}
		if (badgeOuvrirClassement3) {
			panelBordureOuvrirClassement3.setBackground(Color.WHITE);
		}
		if (badgeOuvrirApropos4) {
			panelBordureOuvrirApropos4.setBackground(Color.WHITE);
		}
		if (badgeGciruit5) {
			panelBordureGciruit5.setBackground(Color.WHITE);
		}
		if (badgeGcurling6) {
			panelBordureGcurling6.setBackground(Color.WHITE);
		}
		if (badgeGpeche7) {
		panelBordureGpeche7.setBackground(Color.WHITE);
		}
		if (badgeGtank8) {
		panelBordureGtank8.setBackground(Color.WHITE);
		}

	}
}
