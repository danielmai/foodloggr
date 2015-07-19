/**
 * Class that holds nutritional facts about an item
 */
public class NutriFact {
  private int calories; // number of calories of item
  private int totalFat; // total fat of item
  private int sodium; // amount of sodium in item in milligrams
  private int carbohydrates; // total carbohydrate in grams

  private static CharSequence caloriesStr = "Calories";
  private static CharSequence totalFatStr = "Total Fat";
  private static CharSequence sodiumStr = "Sodium";
  private static CharSequence carbStr = "Total Carb";
  private static CharSequence carbStr2 = "Total Garb";

  // Constructor for creating a nutrition facts table.
  //  Takes in a string, parses it, and assigns the values to
  //  the corresponding descriptions.
  public NutriFact (String str) {
    String[] facts = str.split("\n");
    System.out.printf("Size of facts is: %d\n", facts.length);
    for (int i = 0; i < facts.length; i++) {
      String line = facts[i];
      System.out.printf("Line is %s\n", line);
      String[] tokens = line.split(" ");
      if (calories == 0 && i == 7) {
        line = line.replaceAll("\\D+","");
        if (line.length() > 0)
          calories = Integer.parseInt(line);
      }
      else if (line.contains(caloriesStr)) {
        calories = Integer.parseInt(tokens[1]);
      }
      else if (line.contains(totalFatStr)) {
        totalFat = Integer.parseInt(tokens[2].substring(0, tokens[2].length()-1));
      }
      else if (line.contains(sodiumStr)) {
        sodium = Integer.parseInt(tokens[1].substring(0,tokens[1].length()-2));
      }
      else if (line.contains(carbStr) || line.contains(carbStr2)) {
        carbohydrates = Integer.parseInt(tokens[2].substring(0,tokens[2].length()-1));
      }
    }
  }

  // Return number of calories
  public int getCalories () {
    return calories;
  }

  // Set number of calories
  public void setCalories (int calories) {
    this.calories = calories;
  }

  // Return total fat of item
  public int getTotalFat () {
    return totalFat;
  }

  // Set total fat of item
  public void setTotalFat (int totalFat) {
    this.totalFat = totalFat;
  }

  // Return number of sodium
  public int getSodium () {
    return sodium;
  }

  // Set number of sodium
  public void setSodium (int sodium) {
    this.sodium = sodium;
  }

  // Return number of carbohydrates
  public int getCarbohydrates () {
    return carbohydrates;
  }

  // Set number of carbohydrates
  public void setCarbohydrates (int carbohydrates) {
    this.carbohydrates = carbohydrates;
  }
}
