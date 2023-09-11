package ho.artisan.inspreanite.api.utils.mods;

import ho.artisan.inspreanite.api.utils.IrnLogger;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public enum IrnModDeps {
    FTB_LIBRARY("ftblibrary", Text.translatable("mod.ftblibrary.name"),
            "ftb-library-fabric/download/4720055/file",
            false, false),

    FTB_QUESTS("ftbquests", Text.translatable("mod.ftbquests.name"),
            "ftb-quests-fabric/download/4743498/file",
            false, false),

    FTB_TEAMS("ftbteams", Text.translatable("mod.ftbteams.name"),
            "ftb-teams-fabric/download/4623115/file",
            false, false),

    FTB_ULTIMINE("ftbultimine", Text.translatable("mod.ftbultimine.name"),
            "ftb-ultimine-fabric/files/4597011/file",
            false, false),

    FTB_CHUNKS("ftbchunks", Text.translatable("mod.ftbchunks.name"),
            "ftb-chunks-fabric/download/4727984/file",
            false, false),

    FTB_ESSENTIALS("ftbessential", Text.translatable("mod.ftbessential.name"),
            "ftb-essentials-forge/download/4596991/file",
            false, false),

    FTB_RANKS("ftbranks", Text.translatable("mod.ftbranks.name"),
            "ftb-ranks-fabric/files/4596741/file",
            false, false),

    FTB_XMOD_COMPAT("ftbxmodcompat", Text.translatable("mod.xmod.name"),
            "ftb-xmod-compat/download/4743962/file",
            false, false),

    //QUESTS_ADDITIONS("questsadditions", Text.translatable("mod.questsadditions.name"),
            //"quests-additions-fabric/download/4269973/file",
            //false, false),

    ITEM_FILTERS("itemfilters", Text.translatable("mod.itemfilters.name"),
            "item-filters/download/4728208/file",
            false, false);

    final String modId;
    private final Text name;
    @Nullable
    private final URL url;
    private final boolean required;
    private final boolean isClient;

    IrnModDeps(String id, Text name, String url, boolean required, boolean isClient) {
        this.modId = id;
        this.name = name;
        this.required = required;
        this.url = getUrl("https://www.curseforge.com/minecraft/mc-mods/" + url);
        this.isClient = isClient;
    }

    public String getModId() {
        return modId;
    }

    public Text getName() {
        return name;
    }

    public String getRawName() {
        return name.getString();
    }

    public boolean hasUrl() {
        return url != null;
    }

    @Nullable
    public URL getUrl() {
        if (!hasUrl())
            IrnLogger.logDebugAndError("Invalid URL for mod " + getRawName() + "!");
        return url;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isClient() {
        return isClient;
    }

    public boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public void openUrl() {
        if (hasUrl()) {
            try {
                Util.getOperatingSystem().open(url.toURI());
            } catch (URISyntaxException uriSyntaxException) {
                IrnLogger.logDebugAndError("Cannot handle URL for mod " + getRawName() + "!",
                        uriSyntaxException);
            }
        } else {
            IrnLogger.logInfo("No URL found for mod " + getRawName() + " (" + modId + ")!");
        }
    }

    public boolean matchesSide(boolean isServer) {
        return !isServer || !isClient;
    }

    public static Stream<IrnModDeps> stream() {
        return Arrays.stream(values());
    }

    private static ArrayList<IrnModDeps> arrayList(Stream<IrnModDeps> stream) {
        return stream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static ArrayList<IrnModDeps> getMissing(boolean required, boolean isServer) {
        return arrayList(stream().filter(dep -> (dep.isRequired() || !required)
                && dep.matchesSide(isServer) && !dep.isLoaded()));
    }

    public static ArrayList<IrnModDeps> getAllMissing() {
        return arrayList(stream().filter(dep -> !dep.isLoaded()));
    }

    public static boolean isLoaded(boolean required, boolean isServer) {
        return getMissing(required, isServer).stream().findAny().isEmpty();
    }

    public static boolean isAllLoaded() {
        return getAllMissing().stream().findAny().isEmpty();
    }

    public static String asString(boolean required, boolean isServer) {
        return getMissing(required, isServer).stream().map(dep -> dep.getName().getString())
                .reduce((a, b) -> a + ", " + b).orElse("");
    }

    private static URL getUrl(String spec) {
        try {
            return new URL(spec);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
