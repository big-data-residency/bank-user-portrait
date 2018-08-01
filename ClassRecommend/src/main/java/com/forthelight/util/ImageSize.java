package com.forthelight.util;

public class ImageSize {
    private Double x;
    private Double y;
    private Double width;
    private Double height;

    public ImageSize() {
    }

    public Integer getIntX() {
        return x.intValue();
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Integer getIntY() {
        return y.intValue();
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getIntWidth() {
        return width.intValue();
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Integer getIntHeight() {
        return height.intValue();
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
