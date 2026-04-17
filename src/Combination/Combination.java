package Combination;

import arc.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;

import Combination.content.*;
import Combination.utilities.*;

public class Combination extends Mod{
    long last = -1;

    public Combination(){
        Log.infoTag("[CM]", "Initializing...");

        Events.on(EventType.ContentInitEvent.class, e -> CMOverrides.apply());
        Events.on(EventType.ClientLoadEvent.class, e -> CMSettings.create());
    }

    @Override
    public void loadContent(){
        logTime("[CM]", "Loading content...");

        CMItems.load();
        logTime("[CM]", "Items loaded!");
        CMFx.load();
        logTime("[CM]", "Effects loaded!");
        CMStatusEffects.load();
        logTime("[CM]", "Statuses loaded!");
        CMLiquids.load();
        logTime("[CM]", "Liquids loaded!");
        CMBulletTypes.load();
        logTime("[CM]", "Bullets loaded!");
        CMUnits.load();
        logTime("[CM]", "Units loaded!");
        CMBlocks.load();
        logTime("[CM]", "Blocks loaded!");
        CMTechTree.load();
        logTime("[CM]", "Tech Tree loaded!");
        CMLoadouts.load();
        logTime("[CM]", "Loadouts loaded!");

        Log.infoTag("[CM]", "Content loaded!");
    }

    public void logTime(String tag, String text){
        Log.infoTag(tag, text + (last > 0 ? " (" + Time.timeSinceNanos(last) / Time.nanosPerMilli + "ms)" : ""));
        last = Time.nanos();
    }
}
