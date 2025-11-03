package de.bly.running_around;

import de.bly.running_around.block.RunningAroundBlocks;
import de.bly.running_around.item.RunningAwayItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class Running_around implements ModInitializer {

    public static int[] randomCoords() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int x = rnd.nextInt(-50000, 50001); // inclusive -50000 to 50000
        int y = 100;
        int z = rnd.nextInt(-50000, 50001);
        return new int[] { x, y, z };
    }

    public static final String MOD_ID = "running_around";

    public static final String ItemsListFile = "config/items.yml";
    @Override
    public void onInitialize() {






        //register Items
        RunningAwayItems.registerModItems();
        RunningAroundBlocks.registerModBlocks();

        //events
        ServerWorldEvents.LOAD.register((minecraftServer, serverWorld) -> {


                    if (serverWorld.getDimension().bedWorks() && !java.nio.file.Files.exists(java.nio.file.Path.of(ItemsListFile))) {


                        //write
                        List<String> items = new ArrayList<>();
                        Registries.BLOCK.forEach(block -> items.add(block.toString()));

                        Yaml yaml = new Yaml();
                        try (FileWriter writer = new FileWriter(ItemsListFile)) {
                            yaml.dump(items, writer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //remove

                        try (FileReader reader = new FileReader(ItemsListFile)) {
                            List<String> itemsfromfile = yaml.load(reader);
                            if (itemsfromfile == null) itemsfromfile = new ArrayList<>();
                            else itemsfromfile = new ArrayList<>(itemsfromfile);


                            itemsfromfile.remove("Block{minecraft:air}");
                            itemsfromfile.remove("Block{minecraft:command_block}");
                            itemsfromfile.remove("Block{minecraft:chain_command_block}");
                            itemsfromfile.remove("Block{minecraft:repeating_command_block}");
                            itemsfromfile.remove("Block{minecraft:structure_block}");
                            itemsfromfile.remove("Block{minecraft:structure_void}");
                            itemsfromfile.remove("Block{minecraft:lava}");
                            itemsfromfile.remove("Block{minecraft:water}");


                            // write back
                            try (FileWriter writer = new FileWriter(ItemsListFile)) {
                                yaml.dump(itemsfromfile, writer);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Yaml yamlcoords = new Yaml();
                        for (int i = 1; i <= 7; i++) {
                            int[] c = randomCoords();

                            serverWorld.setBlockState(new BlockPos(c[2],c[1],c[0]), RunningAroundBlocks.Collectable.getDefaultState());
                            System.out.println(c[2]+" "+ c[1]+" " + c[0]);

                            Map<String, Integer> coordMap = Map.of(
                                    "x", c[0],
                                    "y", c[1],
                                    "z", c[2]
                            );
                            String filename = "config/coords" + i + ".yml";
                            try (FileWriter writer = new FileWriter(filename)) {
                                yamlcoords.dump(coordMap, writer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }










                        serverWorld.setBlockState(new BlockPos(98765,100, 98765), RunningAroundBlocks.Collectable.getDefaultState());
                    }

            }
        );

    }




}
