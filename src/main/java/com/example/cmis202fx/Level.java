package com.example.cmis202fx;

public enum Level {
    DAISIES,
    BROWNIES,
    JUNIORS,
    CADETTES,
    SENIORS,
    AMBASSADORS;

    public static String getAgeGroup(Enum levels) {
        if (levels.toString().equalsIgnoreCase("DAISIES")){
            return "Grade K-1";
        }
        else if (levels.toString().equalsIgnoreCase("BROWNIES")){
            return "Grades 2-3";
        }
        else if (levels.toString().equalsIgnoreCase("JUNIORS")){
            return "Grades 4-5";
        }
        else if (levels.toString().equalsIgnoreCase("CADETTES")){
            return "Grades 6-8";
        }
        else if (levels.toString().equalsIgnoreCase("SENIORS")){
            return "Grades 9-10";
        }
        else if (levels.toString().equalsIgnoreCase("AMBASSADORS")){
            return "Grades 11-12";
        }
        else {
            return null;
        }
    }

    public static String getAgeGroup(String lvl){
        Level level1 = Level.DAISIES;
        Level level2 = Level.BROWNIES;
        Level level3 = Level.JUNIORS;
        Level level4 = Level.CADETTES;
        Level level5 = Level.SENIORS;
        Level level6 = Level.AMBASSADORS;
        if (lvl.equalsIgnoreCase(level1.toString())){
            return "Grade K-1";
        }
        else if (lvl.equalsIgnoreCase(level2.toString())){
            return "Grades 2-3";
        }
        else if (lvl.equalsIgnoreCase(level3.toString())){
            return "Grades 4-5";
        }
        else if (lvl.equalsIgnoreCase(level4.toString())){
            return "Grades 6-18";
        }
        else if (lvl.equalsIgnoreCase(level5.toString())){
            return "Grades 9-10";
        }
        else if (lvl.equalsIgnoreCase(level6.toString())){
            return "Grades 11-12";
        }
        else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
}
