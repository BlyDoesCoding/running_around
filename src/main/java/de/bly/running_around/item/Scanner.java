package de.bly.running_around.item;


import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.bly.running_around.Running_around.ItemsListFile;



public class Scanner extends Item {

    public Scanner(Settings settings) {
        super(settings);
    }

    private int getNumb() {



        Yaml yaml = new Yaml();

        try (FileReader reader = new FileReader(ItemsListFile)) {
            List<String> itemsfromfile = yaml.load(reader);
            if (itemsfromfile== null) itemsfromfile = new ArrayList<>();
            else itemsfromfile = new ArrayList<>(itemsfromfile);

           return itemsfromfile.size();




        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {




        int B = 0;

        if (getNumb() <= 900) {
            B = 1;
        }
        if (getNumb() <= 700) {
            B = 2;
        }
        if (getNumb() <= 600) {
            B = 3;
        }
        if (getNumb() <= 500) {
            B = 4;
        }
        if (getNumb() <= 400) {
            B = 5;
        }
        if (getNumb() <= 300) {
            B = 6;
        }
        if (getNumb() <= 100) {
            B = 7;
        }




        for (int i = 1; i <= B; i++) {
            try ( FileReader r = new FileReader("config/coords"+ i +".yml")) {
                Map<String,Object> m = new Yaml().load(r);
                int x = ((Number)m.get("x")).intValue();
                int y = ((Number)m.get("y")).intValue();
                int z = ((Number)m.get("z")).intValue();
                user.sendMessage(Text.literal("#"+ i +" x=" + z + " y=" + y + " z=" + x), false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //user.sendMessage(Text.of("X" + x + " Y" + y + " Z" + z));

        return super.use(world, user, hand);
    }








}
