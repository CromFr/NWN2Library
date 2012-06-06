#include "nwnx_sql"



const string GUI_SCREEN = "SCREEN_LIBRARY";
const string GUI_XML = "library.xml";

string MakeBookDescription(string sText, string sAuthor, string sSource);


void main(string sAction, string arg0)
{
	object oPC = OBJECT_SELF;
	
	SQLExecDirect("SET NAMES utf8");

	if(sAction == "Init")
	{
		ClearListBox(oPC, GUI_SCREEN, "ListBoxCats");
		
		AddListBoxRow(oPC, GUI_SCREEN, "ListBoxCats", "", "TextRowCat=Tout", "", "", "");
		
		
		SQLExecDirect("SELECT `cat` FROM `library` GROUP BY `cat` ORDER BY `cat` ASC");
		while(SQLFetch())
		{
			AddListBoxRow(oPC, GUI_SCREEN, "ListBoxCats", "", "TextRowCat="+SQLGetData(1), "", "", "");
		}
		
		//Refresh books list
		main("Refresh", "");
	
	}
	else if(sAction == "Refresh")
	{
		string sCat = arg0;
		
		ClearListBox(oPC, GUI_SCREEN, "ListBoxBooks");
		
		if(sCat == "" || sCat == "Tout")
		{
			SQLExecDirect("SELECT `code`,`title`,`icon` FROM `library` ORDER BY `title` ASC LIMIT 100");
		}
		else
		{
			SQLExecDirect("SELECT `code`,`title`,`icon` FROM `library` WHERE `cat`='"+sCat+"' ORDER BY `title` ASC");
		}
		
		while(SQLFetch())
		{
			string sIcon = Get2DAString("nwn2_icons", "ICON", StringToInt(SQLGetData(3)))+".tga";
			AddListBoxRow(oPC, GUI_SCREEN, "ListBoxBooks", "", "TextRowBookCode="+SQLGetData(1)+";TextRowBookTitle="+SQLGetData(2), "IconRowBook="+sIcon, "", "");
		}
	}
	else if(sAction == "Buy")
	{
		if(arg0 == "")
		{
			SendMessageToPC(oPC, "Vous devez selectionner un livre");
		}
		else
		{
	
			if(GetGold(oPC)>=10)
			{
				TakeGoldFromCreature(10, oPC, TRUE);
				
				SQLExecDirect("SELECT `icon`,`title`,`text`,`author`,`source` FROM `library` WHERE `code`='"+arg0+"'");
				if(SQLFetch())
				{
					SendMessageToPC(oPC, "Vous venez d'acheter '"+SQLGetData(2)+"'");
					object oBook = CreateItemOnObject("nw_it_book001", oPC, 1, "library_book_"+arg0, FALSE);
					
					SetFirstName(oBook, SQLGetData(2));
					SetLastName(oBook, SQLGetData(2));
					SetItemIcon(oBook, StringToInt(SQLGetData(1)));
					SetDescription(oBook, MakeBookDescription(SQLGetData(3), SQLGetData(4), SQLGetData(5)));
				}
				else
					FloatingTextStringOnCreature("Erreur SQL", oPC);
			}
			else
				SendMessageToPC(oPC, "Vous n'avez pas assez d'argent !");
		}
	}
	else if(sAction == "Read")
	{
		if(arg0 == "")
		{
			SendMessageToPC(oPC, "Vous devez selectionner un livre");
		}
		else
		{
			SQLExecDirect("SELECT `title`,`text`,`author`,`source` FROM `library` WHERE `code`='"+arg0+"' ORDER BY `title` ASC");
			if(SQLFetch())
			{
				DisplayMessageBox(oPC, 0, MakeBookDescription(SQLGetData(2), SQLGetData(3), SQLGetData(4)), "", "", FALSE, "SCREEN_MESSAGEBOX_DEFAULT", 0, "Fermer");
			}
			else
				FloatingTextStringOnCreature("Erreur SQL", oPC);
		}
	}
	
	SQLExecDirect("SET NAMES latin1");


}




string MakeBookDescription(string sText, string sAuthor, string sSource)
{
	string sReturn = "";
	
	if(sAuthor!="")
	{
		sReturn+="par "+sAuthor+"\n\n";
	}
	sReturn+=sText;
	sReturn+="\n";
	
	if(sAuthor!="")
		sReturn+="\nAuteur: "+sAuthor;
	
	if(sSource!="")
		sReturn+="\n(Source: "+sSource+")";
	
	return sReturn;
}