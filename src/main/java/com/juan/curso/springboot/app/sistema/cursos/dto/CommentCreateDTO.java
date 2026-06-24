package com.juan.curso.springboot.app.sistema.cursos.dto;

public class CommentCreateDTO {
	private String comment;
	private Long idClasse;
	private Long idStudent;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getIdClass() {
		return idClasse;
	}
	public void setIdClass(Long idClass) {
		this.idClasse = idClass;
	}
	public Long getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	@Override
	public String toString() {
		return "CommentCreateDTO [comment=" + comment + ", idClasse=" + idClasse + ", idStudent=" + idStudent + "]";
	}
	
	
}
