package com.example.smarter_foodies;

import android.util.Pair;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class recipe {

    //  Must haves:
    private String copy_rights;
    private String title;
    private String main_category;
    private String category;
    private List<Pair<Double, String>> ingredients;
    private List<String> directions;
    private String prepTime;
    private String cookingTime;
    private String totalTime;
    private String servings;
    private String protein;
    private String fat;
    private String carbs;
    private String calories;

    //  Nice to have additions:
    private double stars;
    private int numOfStarGivers;
    private List<String> images;
    private HashMap<String, List<String>> comments;

    public recipe() {
        init();
    } // recipe(<empty>)

    public recipe(String title, String main_category, String category,
                  List<String> ingredients, List<String> directions,
                  String prepTime, String cookingTime, String servings, String protein, String calories,
                  String fat, String carbs, double stars, List<String> images, int numOfStarGivers,
                  HashMap<String, List<String>> comments, String copy_rights) {
        init();
        this.setTitle(title);
        this.setMain_category(main_category);
        this.setCategory(category);
        this.setIngredients(ingredients);
        this.setDirections(directions);
        this.setImages(images);
        this.setPrepTime(prepTime);
        this.setCookingTime(cookingTime);
        this.setTotalTime(this.prepTime + "(prep) + " + this.cookingTime + "(cooking)");
        this.setServings(servings);
        this.setProtein(protein);
        this.setCarbs(carbs);
        this.setFat(fat);
        this.setStars(stars);
        this.setNumOfStarGivers(numOfStarGivers);
        this.setComments(comments);
        this.calories = calories;
        this.setCopy_rights(copy_rights);

    } // recipe(<data>)

    public recipe(JsonObject data, String copy_rights) {
        init();
        this.setTitle(data.get("title").toString());
        this.setMain_category(data.get("main category").toString());
        this.setCategory(data.get("Category").toString());
        this.setIngredients(convert_json_array_to_str_list(data.get("Ingredients").getAsJsonArray()));
        this.setDirections(convert_json_array_to_str_list(data.get("Directions").getAsJsonArray()));
        this.setImages(convert_json_array_to_str_list(data.get("Images").getAsJsonArray()));
        this.set_details(data.get("Details").getAsJsonArray());
        this.set_nutritions(data.get("Nutrition Facts").getAsJsonArray());
        this.setStars(0);
        this.setNumOfStarGivers(0);
        this.setCopy_rights(copy_rights);

    } // recipe(<json>)

    private void init() {
        this.ingredients = new ArrayList<>();
        this.comments = new HashMap<>();
        this.images = new ArrayList<>();
        this.comments = new HashMap<>();
    } // init


    private List<String> convert_json_array_to_str_list(JsonArray arr) {
        JsonArray ingredients = arr.getAsJsonArray();
        int arr_size = ingredients.size();
        List<String> str_list = new ArrayList<>();
        for (int i = 0; i < arr_size; i++){
            JsonObject ing = ingredients.get(i).getAsJsonObject();
            for (String key :ing.keySet()) {
                str_list.add(ing.get(key).toString().replace("\"", ""));
            }
        }
        return str_list;
    } // convert_json_array_to_str_list

    private void set_details(JsonArray details){
        int size = details.size();
        for (int i = 0; i < size; i++) {
            JsonObject detail = details.get(i).getAsJsonObject();
            for (String key :detail.keySet()) {
                switch (key) {
                    case "Prep Time":
                        this.prepTime = detail.get(key).toString();
                        break;
                    case "Cook Time":
                        this.cookingTime = detail.get(key).toString();
                        break;
                    case "Servings":
                        this.servings = detail.get(key).toString();
                        break;
                }
            }
        }
        this.setTotalTime(this.prepTime + "(prep) + " + this.cookingTime + "(cooking)");
    } // set_details

    private void set_nutritions(JsonArray nutrition){
        for (int i = 0; i < nutrition.size(); i++) {
            JsonObject fact = nutrition.get(i).getAsJsonObject();
            for (String key : fact.keySet()) {
                switch (key) {
                    case "Carbs":
                        this.carbs = fact.get(key).toString();
                        break;
                    case "Fat":
                        this.fat = fact.get(key).toString();
                        break;
                    case "Protein":
                        this.protein = fact.get("Protein").toString();
                        break;
                    case "Calories":
                        this.calories = fact.get(key).toString();
                        break;
                }
            }
        }
    } // set_nutritions

    public int getNumOfStarGivers() {
        return numOfStarGivers;
    }

    public void setNumOfStarGivers(int numOfStarGivers) {
        this.numOfStarGivers = numOfStarGivers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String main_category) {
        this.main_category = main_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Pair<Double, String>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        StringBuilder ans;
        double quantity = -1;
        for (String curr_str : ingredients) {
            String curr = curr_str.trim();
            String[] splited = curr.split(" ");
            if (splited.length > 0) {
                quantity = Double.parseDouble(splited[0]);
            }
            ans = new StringBuilder();
            int len = splited.length;
            for (int i = 1; i < len; i++) {
                ans.append(splited[i]);
                if (i < len - 1){
                    ans.append(" ");
                }
            }
            if (quantity != -1) {
                this.ingredients.add(new Pair<Double, String>(quantity, ans.toString()));
            }
        }
    } // setIngredients


    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> imgs) {
        this.images = imgs;
    }

    public void addImage(String img) {
        this.images.add(img);
    }

    public void delImage(String img) {
        this.images.remove(img);
    }

    public HashMap<String, List<String>> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, List<String>> comments) {
        this.comments = comments;
    }

    public void delComment(String name, String comment) {
        Objects.requireNonNull(this.comments.get(name)).remove(comment);
    }

    public void addComment(String name, String comment) {
        if (this.comments.get(name) == null) {
            this.comments.put(name, new ArrayList<>());
        }
        Objects.requireNonNull(this.comments.get(name)).add(comment);
    }

    public String getCopy_rights() {
        return copy_rights;
    }

    public void setCopy_rights(String copy_rights) {
        this.copy_rights = copy_rights;
    }

    @Override
    public String toString() {
        return "recipe{" +
                "title='" + title + '\'' +
                ", main_category='" + main_category + '\'' +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions.toString() +
                ", prepTime=" + prepTime +
                ", cookingTime=" + cookingTime +
                ", totalTime=" + totalTime +
                ", servings=" + servings +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbs=" + carbs +
                ", stars=" + stars +
                ", numOfStarGivers=" + numOfStarGivers +
                ", images=" + images +
                ", comments=" + comments +
                '}';
    } // toString
}