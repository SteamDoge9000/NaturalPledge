package shadowfox.botanicaladdons.common.potions

/**
 * @author WireSegal
 * Created at 9:22 AM on 4/15/16.
 */
object ModPotions {
    val faithlessness: PotionMod
    val drab: PotionMod
    val rooted: PotionMod
    //    val overcharged: Potion
    //    val featherweight: Potion

    private var iconIndex = -1

    init {
        faithlessness = PotionFaithlessness(iconIndex++)
        drab = PotionDrabVision(iconIndex++)
        rooted = PotionRooted(iconIndex++)
    }
}