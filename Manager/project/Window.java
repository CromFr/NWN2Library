import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


///Ne sert pas à grand chose si ce n'est à initialiser et contenir les pages & tabs
public class Window extends JFrame implements ChangeListener
{

	// Gnééé?
	private static final long serialVersionUID = 1L;
	// enlève le warning

	private int nWindowWidth = 640;
	private int nWindowHeight = 480;

	private JTabbedPane ptTabs;
	private PageConfig pConfig;
	private PageBook pBookPage;

	///place les elements et initialise ce qu'il faut !
	public Window()
	{
		//Appel du constructeur de jFrame
		super();

		setTitle("Administration des livres");
		setSize(nWindowWidth, nWindowHeight);
		setLocationRelativeTo(null);// Centré
		setResizable(false); // non redimentionnable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Action du bouton close


		JPanel panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);
		

		//Mise en place des onglets
		pConfig = new PageConfig();
		pBookPage = new PageBook();
		
		ptTabs = new JTabbedPane();
		ptTabs.setBounds(0, 0, nWindowWidth, nWindowHeight - 29);
		ptTabs.add("Configuration", pConfig);
		ptTabs.add("Livres", pBookPage);
		ptTabs.addChangeListener(this);
		panel.add(ptTabs);
		//

		// Actualisation de la page de configuration
		pConfig.update();
	}

	//Implémentation du ChangeListener
	public void stateChanged(ChangeEvent e)
	{
		if (((JTabbedPane) (e.getSource())).getSelectedComponent() != pConfig)
		{
			((Page)(ptTabs.getSelectedComponent())).onOpened();
		}
		else
		{
			((PageConfig) (ptTabs.getSelectedComponent())).update();
		}

	}
}
