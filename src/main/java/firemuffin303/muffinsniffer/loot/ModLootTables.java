package firemuffin303.muffinsniffer.loot;

import firemuffin303.muffinsniffer.MuffinSniffer;
import net.minecraft.loot.LootManager;
import net.minecraft.util.Identifier;

public class ModLootTables {
    public static Identifier SNIFFER_SNIFFING_GIFT_GAMEPLAY;

    public static void init(){
        SNIFFER_SNIFFING_GIFT_GAMEPLAY = new Identifier(MuffinSniffer.MODID,"gameplay/sniffer_sniffing_gift");

    }

}
