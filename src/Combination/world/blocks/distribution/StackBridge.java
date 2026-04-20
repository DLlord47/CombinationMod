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
            // НАКОПЛЕНИЕ ПАЧКИ - забираем ВСЕ предметы из хранилища разом
            if (items.total() > 0) {
                Item firstItem = items.first();

                // Если пачки нет - создаём
                if (stackItem == null) {
                    stackItem = firstItem;
                    stackAmount = 0;
                }

                // Если предметы совпадают - забираем всё, что есть (до capacity)
                if (stackItem == firstItem && stackAmount < stackCapacity) {
                    int canTake = Math.min(stackCapacity - stackAmount, items.get(stackItem));
                    items.remove(stackItem, canTake);
                    stackAmount += canTake;
                }
            }

            // ОТПРАВКА ПАЧКИ
            if (stackItem != null && stackAmount >= stackCapacity) { // Отправляем только когда пачка полная!
                if (timer.get(timerAccept, 60/speed) && other.acceptItem(this, stackItem)) {
                    other.handleStack(stackItem, stackAmount, this);
                    moved = true;
                    stackItem = null;
                    stackAmount = 0;
                }
            }
        }

        // Принимаем пачку целиком
        @Override
        public void handleStack(Item item, int amount, Teamc source) {
            items.add(item, amount);
        }

        // Разрешаем принимать предметы только если они совместимы с текущей пачкой
        @Override
        public boolean acceptItem(Building source, Item item) {
            return (stackItem == null || stackItem == item) &&
                    items.total() + (stackItem == null ? 0 : stackCapacity - stackAmount) < itemCapacity;
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