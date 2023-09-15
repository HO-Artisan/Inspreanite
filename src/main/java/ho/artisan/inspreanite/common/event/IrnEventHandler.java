package ho.artisan.inspreanite.common.event;

import ho.artisan.inspreanite.Inspreanite;
import ho.artisan.inspreanite.api.utils.IrnLogger;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class IrnEventHandler {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal(Inspreanite.ID)
                    .then(literal("export_struct")
                            .executes(context -> {
                                context.getSource().sendMessage(Text.literal("Export structures identifier list"));
                                Registries.STRUCTURE_TYPE.getIds().iterator().forEachRemaining(identifier -> IrnLogger.logInfo(identifier.toString()));
                                return 1;
                            })
                    )
            );
        });
    }

}
