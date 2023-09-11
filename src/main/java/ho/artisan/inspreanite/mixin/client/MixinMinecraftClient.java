package ho.artisan.inspreanite.mixin.client;

import ho.artisan.inspreanite.Inspreanite;
import ho.artisan.inspreanite.api.utils.mods.IrnModDeps;
import ho.artisan.inspreanite.client.screen.MissingModScreen;
import ho.artisan.inspreanite.common.config.IrnConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.resource.ResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Inject(method = "onInitFinished", at = @At(value = "RETURN"))
    public void checkMods(RealmsClient realms, ResourceReload reload, RunArgs.QuickPlay quickPlay, CallbackInfo ci) {
        if (!IrnModDeps.isAllLoaded())
            // If not full loaded, set screen to MissingModScreen
            MinecraftClient.getInstance().setScreen(new MissingModScreen(IrnModDeps.getAllMissing(), IrnModDeps.isLoaded(true, false)));
    }

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void modifyWindowTitle(CallbackInfoReturnable<String> cir) {
        ModContainer container = FabricLoader.getInstance().getModContainer(Inspreanite.ID).orElseThrow();
        cir.setReturnValue(container.getMetadata().getName() + (IrnConfig.includeVersionInWindowTitle ? (" " + container.getMetadata().getVersion().toString()) : ""));
    }
}
