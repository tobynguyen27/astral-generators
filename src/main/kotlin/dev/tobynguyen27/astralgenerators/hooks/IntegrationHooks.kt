package dev.tobynguyen27.astralgenerators.hooks

object IntegrationHooks {
    fun init() {
        EnergyAPI.init()
        FluidTransferAPI.init()
    }
}
