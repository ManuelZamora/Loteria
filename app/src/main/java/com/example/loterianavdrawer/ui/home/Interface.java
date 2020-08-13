package com.example.loterianavdrawer.ui.home;

import android.widget.ImageView;

public class Interface {

    ImageView imageView;
    int card_number;
    boolean flag;

    public Interface(){
        this.flag = false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getCard_number() {
        return card_number;
    }

    public boolean isChecked(){
        return this.flag;
    }

    public void checkCard(){
        this.flag = true;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }
}
