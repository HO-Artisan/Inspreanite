package ho.artisan.inspreanite;

import ho.artisan.inspreanite.common.config.IrnConfig;
import ho.artisan.inspreanite.common.event.IrnEventHandler;
import ho.artisan.inspreanite.loader.recipe.IrnRecipeManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Inspreanite implements ModInitializer {

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
