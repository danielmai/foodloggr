public class NutriFactList {
  private NutriFact[] list;
  private int curr;
  private int size;
  private int totalCal;
  private int totalFat;
  private int totalSodium;
  private int totalCarbs;

  // Creates a NutriFactList of size
  public NutriFactList (int size) {
    list = new NutriFact[size];
    this.size = size;
    curr = 0;
    totalCal = 0;
  }

  public void addNutriFact (String str) {
    if (curr < size) {
      list[curr] = new NutriFact(str);
      totalCal += list[curr].getCalories();
      totalFat += list[curr].getTotalFat();
      totalSodium += list[curr].getSodium();
      totalCarbs += list[curr].getCarbs();
      curr++;
    }
  }

  public NutriFact getNutriFact (int index) {
    return list[index];
  }

  public int getCalories () {
    return totalCal;
  }

  public int getTotalFat () {
    return totalFat;
  }

  public int getSodium () {
    return totalSodium;
  }

  public int getCarbs () {
    return totalCarbs;
  }
}
