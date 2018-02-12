package net.senmori.senlib.util;

import com.google.common.base.CaseFormat;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.senmori.senlib.translation.LanguageFileType;
import net.senmori.senlib.translation.LanguageLoader;
import net.senmori.senlib.translation.maps.JsonLanguageMap;
import org.apache.commons.lang.Validate;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_12_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class AttributeUtil {

    public static ItemStack addAttribute(Attribute attribute, AttributeModifier modifier, ItemStack stack, EquipmentSlot slot) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        NBTTagCompound root = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        Validate.notNull(root, "Null ItemStack NBT Tag");
        NBTTagList modifiers = root.getList("AttributeModifiers", 10);
        if(modifiers == null) {
            modifiers = new NBTTagList();
        }

        NBTTagCompound nmsModifier = GenericAttributes.a(convert(modifier));

        nmsModifier.setString("AttributeName", toMinecraft(attribute));
        nmsModifier.setString("Slot", CraftEquipmentSlot.getNMS(slot).d());

        modifiers.add(nmsModifier);

        Validate.notNull(nmsModifier, "Null modifiers tag");
        root.set("AttributeModifiers", modifiers);
        nmsStack.setTag(root);

        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static AttributeModifier convert(net.minecraft.server.v1_12_R1.AttributeModifier nms) {
        return new AttributeModifier(nms.a(), nms.b(), nms.d(), AttributeModifier.Operation.values()[nms.c()]);
    }

    public static net.minecraft.server.v1_12_R1.AttributeModifier convert(AttributeModifier bukkit) {
        return new net.minecraft.server.v1_12_R1.AttributeModifier(bukkit.getUniqueId(), bukkit.getName(), bukkit.getAmount(), bukkit.getOperation().ordinal());
    }

    public static String toMinecraft(String bukkit) {
        int first = bukkit.indexOf('_');
        int second = bukkit.indexOf('_', first + 1);

        StringBuilder sb = new StringBuilder(bukkit.toLowerCase(java.util.Locale.ENGLISH));

        sb.setCharAt(first, '.');
        if (second != -1) {
            sb.deleteCharAt(second);
            sb.setCharAt(second, bukkit.charAt(second + 1));
        }

        return sb.toString();
    }

    public static String toMinecraft(Attribute attribute) {
        return toMinecraft(attribute.name());
    }

    public static Attribute fromMinecraft(String nms) {
        String[] split = nms.split("\\.", 2);

        String generic = split[0];
        String descriptor = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, split[1]);
        String fin = generic + "_" + descriptor;
        return Attribute.valueOf(fin.toUpperCase());
    }
}
