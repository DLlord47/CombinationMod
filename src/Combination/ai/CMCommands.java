package Combination.ai;

import Combination.ai.types.*;
import mindustry.ai.*;

public class CMCommands {
    public static final UnitCommand

    healUnitsCommand = new UnitCommand("heal-units", "modeSurvival", u -> new FieldMedicAI());
}