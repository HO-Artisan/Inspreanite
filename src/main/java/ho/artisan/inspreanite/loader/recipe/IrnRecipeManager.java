package ho.artisan.inspreanite.loader.recipe;

import com.link.plushies.Items;
import ho.artisan.lib.recipe.api.RecipeManagerHelper;

public class IrnRecipeManager {

    public static void init() {
        remove();
    }

    private static void remove() {
        RecipeManagerHelper.removeRecipes(handler -> Items.ITEMS.forEach(item -> handler.remove(item.getId())));
    }

}
