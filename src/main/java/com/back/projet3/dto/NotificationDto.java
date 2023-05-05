package com.back.projet3.dto;



 //@Data génère automatiquement des méthodes ici getters et setters
public class NotificationDto {


    private String message;
    private String tel;
    private String mail;


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    


}

