package com.gbraille.ortomonstro;

public class QuestionClass {
	private long id;
	public String answer;
	public String question;
	public String missingCharPos;
	public String dificuldade;
	public String jogo;
	public String lingua;
	
	
	public long getId() {
	    return id;
	}
	
	public void setId(long id) {
	    this.id = id;
	}
	
	public String getAnswer() {
	    return answer;
	}
	
	public void setAnswer(String answer) {
	    this.answer = answer;
	}
	
	public String getQuestion() {
	    return question;
	}
	
	public void setQuestion(String dica) {
	    this.question = dica;
	}	
	
	public String getMissingCharPos () {
		return missingCharPos;
	}
	
	public void setMissingCharPos(String missingCharPos) {
		this.missingCharPos = missingCharPos;
	}
	
	public String getDificuldade() {
		return dificuldade;
	}
	
	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}
	
	public String getJogo () {
		return jogo;
	}
	
	public void setJogo(String jogo) {
		this.jogo = jogo;
	}
	
	public String getLingua(){
		return lingua;
	}
	
	public void setLing (String lingua) {
		this.lingua = lingua;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return answer;
	}
	
	
}