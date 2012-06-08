import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

///Page de configuration de la connexion MySQL
public class PageConfig extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblBDDState;

	//====================== params actuels de la bdd
	private JLabel lblMySQLConfig;
	private JLabel lblIP;
	private JTextField txtfIP;
	private JLabel lblBDD;
	private JTextField txtfBDD;
	private JLabel lblUser;
	private JTextField txtfUser;
	private JLabel lblPass;
	private JTextField txtfPass;
	//

	private JButton btnCoDeco;
	
	//====================== Boite de raport d'erreur
	private JLabel lblErrBox;
	private JEditorPane editErrBox;
	//

	PageConfig()
	{
		this.setLayout(null);


		lblBDDState = new JLabel("z", JLabel.CENTER);
		lblBDDState.setToolTipText("Etat de la connexion à la BDD");
		lblBDDState.setBounds(10, 20, 620, 40);
		this.add(lblBDDState);

		//====================== params actuels de la bdd
		lblMySQLConfig = new JLabel("Configuration actuelle du server MySQL");
		lblMySQLConfig.setBounds(10, 70, 300, 20);
		this.add(lblMySQLConfig);

		lblIP = new JLabel("IP");
		lblIP.setBounds(20, 100, 40, 20);
		this.add(lblIP);

		txtfIP = new JTextField("127.0.0.1");
		txtfIP.setToolTipText("Adresse IP de la machine possèdant le serveur MySQL (exemple : 127.0.0.1)");
		txtfIP.setBounds(60, 100, 250, 20);
		this.add(txtfIP);

		lblBDD = new JLabel("Sch");
		lblBDD.setBounds(20, 130, 40, 20);
		this.add(lblBDD);

		txtfBDD = new JTextField("nwnx");
		txtfBDD.setToolTipText("Schéma du serveur MySQL à utiliser (exemple : nwnx)");
		txtfBDD.setBounds(60, 130, 250, 20);
		this.add(txtfBDD);

		lblUser = new JLabel("User");
		lblUser.setBounds(20, 160, 40, 20);
		this.add(lblUser);

		txtfUser = new JTextField("root");
		txtfUser.setToolTipText("Nom de l'utilisateur à utiliser pour se connecter");
		txtfUser.setBounds(60, 160, 250, 20);
		this.add(txtfUser);

		lblPass = new JLabel("Pass");
		lblPass.setBounds(20, 190, 40, 20);
		this.add(lblPass);

		txtfPass = new JTextField();
		txtfPass.setToolTipText("Mot de passe associé au compte");
		txtfPass.setBounds(60, 190, 250, 20);
		this.add(txtfPass);
		//
		

		
		
		btnCoDeco = new JButton();
		btnCoDeco.setBounds(30, 250, 260, 20);
		btnCoDeco.addActionListener(this);
		this.add(btnCoDeco);

		//====================== Boite de raport d'erreur
		lblErrBox = new JLabel("Erreurs");
		lblErrBox.setBounds(10, 270, 100, 20);
		this.add(lblErrBox);

		editErrBox = new JEditorPane("text/plain", "");
		JScrollPane scrCont = new JScrollPane(editErrBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrCont.setBounds(10, 290, 610, 127);
		this.add(scrCont);
		
		//txtaErrBox = new JTextArea();
		//txtaErrBox.setBounds(10, 290, 610, 127);
		//this.add(txtaErrBox);
	}

	
	///Met à jour tous les champs de la page (ainsi que les lbl de la config)
	public void update()
	{
		// Etat de la bdd :
		if (Bdd.getIsConnected())
		{
			btnCoDeco.setText("Déconnexion");
			lblBDDState.setText("Connexion à la BDD établie !");
		} else
		{
			btnCoDeco.setText("Connexion");
			lblBDDState.setText("Non connecté à la BDD");
		}
	}

	//implémentation du ActionListener
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnCoDeco)
		{
			if (Bdd.getIsConnected())
			{
				Bdd.close();

				update();
			} else
			{
				try
				{
					Bdd.connect(txtfIP.getText(), txtfBDD.getText(), txtfUser.getText(), txtfPass.getText());

					editErrBox.setText("Connexion réussie, vous pouvez administrer la base de données !");
					// super.lblBDDConn.setText("Connexion réussie");

					update();
				} catch (Exception e1)
				{
					editErrBox.setText(e1.toString());
					// super.lblBDDConn.setText("Connexion échouée !");
				}
			}

		}
	}

}
