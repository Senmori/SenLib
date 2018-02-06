package net.senmori.senlib.configuration.resolver;

import com.google.common.collect.Maps;
import net.senmori.senlib.configuration.resolver.types.AttributeModifierResolver;
import net.senmori.senlib.configuration.resolver.types.BlockVectorResolver;
import net.senmori.senlib.configuration.resolver.types.ChatColorResolver;
import net.senmori.senlib.configuration.resolver.types.FireworkEffectResolver;
import net.senmori.senlib.configuration.resolver.types.ItemStackResolver;
import net.senmori.senlib.configuration.resolver.types.LocationResolver;
import net.senmori.senlib.configuration.resolver.types.PatternResolver;
import net.senmori.senlib.configuration.resolver.types.PlayerResolver;
import net.senmori.senlib.configuration.resolver.types.PotionEffectResolver;
import net.senmori.senlib.configuration.resolver.types.SerializableObjectResolver;
import net.senmori.senlib.configuration.resolver.types.VectorResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public final class ConfigurationResolver {

    public static Map<Class, ObjectResolver> resolvers = Maps.newHashMap();

    public static final VectorResolver VECTOR = addResolver(new VectorResolver());
    public static final ChatColorResolver CHAT_COLOR = addResolver(new ChatColorResolver());
    public static final ItemStackResolver ITEMSTACK = addResolver(new ItemStackResolver());
    public static final AttributeModifierResolver ATTRIBUTE_MODIFIER = addResolver(new AttributeModifierResolver());
    public static final BlockVectorResolver BLOCK_VECTOR = addResolver(new BlockVectorResolver());
    public static final PlayerResolver OFFLINE_PLAYER = addResolver(new PlayerResolver());
    public static final LocationResolver LOCATION = addResolver(new LocationResolver());
    public static final PatternResolver PATTERN = addResolver(new PatternResolver());
    public static final FireworkEffectResolver FIREWORK_EFFECT = addResolver(new FireworkEffectResolver());
    public static final PotionEffectResolver POTION_EFFECT = addResolver(new PotionEffectResolver());

    public static final SerializableObjectResolver GENERIC_RESOLVER = new SerializableObjectResolver();

    private ConfigurationResolver() { }

    public static <T extends ObjectResolver> T addResolver(T resolver) {
        resolvers.put(resolver.getValueClass(), resolver);
        return resolver;
    }

    public static <T extends ObjectResolver> boolean removeResolver(T resolver) {
        return resolvers.remove(resolver.getValueClass()) != null;
    }

    public static boolean removeResolver(Class clazz) {
        return resolvers.remove(clazz) != null;
    }

    @Nullable
    public static ObjectResolver getResolver(Class clazz) {
        ObjectResolver resolver = resolvers.get(clazz);
        if(resolver == null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
            return GENERIC_RESOLVER;
        }
        return resolver;
    }
}
