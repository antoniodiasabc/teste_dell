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
	private String message;

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

	public Integer getChancesUsed() {
		return chancesUsed;
	}

	public String getDraft() {
		return draft;
	}

	public String getDraftBlocked() {
		return draftBlocked;
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

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

	public String getTyped() {
		return typed;
	}

	public void setChancesUsed(Integer chancesUsed) {
		this.chancesUsed = chancesUsed;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	public void setDraftBlocked(String draftBlocked) {
		this.draftBlocked = draftBlocked;
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

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTyped(String typed) {
		this.typed = typed;
	}
}
