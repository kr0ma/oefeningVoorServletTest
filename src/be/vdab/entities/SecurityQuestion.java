package be.vdab.entities;

public class SecurityQuestion {
	private int id;
	private String vraag;
	private String antwoord;
	
	public SecurityQuestion(String vraag, String antwoord){
		this.vraag = vraag;
		this.antwoord = antwoord;		
	}
	
	public SecurityQuestion(int id, String vraag, String antwoord){
		this(vraag,antwoord);
		this.id = id;
	}	
	
	public String getVraag() {
		return vraag;
	}

	public Boolean isAntwoordJuist(String ingave){
		return antwoord.equalsIgnoreCase(ingave);
	}
	
	public int getId(){
		return id;
	}

}
