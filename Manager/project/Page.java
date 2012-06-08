import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.util.ArrayList;

///Classe abstraite représentant la partie fixe des pages de catégorie, groupe et question.
public abstract class Page extends JPanel implements ActionListener, ListSelectionListener
{

	// Gnééé?
	private static final long serialVersionUID = 1L;
	// enlève le warning

	private JLabel lblBrowse;
	private JList listBrowse;
	private JButton btnEntryAdd;
	private JButton btnEntryRemove;
	private JButton btnEntryModify;

	// Méthodes abstraites, réécrites dans les classes filles

	///this appelle cette méthode quand on ouvre l'onglet
	public abstract void onOpened();

	///this appelle cette méthode quand on clique sur un élément de la liste
	public abstract void onListSelection();

	///Methode appelée par un clic sur le bouton "Modifier"
	public abstract void modify();

	///Methode appelée par un clic sur le bouton "Supprimer"
	public abstract void remove();

	///Methode appelée par un clic sur le bouton "Ajouter"
	public abstract void add();

	//-----

	///Place les elements sur la page
	public Page()
	{
		//Appel du constructeur de JPanel
		super();

		this.setLayout(null);

		//====================== Paneau de gauche
		// Browse Label
		lblBrowse = new JLabel("Parcourir la base de données", JLabel.CENTER);
		lblBrowse.setBounds(10, 10, 300, 20);
		this.add(lblBrowse);

		// Browse list
		listBrowse = new JList();
		listBrowse.addListSelectionListener(this);
		listBrowse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(listBrowse);

		
		//====================== Paneau de droite
		// Entry Label
		lblBrowse = new JLabel("Données du champ selectionné", JLabel.CENTER);
		lblBrowse.setBounds(330, 10, 300, 20);
		this.add(lblBrowse);

		//Bouton ajouter nouvelle ligne
		btnEntryAdd = new JButton("Ajouter");
		btnEntryAdd.setBounds(30, 397, 260, 20);
		btnEntryAdd.setToolTipText("Ajoute une entrée dans la liste");
		btnEntryAdd.addActionListener(this);
		this.add(btnEntryAdd);

		//Bouton modifier ligne selectionnée
		btnEntryModify = new JButton("Modifier");
		btnEntryModify.setBounds(350, 367, 260, 20);
		btnEntryModify.setToolTipText("Modifie la ligne selectionnée avec les données ci dessus");
		btnEntryModify.addActionListener(this);
		this.add(btnEntryModify);

		//Bouton Supprimer ligne selectionnée
		btnEntryRemove = new JButton("Supprimer");
		btnEntryRemove.setBounds(350, 397, 260, 20);
		btnEntryRemove.setToolTipText("Supprime définitivement la ligne selectionnée !");
		btnEntryRemove.addActionListener(this);
		this.add(btnEntryRemove);
		//
	}

	//Implémentation du ActionListener
	//Distribue les méthodes abstraites à éxécuter en fonction de la source
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnEntryAdd)
		{
			add();
		}
		if (e.getSource() == btnEntryModify)
		{
			modify();
		}
		if (e.getSource() == btnEntryRemove)
		{
			remove();
		}
	}

	//Implémentation du ItemListener
	//Execute OnListSelection (méthode abstraite)
	public void valueChanged(ListSelectionEvent e)
	{
		onListSelection();
	}

	///Permet de changer le contenu de la liste
	/// @param content ArrayList contenant des ListRow représentant une entrée
	protected void setListContent(ArrayList<Book> content)
	{
		listBrowse.setListData(content.toArray());
		listBrowse.setSelectedIndex(0);
	}

	///Récupère la cellule selectionnée dans la liste
	protected Book getSelectedListRow()
	{
		Object oMyRow = listBrowse.getSelectedValue();
		if (oMyRow != null)
		{
			return ((Book) oMyRow);
		}
		return null;
	}

	///Modifie la hauteur de la liste, tout en la gardant alignée en bas (redimentionnement auto)
	/// @param top Pixe
	protected void setListTop(int top)
	{
		listBrowse.setBounds(10, top, 300, 387 - top);
	}

}
