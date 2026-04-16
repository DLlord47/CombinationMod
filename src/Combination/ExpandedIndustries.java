package Combination;

import arc.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;

import Combination.content.*;
import Combination.utilities.*;

public class ExpandedIndustries extends Mod{
    long last = -1;

    public ExpandedIndustries(){
        Log.infoTag("[EI]", "Initializing...");

        Events.on(EventType.ContentInitEvent.class, e -> EIOverrides.apply());
        Events.on(EventType.ClientLoadEvent.class, e -> EISettings.create());
    }

    @Override
    public void loadContent(){
        logTime("[EI]", "Loading content...");

        CMItems.load();
        logTime("[EI]", "Items loaded!");
        CMFx.load();
        logTime("[EI]", "Effects loaded!");
        CMStatusEffects.load();
        logTime("[EI]", "Statuses loaded!");
        CMLiquids.load();
        logTime("[EI]", "Liquids loaded!");
        CMBulletTypes.load();
        logTime("[EI]", "Bullets loaded!");
        CMUnits.load();
        logTime("[EI]", "Units loaded!");
        CMBlocks.load();
        logTime("[EI]", "Blocks loaded!");
        CMTechTree.load();
        logTime("[EI]", "Tech Tree loaded!");
        CMLoadouts.load();
        logTime("[EI]", "Loadouts loaded!");

        Log.infoTag("[EI]", "Content loaded!");
    }

    public void logTime(String tag, String text){
        Log.infoTag(tag, text + (last > 0 ? " (" + Time.timeSinceNanos(last) / Time.nanosPerMilli + "ms)" : ""));
        last = Time.nanos();
    }
}
