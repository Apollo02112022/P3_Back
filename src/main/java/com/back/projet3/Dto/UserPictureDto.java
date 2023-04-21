package com.back.projet3.Dto;

import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

public class UserPictureDto {

    @Lob
    private MultipartFile picture;


    public MultipartFile getPicture() {
        return picture;
    }


    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
    
}
