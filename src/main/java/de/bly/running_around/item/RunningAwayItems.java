package de.bly.running_around.item;

import de.bly.running_around.Running_around;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class RunningAwayItems {

    public static final Item Analyzer = registerItem("analyzer", new Analyzer(new Item.Settings()));
    public static final Item Scanner = registerItem("scanner", new Scanner(new Item.Settings()));
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Running_around.MOD_ID, name), item);
    }

    public static void registerModItems() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(Analyzer);
            entries.add(Scanner);

        });
    }
}
