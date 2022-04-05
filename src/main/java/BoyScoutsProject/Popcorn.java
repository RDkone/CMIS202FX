package BoyScoutsProject;

public enum Popcorn {
    HOKEY_POKEY("Original Hokey Pokey", 140, 8),
    CARAMEL_CORN("Gourmet Caramel Corn", 150, 8),
    TEXAS_CHEDDAR("Texas Cheddar", 170, 10),
    CARAMEL_APPLE_CORN("Caramel Apple Corn", 130, 8),
    HOKEY_POKEY_CHOCOLATE("Hokey Pokey w/ Chocolate", 140, 8),
    SPICY_CHICAGO("Spicy Chicago-Style", 160, 8),
    BUTTERY_CARAMEL("Buttery Caramel", 130, 10),
    CARAMEL_CORN_CHOCOLATE("Caramel Corn w/ Chocolate", 150, 8),
    THREE_CHEESE("Original Hokey Pokey", 140, 10),
    WHITE_CHEDDAR("White Cheddar", 120, 10);

    private String popcornName;
    private int caloriesPerServing;
    private int price;

    private Popcorn(String name, int cal, int price){
        this.popcornName = name;
        this.caloriesPerServing = cal;
        this.price = price;
    }

    public String getPopcornName() {
        return popcornName;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.name() + " [Popcorn=" + getPopcornName() + ", Cal/Serving=" +
                getCaloriesPerServing() + ", Cost=" + getPrice() + "]";
    }
}
