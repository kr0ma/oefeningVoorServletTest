package be.vdab.entities;

public class GastenboekEntry {
	private long id;
	private String naam;
	private String bericht;
	
	
	public GastenboekEntry(String naam, String bericht) {
		this.naam = naam;
		this.bericht = bericht;
	}	
	
	public GastenboekEntry(long id, String naam, String bericht) {
		this(naam, bericht);
		this.id = id;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getBericht() {
		return bericht;
	}
	public void setBericht(String bericht) {
		this.bericht = bericht;
	}
	
	public static boolean isNaamValid(String naam) { 
		return naam != null && !naam.isEmpty();
	}
	
	public static boolean isBerichtValid(String bericht){
		return bericht != null && !bericht.isEmpty();
	}
}
