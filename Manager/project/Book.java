
public class Book {
	
	public String m_sCode;
	public String m_sTagLibs;

	public String m_sDate;

	public String m_sIcon;

	public String m_sPrice;

	public String m_sCategory;

	public String m_sAuthor;

	public String m_sSource;

	public String m_sTitle;
	public String m_sTxt;
	
	public Book(String sCode, String sTagLibs, String sDate, String sIcon, String sPrice, String sCategory, String sAuthor, String sSource, String sTitle, String sTxt)
	{
		m_sCode = sCode;
		m_sTagLibs = sTagLibs;
		m_sDate = sDate;
		m_sIcon = sIcon;
		m_sPrice = sPrice;
		m_sCategory = sCategory;
		m_sAuthor = sAuthor;
		m_sSource = sSource;
		m_sTitle = sTitle;
		m_sTxt = sTxt;
	}
	
	
	public String toString()
	{
		return "("+m_sCode+") "+m_sTitle;
	}

}
