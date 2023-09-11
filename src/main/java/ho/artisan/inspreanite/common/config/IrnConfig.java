package ho.artisan.inspreanite.common.config;

import ho.artisan.inspreanite.Inspreanite;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Inspreanite.ID)
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class IrnConfig implements ConfigData {
    public static IrnConfig get() {
        return AutoConfig.getConfigHolder(IrnConfig.class).getConfig();
    }

    public static void save() {
        AutoConfig.getConfigHolder(IrnConfig.class).save();
    }

    public static boolean includeVersionInWindowTitle = false;
}