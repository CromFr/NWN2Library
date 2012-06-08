import javax.swing.*;


/// @brief Application contenant la méthode main
public final class App
{
	//Constructeur privé pour empêcher l'instantiation manuelle
	private App(){}

	
	///Main
	public static void main(String args[])
	{
	//	System.out.println(System.getProperty("java.version"));
		
		SwingUtilities.invokeLater(new 
			Runnable()
			{
				public void run()
				{
					Window fenetre = new Window();
					fenetre.setVisible(true);
				}
			});

	}

}
