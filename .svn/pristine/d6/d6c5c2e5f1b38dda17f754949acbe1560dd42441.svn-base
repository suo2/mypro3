package com.huawei.solarsafe.view.maintaince.patrol.FloatActionMenu;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ButtonData implements Cloneable{
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private boolean isMainButton = false;//main button is the button you see when buttons are all collapsed
    private boolean iconButton;//true if the button use icon resource,else string resource

    private String[] texts;//String array that you want to show at button center,texts[i] will be shown at the ith row
    private Drawable icon;//icon drawable that will be shown at button center
    private float iconPaddingDp;//the padding of the icon drawable in button
    private int backgroundColor = DEFAULT_BACKGROUND_COLOR;//the background color of the button

    private int textColor;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ButtonData buttonData = (ButtonData)super.clone();
        buttonData.setIsIconButton(this.iconButton);
        buttonData.setBackgroundColor(this.backgroundColor);
        buttonData.setIsMainButton(this.isMainButton);
        buttonData.setIcon(this.icon);
        buttonData.setIconPaddingDp(this.iconPaddingDp);
        buttonData.setTexts(this.texts);
        return buttonData;
    }


    private ButtonData(boolean iconButton) {
        this.iconButton = iconButton;
    }

    public void setIsMainButton(boolean isMainButton) {
        this.isMainButton = isMainButton;
    }

    public boolean isMainButton() {
        return isMainButton;
    }

    public void setIsIconButton(boolean isIconButton) {
        iconButton = isIconButton;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    public void setText(String... text) {
        this.texts = new String[text.length];
        for (int i = 0, length = text.length; i < length; i++) {
            this.texts[i] = text[i];
        }
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public boolean isIconButton() {
        return iconButton;
    }

    public float getIconPaddingDp() {
        return iconPaddingDp;
    }

    public void setIconPaddingDp(float padding) {
        this.iconPaddingDp = padding;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }
}
