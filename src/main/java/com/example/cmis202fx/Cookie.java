package com.example.cmis202fx;

public enum Cookie {
    LEMONUPS ("Lemon Ups", 210, 5),
    TOFFEE_TASTIC ("Toffe-tastic", 210, 6),
    THIN_MINTS ("Thin Mints", 120, 5),
    CARAMEL_CHOCOLATE_CHIP ("Caramel Chocolate Chip", 170, 6),
    DO_SI_DOS ("Do-si-dos", 160, 5),
    LEMONADES ("Lemonades", 200, 5),
    TREFOILS ("Trefoils", 100, 5),
    SAMOAS ("Samoas", 200, 5),
    TAGALONGS ("Tagalongs", 210, 5);

    private String cookieName;
    private int caloriesPerServing;
    private int price;

    private Cookie(String cookieName, int caloriesPerServing, int price){
        this.cookieName = cookieName;
        this.caloriesPerServing = caloriesPerServing;
        this.price = price;
    }

    public String getCookieName() {
        return cookieName;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.getCookieName().toUpperCase() + " [" +
                "cookieName='" + cookieName + '\'' +
                ", caloriesPerServing=" + caloriesPerServing +
                ", price=" + price +
                ']';
    }
}
