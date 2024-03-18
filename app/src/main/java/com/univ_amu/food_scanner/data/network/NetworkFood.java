package com.univ_amu.food_scanner.data.network;

import java.util.List;

public class NetworkFood {
    String code;
    String name;
    String brands;
    String nutriscore;
    List<NetworkQuantity> quantities;


    public static class NetworkQuantity {
        String name;
        int rank;
        int level;
        double quantity;
        String unit;


        @Override
        public String toString() {
            return "Name: " + name + ", Rank: " + rank + ", Level: " + level + ", Quantity: " + quantity + " " + unit;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code: ").append(code).append("\n");
        sb.append("Nom: ").append(name).append("\n");
        sb.append("Marques: ").append(brands).append("\n");
        sb.append("Nutri-score: ").append(nutriscore).append("\n");
        sb.append("Quantités:\n");
        for (NetworkQuantity quantity : quantities) {
            sb.append("\t").append(quantity.toString()).append("\n");
        }
        return sb.toString();
    }
}
