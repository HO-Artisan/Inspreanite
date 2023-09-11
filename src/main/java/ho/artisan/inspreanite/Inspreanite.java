package ho.artisan.inspreanite;

import ho.artisan.inspreanite.loader.recipe.IrnRecipeManager;
import net.fabricmc.api.ModInitializer;

public class Inspreanite implements ModInitializer {
    @Override
    public void onInitialize() {
        IrnRecipeManager.init();
    }
}
