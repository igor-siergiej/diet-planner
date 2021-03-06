package com.app.planner;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Profile {
    private String email;
    private String profileName; // TODO group up some of these variables so there is less instance variables
    private int height;
    private int weight;
    private int age;
    private String sex;
    private boolean pregnant;
    private boolean breastFeeding;
    private Diary diary;
    private Option options;
    private DailyIntake dailyIntake;
    private int calorieTarget; //TODO implement
    private UndoRedoStack<BaseScreenController> undoRedoStack;
    // stack for undo and redo

    public Profile(String email, String profileName, int height, int weight, int age, String sex, boolean pregnant, boolean breastFeeding, Diary diary, Option options) {

        this.email = email;
        this.profileName = profileName;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.sex = sex;
        this.pregnant = pregnant;
        this.breastFeeding = breastFeeding;
        this.diary = diary;
        this.options = options;

        dailyIntake = new DailyIntake();
        dailyIntake.setMaximumDoses();
        dailyIntake.setTargetNutrients(age, sex, pregnant, breastFeeding);
    }

    public void initialiseProfile() {
        diary = new Diary();
        dailyIntake = new DailyIntake();
        dailyIntake.setMaximumDoses();
        dailyIntake.setTargetNutrients(age, sex, pregnant, breastFeeding);
    }

    public Profile() {
    }

    @Override
    public String toString() {
        return "Profile{" +
                "email='" + email + '\'' +
                ", profileName='" + profileName + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", pregnant=" + pregnant +
                ", breastFeeding=" + breastFeeding +
                ", diary=" + diary +
                ", options=" + options +
                ", dailyIntake=" + dailyIntake +
                ", calorieTarget=" + calorieTarget +
                ", undoRedoStack=" + undoRedoStack +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public boolean isBreastFeeding() {
        return breastFeeding;
    }

    public void setBreastFeeding(boolean breastFeeding) {
        this.breastFeeding = breastFeeding;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Option getOptions() {
        return options;
    }

    public void setOptions(Option options) {
        this.options = options;
    }

    public DailyIntake getDailyIntake() {
        return dailyIntake;
    }

    public void saveToFile(File file) {
        try {
            Gson gson = createCustomGson();
            Writer writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(File file) {
        try {
            Gson gson = createCustomGson();
            JsonReader reader = new JsonReader(new FileReader(file));
            Profile profile = gson.fromJson(reader, Profile.class);
            this.setDiary(profile.getDiary());
            this.setProfileName(profile.getProfileName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadFromString(String loadString) {
        Gson gson = createCustomGson();
        Profile profile = gson.fromJson(loadString, Profile.class);
        this.setProfileName(profile.getProfileName());
        this.setDiary(profile.getDiary());
    }

    public String saveToString() {
        Gson gson = createCustomGson();
        String returnString = gson.toJson(this);
        return returnString;
    }

    public Gson createCustomGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        // this is needed because the standard Gson serializer and deserializer doesn't handle LocalDateTime well

        return gsonBuilder.setPrettyPrinting().create();
    }

    public float getNutrientValueForCurrentDay(String nutrientName) {
        float value = 0;
        DecimalFormat df = new DecimalFormat("0.0");
        ArrayList<Nutrient> nutrients = new ArrayList<>();
        for (Entry entry : getDiary().getEntriesDay(LocalDate.now())) {
            for (Food food : entry.getMeal().getFoods()) {
                nutrients.addAll(food.getNutrients());
            }
        }
        ArrayList<Nutrient> combinedList = Main.combineNutrientList(nutrients);
        for (Nutrient nutrient : combinedList) {
            if (nutrient.getNutrientName().equals(nutrientName)) {
                value = nutrient.getNutrientValue();
            }
        }
        return Float.parseFloat(df.format(value));
    }

    class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }

    class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        }
    }
}

