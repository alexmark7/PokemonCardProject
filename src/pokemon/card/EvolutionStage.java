package pokemon.card;

// A card's evolution stage.
// Basic, Stage 1, Stage 2, VMAX and VSTAR sort first in this order,
// then the other stages alphabetically
public enum EvolutionStage {

    BASIC("Basic"),
    STAGE_1("Stage 1"),
    STAGE_2("Stage 2"),
    VMAX("VMAX"),
    VSTAR("VSTAR"),

    BABY("Baby"),
    BREAK("BREAK"),
    LEVEL_UP("LEVEL-UP"),
    MEGA("Mega"),
    RESTORED("Restored"),
    V_UNION("V-UNION");

    private String displayName;

    EvolutionStage(String displayName) {
        this.displayName = displayName;
    }

    // Turns text from the file into a stage, or null if it is unknown
    public static EvolutionStage parse(String value) {

        if (value == null) {
            return null;
        }

        String text = value.trim().toLowerCase();

        switch (text) {
            case "basic":
                return BASIC;
            case "stage1":
            case "stage 1":
                return STAGE_1;
            case "stage2":
            case "stage 2":
                return STAGE_2;
            case "vmax":
                return VMAX;
            case "vstar":
                return VSTAR;
            case "baby":
                return BABY;
            case "break":
                return BREAK;
            case "level-up":
                return LEVEL_UP;
            case "mega":
                return MEGA;
            case "restored":
                return RESTORED;
            case "v-union":
                return V_UNION;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
