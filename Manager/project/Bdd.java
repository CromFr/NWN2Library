import java.sql.*;
import java.util.ArrayList;


///Classe principalement statique servant d'intermédiaire avec la BDD
public final class Bdd
{
	
	//Constructeur privé pour empêcher l'instantiation manuelle
	private Bdd(){}
	

	//Auto instantiation
	private static Bdd selfInstance = null;
	//
	
	
	private static boolean bConnected = false;
	
	
	private static Connection connConn = null;
	private static Statement statStat = null;
	private static ResultSet rsResult = null;
	private static PreparedStatement psPrepStat = null;
	
	
	//Paramètres de la BDD
	private static String sIP;
	private static String sSchema;
	private static String sUser;
	private static String sPassword;
	//

	///Accesseur pour l'IP de la dernière tentative de connexion à la bdd
	public static String getIP()
	{
		return sIP;
	}

	///Accesseur pour le schéma de la dernière tentative de connexion à la bdd
	public static String getSchema()
	{
		return sSchema;
	}

	///Accesseur pour le nom de l'utilisateur de la dernière tentative de connexion à la bdd
	public static String getUser()
	{
		return sUser;
	}

	///Accesseur pour le mdp utilisé à la dernière tentative de connexion à la bdd
	public static String getPassword()
	{
		return sPassword;
	}

	///Connecte la BDD au serveur MySQL donné
	/// @warning Stocke les paramètres utilisé dans la classe, même si la connexion à échouée
	/// @return 1 si connecté, 0 si la connexion à échouée
	/// @param sIPb l'IP de la bdd
	/// @param sSchemab le schéma utilisé par la bdd
	/// @param sUserb l'utilisateur ayant accès la table burgerquizz
	/// @param sPasswordb le mot de passe à utiliser
	/// @throws propage l'exception générée par la tentative de connexion MySQL
	public static void connect(String sIPb, String sSchemab, String sUserb, String sPasswordb) throws Exception
	{
		sIP = sIPb;
		sSchema = sSchemab;
		sUser = sUserb;
		sPassword = sPasswordb;

		try
		{
			selfInstance = new Bdd();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connConn = DriverManager.getConnection("jdbc:mysql://" + sIP + "/" + sSchema, sUser, sPassword);
			statStat = connConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			bConnected = true;
		} catch (Exception e)
		{
			bConnected = false;
			throw e;
		}
	}

	///Ferme la connexion MySQL
	public static void close()
	{
		try
		{
			statStat.close();
			connConn.close();
			bConnected = false;
		} catch (Exception e)
		{
			// System.out.println(e);
		}

	}

	///Renvoie 1 si la l'application est connectée à la BDD
	public static boolean getIsConnected()
	{
		return bConnected;
	}
	// ====================================================================

	
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// SELECT FUNCTIONS
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	///Génère une liste contenant les catégories tirées de la BDD
	/// @return ladite liste
	public static ArrayList<Book> generateBookList()
	{
		if (selfInstance == null || !bConnected)
		{
			return null;
		}

		ArrayList<Book> content = new ArrayList<Book>();
		try
		{
			rsResult = statStat.executeQuery("SELECT `code`,`libtag`,`date`,`icon`,`price`,`cat`,`author`,`source`,`title`,`text` FROM `library`");

			while (rsResult.next())
			{
				content.add(new Book(rsResult.getString(1),
									 rsResult.getString(2),
									 rsResult.getString(3),
									 rsResult.getString(4),
									 rsResult.getString(5),
									 rsResult.getString(6),
									 rsResult.getString(7),
									 rsResult.getString(8),
									 rsResult.getString(9),
									 rsResult.getString(10)));
			}
		} catch (Exception e)
		{
			// System.out.println(e);
		}

		return content;
	}
	// =======================
	
	
	
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// REMOVE FUNCTIONS
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///Supprime la catégorie identifiée par son ID
	/// @warning Supprime aussi tous les group et les question associés via OnDeleteCascade (via MySQL)
	/// @param sID valeur de la colonne nom de la table categorie de la catégorie à supprimer
	public static void removeBook(String sID)
	{
		if (selfInstance == null)
		{
			return;
		}

		try
		{
			statStat.execute("DELETE FROM `library` WHERE `code`='" + sID + "'");
		} catch (Exception e)
		{
			// System.out.println(e);
		}
	}
	// =======================

	
	
	
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// UPDATE FUNCTIONS
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	///Modifie la catégorie identifiée par sID
	/// @param sID valeur de la colonne nom de la table categorie de la catégorie à modifier
	/// @param sName nouveau nom à attribuer
	public static void updateBook(String sOriCode, String sCode, String sTagLibs, String sDate, String sIcon, String sPrice, String sCategory, String sAuthor, String sSource, String sTitle, String sTxt)
	{
		if (selfInstance == null)
		{
			return;
		}

		try
		{
			statStat.execute("UPDATE `library` SET "
					+" `code`='" + sCode + "'"
					+", `libtag`='" + sTagLibs + "'"
					+", `date`='" + sDate + "'"
					+", `icon`='" + sIcon + "'"
					+", `price`='" + sPrice + "'"
					+", `cat`='" + sCategory + "'"
					+", `author`='" + sAuthor + "'"
					+", `source`='" + sSource + "'"
					+", `title`='" + sTitle + "'"
					+", `text`='" + sTxt + "'"
				+" WHERE `code`='" + sOriCode + "'");
		} catch (Exception e)
		{
			// System.out.println(e);
		}
	}
	// =======================

	

	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// INSERT FUNCTIONS
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	///Ajoute une catégorie par défaut
	/// @note le nom de la catégorie est 'Nouvelle cat.'
	public static void insertDefaultBookRow()
	{
		if (selfInstance == null)
		{
			return;
		}

		try
		{
			statStat.execute("INSERT INTO `library` (`code`,`title`,`text`)VALUES('_NEW BOOK_CODE_','_NEW BOOK_TITLE_','_NEW BOOK_TEXT_')");
		} catch (Exception e)
		{
			// System.out.println(e);
		}

	}
	// =======================
	


}
