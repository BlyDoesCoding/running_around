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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.bly.running_around.Running_around.ItemsListFile;

public class Analyzer extends Item {

    public Analyzer(Settings settings) {
        super(settings);
    }



    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {



        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        assert context.getPlayer() != null;
        Yaml yaml = new Yaml();

        try (FileReader reader = new FileReader(ItemsListFile)) {
            List<String> itemsfromfile = yaml.load(reader);
            if (itemsfromfile== null) itemsfromfile = new ArrayList<>();
            else itemsfromfile = new ArrayList<>(itemsfromfile);

            if (itemsfromfile.contains(clickedBlock.toString())) {
                itemsfromfile.remove(clickedBlock.toString());
                context.getPlayer().addExperience(5);

                context.getPlayer().sendMessage(Text.of("§a Analyzed: " + clickedBlock.toString()), true);





            }



            // write back
            try (FileWriter writer = new FileWriter(ItemsListFile)) {
                yaml.dump(itemsfromfile, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Ensure we don't spawn the lightning only on the client.
        // This is to prevent desync.
        Yaml yaml = new Yaml();

        try (FileReader reader = new FileReader(ItemsListFile)) {
            List<String> itemsfromfile = yaml.load(reader);
            user.sendMessage(Text.of(itemsfromfile.toString()));
            user.sendMessage(Text.of("------------------["+ itemsfromfile.toArray().length +"]-------------------"));



        } catch (IOException e) {
            e.printStackTrace();
        }





        // Nothing has changed to the item stack,
        // so we just return it how it was.
        return super.use(world, user, hand);
    }







}
