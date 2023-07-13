package io.github.blumbo.blfscheduler.mixin;

import io.github.blumbo.blfscheduler.BlfSchedulerTicker;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        BlfSchedulerTicker.tick();
    }

}
