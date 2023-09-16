package ho.artisan.inspreanite;

import ho.artisan.inspreanite.common.config.IrnConfig;
import ho.artisan.inspreanite.common.event.IrnEventHandler;
import ho.artisan.inspreanite.loader.recipe.IrnRecipeManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.krlite.equator.visual.color.AccurateColor;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Inspreanite implements ModInitializer {

    public static class Colors {
        public static final AccurateColor IRN_CYAN = AccurateColor.fromARGB(0x19EAEA);
        public static final AccurateColor IRN_MID_CYAN = AccurateColor.fromARGB(0x15B9C1);
        public static final AccurateColor IRN_DIM_CYAN = AccurateColor.fromARGB(0x166672);
        public static final AccurateColor IRN_GRAY_CYAN = AccurateColor.fromARGB(0x50757A);
        public static final AccurateColor IRN_BRIGHT_CYAN = AccurateColor.fromARGB(0xCBDADB);
        public static final AccurateColor IRN_BLACK = AccurateColor.fromARGB(0x020B0C);
        public static final AccurateColor QUEST_DEPENDENCY = AccurateColor.fromARGB(0x4BFE90);
        public static final AccurateColor QUEST_DEPENDENT = AccurateColor.fromARGB(0x55E8E8);
    }

    public static final String NAME = "Inspreanite";
    public static final String ID = "inspreanite";

    @Contract("_,_ -> new")
    public static @NotNull String genTranslationKey(String type, String... path) {
        return type + "." + ID + "." + String.join(".", path);
    }

    @Contract("_,_ -> new")
    public static @NotNull Text genTranslatableText(String type, String... path) {
        return Text.translatable(genTranslationKey(type, path));
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(IrnConfig.class, GsonConfigSerializer::new);

        IrnEventHandler.init();
        IrnRecipeManager.init();
    }
}
