package Combination.world.blocks.distribution;

import arc.util.io.*;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.BufferedItemBridge;
import mindustry.world.blocks.distribution.BufferedItemBridge.*;

public class StackBridge extends BufferedItemBridge {
    public int stackCapacity = 10;

    public StackBridge(String name) {
        super(name);
        bufferCapacity = stackCapacity;
    }

    public class StackBridgeBuild extends BufferedItemBridgeBuild {
        public Item stackItem = null;
        public int stackAmount = 0;

        @Override
        public void updateTransport(Building other) {
            if (stackItem != null && stackAmount < stackCapacity && items.total() > 0) {
                Item take = items.first();
                if (take == stackItem) {
                    int canTake = Math.min(stackCapacity - stackAmount, items.get(take));
                    items.remove(take, canTake);
                    stackAmount += canTake;
                }
            }

            if (stackItem == null && items.total() > 0) {
                stackItem = items.first();
                stackAmount = Math.min(stackCapacity, items.get(stackItem));
                items.remove(stackItem, stackAmount);
            }

            if (stackItem != null && stackAmount > 0) {
                if (timer(timerAccept, 4 / timeScale) && other.acceptItem(this, stackItem)) {
                    other.handleStack(stackItem, stackAmount, this);
                    moved = true;
                    stackItem = null;
                    stackAmount = 0;
                }
            }
        }

        @Override
        public void handleStack(Item item, int amount, Teamc source) {
            items.add(item, amount);
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return (stackItem == null || stackItem == item) &&
                    items.total() + (stackAmount > 0 ? stackCapacity - stackAmount : 0) < itemCapacity;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(stackItem == null ? -1 : stackItem.id);
            write.i(stackAmount);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int itemId = read.i();
            stackItem = itemId == -1 ? null : Vars.content.item(itemId);
            stackAmount = read.i();
        }
    }
}