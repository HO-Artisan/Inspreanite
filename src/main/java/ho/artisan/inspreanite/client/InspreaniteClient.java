package ho.artisan.inspreanite.client;

import ho.artisan.inspreanite.Inspreanite;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import org.jetbrains.annotations.Nullable;

public class InspreaniteClient implements ClientModInitializer {
    public static String ID = Inspreanite.ID + "Client";
    @Nullable
    private static RotatingCubeMapRenderer cubeMapRenderer;
    @Override
    public void onInitializeClient() {

    }

    public static void cubeMapRenderer(RotatingCubeMapRenderer cubeMapRenderer) {
        InspreaniteClient.cubeMapRenderer = cubeMapRenderer;
    }

    @Nullable
    public static RotatingCubeMapRenderer cubeMapRenderer() {
        return cubeMapRenderer;
    }
}
