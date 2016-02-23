package com.example.leed3.pizzaorder;

/**
 * Created by leed3 on 2/14/2016.
 */
public class Pizza {

    private String size;
    private int veggieToppings;
    private int meatToppings;

    public Pizza(String size, int veggieToppings, int meatToppings) {
        this.size = size;
        this.veggieToppings = veggieToppings;
        this.meatToppings = meatToppings;
    }

    public int getPrice() {

        int total;
        int sizePrice = 0;
        int veggiePrice = 0;
        int meatPrice = 0;

        if (size.equals("Small")) {
            sizePrice = 5;
            veggiePrice = veggieToppings;
            meatPrice = 2*meatToppings;
        }

        else if (size.equals("Medium")) {
            sizePrice = 7;
            veggiePrice = 2*veggieToppings;
            meatPrice = 4*meatToppings;
        }

        else {
            sizePrice = 10;
            veggiePrice = 3*veggieToppings;
            meatPrice = 6*meatToppings;
        }

        total = sizePrice + veggiePrice + meatPrice;
        return total;
    }
}