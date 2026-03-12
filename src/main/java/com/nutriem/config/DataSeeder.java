package com.nutriem.config;

import com.nutriem.model.Food;
import com.nutriem.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private final FoodRepository foodRepository;

    public DataSeeder(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(String... args) {
        if (foodRepository.count() > 0) return; // already seeded
        log.info("Seeding food database...");
        seed();
        log.info("Food database seeded: {} items", foodRepository.count());
    }

    private void seed() {
        // name, category, kcal, protein, carbs, fat, fiber, serving desc, serving g
        Object[][] foods = {
            // ── GRAINS ──────────────────────────────────────────────────
            {"Rolled oats",              "GRAINS",       389, 17.0, 66.0,  7.0,  10.0, "1/2 cup",   40.0},
            {"White rice (cooked)",      "GRAINS",       130,  2.7, 28.2,  0.3,   0.4, "1/2 cup",  100.0},
            {"Brown rice (cooked)",      "GRAINS",       112,  2.6, 23.5,  0.9,   1.8, "1/2 cup",  100.0},
            {"Whole wheat bread",        "GRAINS",       247,  9.0, 43.0,  3.5,   6.0, "1 slice",   30.0},
            {"White bread",              "GRAINS",       265,  9.0, 49.0,  3.2,   2.7, "1 slice",   30.0},
            {"Corn tortilla",            "GRAINS",       218,  5.7, 46.0,  2.5,   6.3, "1 tortilla",26.0},
            {"Flour tortilla",           "GRAINS",       312,  8.0, 49.0,  8.3,   3.0, "1 tortilla",40.0},
            {"Quinoa (cooked)",          "GRAINS",       120,  4.4, 21.3,  1.9,   2.8, "1/2 cup",   93.0},
            {"Spaghetti (cooked)",       "GRAINS",       158,  5.8, 30.9,  0.9,   1.8, "1 cup",    140.0},
            {"Corn flakes",              "GRAINS",       357,  6.7, 84.0,  0.4,   2.0, "1 cup",     28.0},
            {"Granola",                  "GRAINS",       471, 10.0, 64.0, 20.0,   6.0, "1/4 cup",   29.0},
            {"Crackers (whole wheat)",   "GRAINS",       432, 10.0, 65.0, 14.0,   9.0, "5 crackers",15.0},

            // ── VEGETABLES ──────────────────────────────────────────────
            {"Broccoli (cooked)",        "VEGETABLES",    35,  2.4,  7.2,  0.4,   3.3, "1/2 cup",   78.0},
            {"Spinach (raw)",            "VEGETABLES",    23,  2.9,  3.6,  0.4,   2.2, "1 cup",     30.0},
            {"Tomato",                   "VEGETABLES",    18,  0.9,  3.9,  0.2,   1.2, "1 medium",  123.0},
            {"Carrot",                   "VEGETABLES",    41,  0.9,  9.6,  0.2,   2.8, "1 medium",  61.0},
            {"Bell pepper (red)",        "VEGETABLES",    31,  1.0,  6.0,  0.3,   2.1, "1/2 cup",   75.0},
            {"Zucchini (cooked)",        "VEGETABLES",    17,  1.1,  3.6,  0.2,   1.2, "1/2 cup",   90.0},
            {"Cucumber",                 "VEGETABLES",    15,  0.7,  3.6,  0.1,   0.5, "1/2 cup",   52.0},
            {"Onion",                    "VEGETABLES",    40,  1.1,  9.3,  0.1,   1.7, "1/4 cup",   40.0},
            {"Garlic",                   "VEGETABLES",   149,  6.4, 33.1,  0.5,   2.1, "1 clove",    3.0},
            {"Lettuce (romaine)",        "VEGETABLES",    17,  1.2,  3.3,  0.3,   2.1, "1 cup",     47.0},
            {"Sweet potato (cooked)",    "VEGETABLES",    90,  2.0, 20.7,  0.1,   3.3, "1/2 medium",75.0},
            {"Potato (boiled)",          "VEGETABLES",    87,  1.9, 20.1,  0.1,   1.8, "1 medium",  136.0},
            {"Mushrooms (cooked)",       "VEGETABLES",    28,  2.2,  5.3,  0.4,   1.7, "1/2 cup",   78.0},
            {"Asparagus (cooked)",       "VEGETABLES",    22,  2.4,  4.1,  0.2,   2.1, "6 spears",  60.0},
            {"Cauliflower (cooked)",     "VEGETABLES",    23,  1.8,  5.1,  0.1,   2.0, "1/2 cup",   62.0},
            {"Celery",                   "VEGETABLES",    16,  0.7,  3.0,  0.2,   1.6, "1 stalk",   40.0},
            {"Kale (cooked)",            "VEGETABLES",    28,  1.9,  5.6,  0.5,   2.0, "1/2 cup",   65.0},
            {"Green beans (cooked)",     "VEGETABLES",    35,  1.9,  7.9,  0.4,   3.4, "1/2 cup",   62.0},
            {"Corn (cooked)",            "VEGETABLES",    96,  3.4, 21.0,  1.5,   2.4, "1/2 cup",   82.0},
            {"Peas (cooked)",            "VEGETABLES",    84,  5.4, 15.6,  0.2,   5.5, "1/2 cup",   80.0},
            {"Beet (cooked)",            "VEGETABLES",    44,  1.7,  9.9,  0.2,   2.0, "1/2 cup",   85.0},
            {"Eggplant (cooked)",        "VEGETABLES",    25,  0.6,  6.0,  0.2,   2.5, "1/2 cup",   50.0},
            {"Nopal (cooked)",           "VEGETABLES",    22,  1.5,  5.0,  0.2,   2.2, "1/2 cup",   75.0},

            // ── FRUITS ──────────────────────────────────────────────────
            {"Apple",                    "FRUITS",        52,  0.3, 13.8,  0.2,   2.4, "1 medium",  182.0},
            {"Banana",                   "FRUITS",        89,  1.1, 22.8,  0.3,   2.6, "1 medium",  118.0},
            {"Orange",                   "FRUITS",        47,  0.9, 11.8,  0.1,   2.4, "1 medium",  131.0},
            {"Strawberries",             "FRUITS",        32,  0.7,  7.7,  0.3,   2.0, "1 cup",     152.0},
            {"Blueberries",              "FRUITS",        57,  0.7, 14.5,  0.3,   2.4, "1/2 cup",   74.0},
            {"Mango",                    "FRUITS",        60,  0.8, 15.0,  0.4,   1.6, "1/2 cup",   82.0},
            {"Pineapple",                "FRUITS",        50,  0.5, 13.1,  0.1,   1.4, "1/2 cup",   82.0},
            {"Watermelon",               "FRUITS",        30,  0.6,  7.6,  0.2,   0.4, "1 cup",    152.0},
            {"Grapes",                   "FRUITS",        69,  0.7, 18.1,  0.2,   0.9, "1 cup",    151.0},
            {"Peach",                    "FRUITS",        39,  0.9,  9.5,  0.3,   1.5, "1 medium",  150.0},
            {"Pear",                     "FRUITS",        57,  0.4, 15.2,  0.1,   3.1, "1 medium",  178.0},
            {"Kiwi",                     "FRUITS",        61,  1.1, 14.7,  0.5,   3.0, "1 medium",   76.0},
            {"Avocado",                  "FRUITS",       160,  2.0,  8.5, 14.7,   6.7, "1/2 fruit",  68.0},
            {"Papaya",                   "FRUITS",        43,  0.5, 11.0,  0.3,   1.7, "1 cup",     145.0},
            {"Guava",                    "FRUITS",        68,  2.6, 14.3,  1.0,   5.4, "1 fruit",    55.0},

            // ── DAIRY ───────────────────────────────────────────────────
            {"Whole milk",               "DAIRY",         61,  3.2,  4.8,  3.3,   0.0, "1 cup",    244.0},
            {"Skim milk",                "DAIRY",         34,  3.4,  5.0,  0.1,   0.0, "1 cup",    244.0},
            {"Greek yogurt (plain)",     "DAIRY",         59,  9.9,  3.9,  0.4,   0.0, "1/2 cup",  113.0},
            {"Yogurt (plain, whole)",    "DAIRY",         61,  3.5,  4.7,  3.3,   0.0, "1/2 cup",  113.0},
            {"Cheddar cheese",           "DAIRY",        403, 25.0,  1.3, 33.0,   0.0, "1 oz",      28.0},
            {"Mozzarella",               "DAIRY",        280, 28.0,  2.2, 17.0,   0.0, "1 oz",      28.0},
            {"Cottage cheese",           "DAIRY",         98, 11.1,  3.4,  4.3,   0.0, "1/2 cup",  113.0},
            {"Butter",                   "DAIRY",        717,  0.9,  0.1, 81.0,   0.0, "1 tbsp",    14.0},
            {"Cream cheese",             "DAIRY",        342,  6.0,  4.1, 34.0,   0.0, "2 tbsp",    29.0},

            // ── MEAT ────────────────────────────────────────────────────
            {"Chicken breast (cooked)",  "MEAT",         165, 31.0,  0.0,  3.6,   0.0, "100g",     100.0},
            {"Chicken thigh (cooked)",   "MEAT",         209, 26.0,  0.0, 11.0,   0.0, "100g",     100.0},
            {"Ground beef (lean)",       "MEAT",         215, 26.0,  0.0, 12.0,   0.0, "100g",     100.0},
            {"Beef steak (sirloin)",     "MEAT",         207, 26.0,  0.0, 11.0,   0.0, "100g",     100.0},
            {"Pork loin (cooked)",       "MEAT",         242, 29.0,  0.0, 14.0,   0.0, "100g",     100.0},
            {"Ham",                      "MEAT",         145, 21.0,  1.5,  6.0,   0.0, "100g",     100.0},
            {"Turkey breast (cooked)",   "MEAT",         135, 30.0,  0.0,  1.0,   0.0, "100g",     100.0},
            {"Lamb chop (cooked)",       "MEAT",         294, 25.0,  0.0, 21.0,   0.0, "100g",     100.0},

            // ── FISH & SEAFOOD ───────────────────────────────────────────
            {"Salmon (cooked)",          "FISH_SEAFOOD", 208, 20.0,  0.0, 13.0,   0.0, "100g",     100.0},
            {"Tuna (canned, water)",     "FISH_SEAFOOD", 116, 25.5,  0.0,  1.0,   0.0, "100g",     100.0},
            {"Tilapia (cooked)",         "FISH_SEAFOOD", 128, 26.0,  0.0,  3.0,   0.0, "100g",     100.0},
            {"Shrimp (cooked)",          "FISH_SEAFOOD",  99, 24.0,  0.0,  0.3,   0.0, "100g",     100.0},
            {"Sardines (canned)",        "FISH_SEAFOOD", 208, 24.6,  0.0, 11.5,   0.0, "100g",     100.0},
            {"Cod (cooked)",             "FISH_SEAFOOD",  82, 18.0,  0.0,  0.7,   0.0, "100g",     100.0},

            // ── EGGS ────────────────────────────────────────────────────
            {"Whole egg",                "EGGS",          155, 13.0,  1.1, 11.0,   0.0, "1 large",   50.0},
            {"Egg white",                "EGGS",           52, 11.0,  0.7,  0.2,   0.0, "1 large",   33.0},
            {"Egg yolk",                 "EGGS",          322, 16.0,  3.6, 27.0,   0.0, "1 large",   17.0},

            // ── LEGUMES ─────────────────────────────────────────────────
            {"Black beans (cooked)",     "LEGUMES",       132,  8.9, 23.7,  0.5,   8.7, "1/2 cup",   86.0},
            {"Pinto beans (cooked)",     "LEGUMES",       143,  9.0, 26.8,  0.6,   7.4, "1/2 cup",   86.0},
            {"Lentils (cooked)",         "LEGUMES",       116,  9.0, 20.1,  0.4,   7.9, "1/2 cup",   99.0},
            {"Chickpeas (cooked)",       "LEGUMES",       164,  8.9, 27.4,  2.6,   7.6, "1/2 cup",   82.0},
            {"Kidney beans (cooked)",    "LEGUMES",       127,  8.7, 22.8,  0.5,   6.4, "1/2 cup",   88.0},
            {"Soy beans (cooked)",       "LEGUMES",       173, 16.6,  9.9,  9.0,   6.0, "1/2 cup",   86.0},
            {"Tofu (firm)",              "LEGUMES",        76,  8.0,  1.9,  4.8,   0.3, "100g",     100.0},

            // ── NUTS & SEEDS ─────────────────────────────────────────────
            {"Almonds",                  "NUTS_SEEDS",    579, 21.2, 21.6, 49.9,  12.5, "1 oz",      28.0},
            {"Walnuts",                  "NUTS_SEEDS",    654, 15.2, 13.7, 65.2,   6.7, "1 oz",      28.0},
            {"Peanuts",                  "NUTS_SEEDS",    567, 25.8, 16.1, 49.2,   8.5, "1 oz",      28.0},
            {"Peanut butter",            "NUTS_SEEDS",    588, 25.0, 20.0, 50.0,   6.0, "2 tbsp",    32.0},
            {"Sunflower seeds",          "NUTS_SEEDS",    584, 20.8, 20.0, 51.5,   8.6, "1 oz",      28.0},
            {"Chia seeds",               "NUTS_SEEDS",    486, 16.5, 42.1, 30.7,  34.4, "1 tbsp",    12.0},
            {"Flaxseeds",                "NUTS_SEEDS",    534, 18.3, 28.9, 42.2,  27.3, "1 tbsp",    10.0},
            {"Cashews",                  "NUTS_SEEDS",    553, 18.2, 30.2, 43.8,   3.3, "1 oz",      28.0},
            {"Pistachios",               "NUTS_SEEDS",    562, 20.2, 27.5, 45.4,  10.3, "1 oz",      28.0},

            // ── OILS & FATS ──────────────────────────────────────────────
            {"Olive oil",                "OILS_FATS",     884,  0.0,  0.0, 100.0,  0.0, "1 tbsp",    14.0},
            {"Coconut oil",              "OILS_FATS",     862,  0.0,  0.0, 100.0,  0.0, "1 tbsp",    14.0},
            {"Canola oil",               "OILS_FATS",     884,  0.0,  0.0, 100.0,  0.0, "1 tbsp",    14.0},
            {"Sunflower oil",            "OILS_FATS",     884,  0.0,  0.0, 100.0,  0.0, "1 tbsp",    14.0},

            // ── BEVERAGES ────────────────────────────────────────────────
            {"Orange juice",             "BEVERAGES",      45,  0.7, 10.4,  0.2,   0.2, "1 cup",    248.0},
            {"Whole milk",               "BEVERAGES",      61,  3.2,  4.8,  3.3,   0.0, "1 cup",    244.0},
            {"Whey protein powder",      "BEVERAGES",     352, 80.0, 12.0,  3.0,   0.0, "1 scoop",   30.0},
        };

        for (Object[] f : foods) {
            String name = (String) f[0];
            if (foodRepository.existsByNameIgnoreCase(name)) continue;

            Food food = new Food();
            food.setName(name);
            food.setVerified(true);

            try { food.setCategory(Food.Category.valueOf((String) f[1])); }
            catch (Exception ignored) {}

            food.setCaloriesPer100g(toDouble(f[2]));
            food.setProteinPer100g(toDouble(f[3]));
            food.setCarbsPer100g(toDouble(f[4]));
            food.setFatPer100g(toDouble(f[5]));
            food.setFiberPer100g(toDouble(f[6]));
            food.setServingDescription((String) f[7]);
            food.setServingGrams(toDouble(f[8]));

            foodRepository.save(food);
        }
    }

    private Double toDouble(Object v) {
        if (v instanceof Integer) return ((Integer) v).doubleValue();
        if (v instanceof Double)  return (Double) v;
        return null;
    }
}
