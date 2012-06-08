import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/// @brief Page d'administration des catégories
class PageBook extends Page{
	
	//Gnééé?
	private static final long serialVersionUID = 1L;
	//enlève le warning
	
	 
	private JTextField txtfEntryCode;
	private JLabel lblEntryCode;
	
	private JTextField txtfEntryTagLibs;
	private JLabel lblEntryTagLibs;

	private JTextField txtfEntryDate;
	private JLabel lblEntryDate;

	private JTextField txtfEntryIcon;
	private JLabel lblEntryIcon;

	private JTextField txtfEntryPrice;
	private JLabel lblEntryPrice;

	private JTextField txtfEntryCategory;
	private JLabel lblEntryCategory;

	private JTextField txtfEntryAuthor;
	private JLabel lblEntryAuthor;

	private JTextField txtfEntrySource;
	private JLabel lblEntrySource;

	private JTextField txtfEntryTitle;
	private JLabel lblEntryTitle;
	private JEditorPane editEntryTxt;
	
	


	
	
	///Place les elements sur la page
	public PageBook()
	{
		super();
		setListTop(30);

		
		
		lblEntryCode = new JLabel("Code");
		lblEntryCode.setBounds(330, 30, 40, 20);
		this.add(lblEntryCode);
		txtfEntryCode = new JTextField();
		txtfEntryCode.setToolTipText("Code du livre (doit etre UNIQUE !)");
		txtfEntryCode.setBounds(370,30,260,20);
		this.add(txtfEntryCode);
		//==================================================
		lblEntryTagLibs = new JLabel("Libs");
		lblEntryTagLibs.setBounds(330, 50, 40, 20);
		this.add(lblEntryTagLibs);
		txtfEntryTagLibs = new JTextField();
		txtfEntryTagLibs.setToolTipText("Tag des biliothèques possèdant ce livre.  Plusieurs bibliothèques sont séparées par \",\".  Pour désigner toutes, entrer \"*\"");
		txtfEntryTagLibs.setBounds(370,50,260,20);
		this.add(txtfEntryTagLibs);
		//==================================================
		lblEntryDate = new JLabel("Date");
		lblEntryDate.setBounds(330, 70, 40, 20);
		this.add(lblEntryDate);
		txtfEntryDate = new JTextField();
		txtfEntryDate.setToolTipText("Date d'enregistrement du livre (format AAAA-MM-DD hh:mm:ss");
		txtfEntryDate.setBounds(370,70,260,20);
		this.add(txtfEntryDate);
		//==================================================
		lblEntryIcon = new JLabel("Icon");
		lblEntryIcon.setBounds(330, 90, 40, 20);
		this.add(lblEntryIcon);
		txtfEntryIcon = new JTextField();
		txtfEntryIcon.setToolTipText("Numéro de l'icône du livre (v. README)");
		txtfEntryIcon.setBounds(370,90,260,20);
		this.add(txtfEntryIcon);
		//==================================================
		lblEntryPrice = new JLabel("Price");
		lblEntryPrice.setBounds(330, 110, 40, 20);
		this.add(lblEntryPrice);
		txtfEntryPrice = new JTextField();
		txtfEntryPrice.setToolTipText("Prix du livre en po");
		txtfEntryPrice.setBounds(370,110,260,20);
		this.add(txtfEntryPrice);
		//==================================================
		lblEntryCategory = new JLabel("Cat");
		lblEntryCategory.setBounds(330, 130, 40, 20);
		this.add(lblEntryCategory);
		txtfEntryCategory = new JTextField();
		txtfEntryCategory.setToolTipText("Catégorie du livre (ex: Bestiaire, Module, ...)");
		txtfEntryCategory.setBounds(370,130,260,20);
		this.add(txtfEntryCategory);
		//==================================================
		lblEntryAuthor = new JLabel("Author");
		lblEntryAuthor.setBounds(330, 150, 40, 20);
		this.add(lblEntryAuthor);
		txtfEntryAuthor = new JTextField();
		txtfEntryAuthor.setToolTipText("Auteur RP du livre");
		txtfEntryAuthor.setBounds(370,150,260,20);
		this.add(txtfEntryAuthor);
		//==================================================
		lblEntrySource = new JLabel("Src");
		lblEntrySource.setBounds(330, 170, 40, 20);
		this.add(lblEntrySource);
		txtfEntrySource = new JTextField();
		txtfEntrySource.setToolTipText("Provenance HRP du récit (ex : Wikipedia, Gemmaline, ...)");
		txtfEntrySource.setBounds(370,170,260,20);
		this.add(txtfEntrySource);
		//==================================================
		lblEntryTitle = new JLabel("Title");
		lblEntryTitle.setBounds(330, 190, 40, 20);
		this.add(lblEntryTitle);
		txtfEntryTitle = new JTextField();
		txtfEntryTitle.setToolTipText("Titre du livre");
		txtfEntryTitle.setBounds(370,190,260,20);
		this.add(txtfEntryTitle);
		//==================================================
		
		
		editEntryTxt = new JEditorPane("text/plain", "");
		editEntryTxt.setToolTipText("Contenu du livre");
		JScrollPane scrCont = new JScrollPane(editEntryTxt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrCont.setBounds(330,210,300,150);
		this.add(scrCont);
	}
	
	
	
	public void onOpened()
	{
		if(Bdd.getIsConnected())
		{
			updateList();
		}
	}
	public void onListSelection()
	{
		Book oRow = getSelectedListRow();
		if(oRow!=null)
		{
			txtfEntryCode.setText(oRow.m_sCode);
			txtfEntryTagLibs.setText(oRow.m_sTagLibs);
			txtfEntryDate.setText(oRow.m_sDate);
			txtfEntryIcon.setText(oRow.m_sIcon);
			txtfEntryPrice.setText(oRow.m_sPrice);
			txtfEntryCategory.setText(oRow.m_sCategory);
			txtfEntryAuthor.setText(oRow.m_sAuthor);
			txtfEntrySource.setText(oRow.m_sSource);
			txtfEntryTitle.setText(oRow.m_sTitle);
			editEntryTxt.setText(oRow.m_sTxt);
		}
	}
	
	
	
	
	//==================
	public void modify()
	{
		Bdd.updateBook(getSelectedListRow().m_sCode, 
				txtfEntryCode.getText(), 
				txtfEntryTagLibs.getText(), 
				txtfEntryDate.getText(), 
				txtfEntryIcon.getText(), 
				txtfEntryPrice.getText(), 
				txtfEntryCategory.getText(), 
				txtfEntryAuthor.getText(), 
				txtfEntrySource.getText(), 
				txtfEntryTitle.getText(), 
				editEntryTxt.getText());
		updateList();
	}
	//==================
	public void remove()
	{
		Bdd.removeBook(getSelectedListRow().m_sCode);
		updateList();
	}
	//==================
	public void add()
	{
		Bdd.insertDefaultBookRow();
		updateList();
	}
	//==================
	public void updateList()
	{
		setListContent(Bdd.generateBookList());
	}

}
