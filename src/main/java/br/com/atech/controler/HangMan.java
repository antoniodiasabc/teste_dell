package br.com.atech.controler;

public class HangMan {
	private String letter;
	private String status;
	private String draft;
	private Integer found;
	private Integer maxChances;
	private Integer chancesUsed;
	private String draftBlocked;
	private String typed;

	public HangMan() {
		this.letter = "";
		this.chancesUsed = 0;
		this.draft = "";
		this.draftBlocked = "";
		this.found = 0;
		this.letter = "";
		this.maxChances = 0;
		this.status = "";
		this.typed = "";
	}

	public String getTyped() {
		return typed;
	}

	public void setTyped(String typed) {
		this.typed = typed;
	}

	public String getDraftBlocked() {
		return draftBlocked;
	}

	public void setDraftBlocked(String draftBlocked) {
		this.draftBlocked = draftBlocked;
	}

	public Integer getChancesUsed() {
		return chancesUsed;
	}

	public String getDraft() {
		return draft;
	}

	public Integer getFound() {
		return found;
	}

	public String getLetter() {
		return letter;
	}

	public Integer getMaxChances() {
		return maxChances;
	}

	public String getStatus() {
		return status;
	}

	public void setChancesUsed(Integer chancesUsed) {
		this.chancesUsed = chancesUsed;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	public void setFound(Integer found) {
		this.found = found;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public void setMaxChances(Integer maxChances) {
		this.maxChances = maxChances;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
