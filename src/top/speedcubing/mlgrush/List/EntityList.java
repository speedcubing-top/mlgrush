package top.speedcubing.mlgrush.List;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import top.speedcubing.mlgrush.Core.MLGRush2x1;
import top.speedcubing.mlgrush.Core.StickDuel;
import top.speedcubing.lib.bukkit.entity.Hologram;
import top.speedcubing.lib.bukkit.entity.NPC;

import java.util.ArrayList;
import java.util.List;

public class EntityList {
    public static Hologram[] Lobbys = new Hologram[6];
    public static NPC[] npcs = new NPC[3];
    public static List<Integer> NPCS = new ArrayList<>();

    public void Load() {

        String VEPEXtextures = "ewogICJ0aW1lc3RhbXAiIDogMTYxNzE4MDQwNzAyNSwKICAicHJvZmlsZUlkIiA6ICIwMGM2Yjk0YTY5YmU0MzY3OTkwOTQxNjFjMjAxOWI3ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJLQUVWRVJZIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI1YTUzMTU2Zjk1ODhlZjNkZTQyMGU0ODA5MTYzN2Q3NGYwN2ZmM2MyMzIyNjRkM2Q0MGY3Y2RlOGQ1N2JiODMiCiAgICB9CiAgfQp9";
        String VEPEXsignature = "CY9LJ6UsQiJ4h0x2MlHVyWq08zc8bA5neVud+3Deh0eK9UcNU4eC5rk+5Wghq6Ue8KUUV8Kc+h6Ze4XuujN/OfX6tulvt2YrkLgDWh9dyoPFVhmUiIV7cyYUqHqCbNSvRDjFjMmlnmcgzLMmTYnsaArfxwk/vqnHpigkGMFa7EnfkyoFHHTs5evAPCfr2CXWTdOXJAgsGeb/o54KI159sjOdRjv4lgmdQFq+7SyzBKcGMD0cR5nB4thczXw79U9rfKc9IYFfMKtjTFV1Qxhzhnyugw9yPO5TiZ9qve59H9wf6THFgA7m8QWwVqjrQEIBTE7MSUDUS9jSU/sVPnYKz6F3z+pIQSPSPykh0AHW5T1fP5IerzVgo4ddBuAAQegpv7VX/wybjel02ZYa79bYXMbb2FUfSwcfLj6b6pAGMNTPgrhArr2v2EUZzZwa5Z1BfNAUI7KvQk+6VlYNO/2PfKqaSxxmDlo2C63eXTpVzndcPtuBNmEK9z9Zl1+SYDiEqjKwapPiwT6SsDr6LN4uM+QWJhV7H78KiXDB1ADfg3wf7VTWeIFphnVh/a3LFfRAq/kY88g8PcEHFaoU65nVZEe2K/M9Eis3YzX1mTqDJ8bbsOU2SnwzBv888wlNAdPJtSAuOIkWdoROxEn48YXOzUORpkk58HV1BU9POzdk0iw=";
        String RNStextures = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTE1NjE1ODQ0MSwKICAicHJvZmlsZUlkIiA6ICI0ODBkMmFiMmY1ZTk0OWQ0YWM2Mzk0NDdmNjIzYTYwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIb3RhbXBhIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzM4MzU3ZDkxM2RlZDIzNmZlZTJhNDg0ZWYzY2ZkN2ZhNzBjOThlNDRkNDk4MDVkYjA5Y2JiYzdhYjY5NDNhYzIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";
        String RNSsignature = "vXynPDMG6dgGyMzXgXKUbBR8TD280p2DntitiagD9FjO5Sey/zhtcx44eJxP0iWET0Ig7vT2YDEVOT5zbRviN7VQbhw7EQz5lJ+yOdLhmc+gudV5pYc43kUK05namKFmII2r1RUaco0sxOVf01ifX6hdrbR08QbNT8smbuC3uq2A5Y5nir+8pL3+76B0dnbwe/QMSXU5rdQ3TvCHncayMk4W6FdFIKf+iFkGYllGArd7EZYedXVvJjhZZVfi5tZELuyYJ8vCF5RuzA4eGFnvOY0YhtdIMRTjKe1TLBgSgAcsSO+te8kTRHBrBwPB1ppvs92GqNZ+TEbP7vaHdsypVnxAwGG1jHa6xr//u3xPzjw/5nfie2BYb0MLQhcE8hEB0Zvhga5oeSFkFsVq37DN3YI6GcgqNJQU96l7f0FeN0a50t8MdF7/+8HFysixshZ1OiHKSA+WbzoVXiaPfwL7F2vxJkV3Jlcr1ACGu33eigjrp2H3RJG163dSjaneREXTjeCjJFYPa1T2/JhuG1grpnvI7DVw3r/EOx67VtMcaUGBsHPNNpGpppkYzrXT4fpWIaaeNhR4hr3b5U7UETj1n+esIljvyPhmF0yKrm3CC3rqOXOWFzV5XiUupYYK2Ag9ZJiGHehKXSVqOqJpILe8uCLPNC+dSSDOjNQM47Vz3gM=";
        String TFGTEXTURES = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTMwMzkwNTUyMywKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NmNkYThlNGQ5MjhkNThlZmNiYTI4Nzc3YjBkNTg2NDgyMjE0OGMwNGJiYjY0OWExY2YwMjBmNzNhNzNiYmEwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String TFGSIGNATURES = "s6g7qp2TxGqGUh8pEO8+WrPOym0rhTmw7ewZ85eFQZuaDCaNJCENBbTZGGTcbalZn+KXGxZ3iBuWbPuGOGPd+ggyca8VdWhRugAESGyEiDN2/25SY++iTgZVB44hU1bCBQqM5108QbSeSbvHPYvkasO60pUkTa+delj5h7ligFo5iF7xh1j8WPsHnaTOXpzRqosKABaRE10cA1HB7hRrTiHVKusDxYA5PtBK/IdWCXy2uFOLR5tp17gke8vfjy3xWKB2dYu+IATVIDshq1niLRupQMUL29X2gmWXbJ65TdNaNP7JZjVvKPnvGcb6GhXu27bK1Eu6iY1ClLQThaKgm7VITzSA99dpkytqw+ttMLTY0ucJGLDBx2bZSR4+2oQq0KiSLW75Tmf0LKftxy4euC6zi1qidN/++KiFMGmeMI7bj8//vAEQKAmoBaIApAlv9/pSI/rR8DJsrAIBq4VhKd7/prSupQ4fav6PHQ+GAGRspWGIMP0h/bQLRtIMyDLkBDA5P86+1Tu50nfOwbFvk15fTdIj6+eh9D2MoqxA6Qgm7GzVBCNa0v4ScwaYDQNny26NjRlIS91xrglRYL5+g6O9oS6J6OlHsa0jkzzaM0AN9v7Pq1E9dCCMTJJvwBKKNvUSk97S3CUt6bzrmrfJ8uQCqJXLOvLua9vufcvuCz0=";


        for (int i = 0; i < npcs.length; i++) {
            npcs[i] = new NPC("", null, true).hideNametag().setAutoSpawn(true);
            npcs[i].world.add("world");
        }
        for (int i = 0; i < Lobbys.length; i++) {
            Lobbys[i] = new Hologram("", 0, 0, 0, 0, 0).setAutoSpawn(true);
            Lobbys[i].world.add("world");
        }
        Lobbys[0].setLocation(-2.5, 101.85, 19.5, 0, 0);
        Lobbys[1].setLocation(0.5, 101.85, 19.5, 0, 0);
        Lobbys[2].setLocation(3.5, 101.85, 19.5, 0, 0);
        Lobbys[3].setLocation(-2.5, 102.15, 19.5, 0, 0);
        Lobbys[4].setLocation(0.5, 102.15, 19.5, 0, 0);
        Lobbys[5].setLocation(3.5, 102.15, 19.5, 0, 0);
        Lobbys[0].setName("§bStickDuel");
        Lobbys[1].setName("§bMLGRush 2x1");
        Lobbys[2].setName("§bMLGRush 4x1");
        Lobbys[3].setName("§a0/2");
        Lobbys[4].setName("§a0/2");
        Lobbys[5].setName("§4Maintenance");
        npcs[0].setSpawnBodyYaw(180).setSkin(VEPEXtextures, VEPEXsignature).setClickEvent(((player, action) -> {
            if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                StickDuel.Queue(player);
        }));
        npcs[1].setSpawnBodyYaw(180).setSkin(TFGTEXTURES, TFGSIGNATURES).setClickEvent(((player, action) -> {
            if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                MLGRush2x1.queue(player);
        }));
        npcs[2].setSpawnBodyYaw(179).setSkin(RNStextures, RNSsignature).setClickEvent(((player, action) -> {
            if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                player.sendMessage("§cThis gamemode is under maintenance.");
        }));
        npcs[0].setLocation(-2.5, 100, 19.5, 128, 0);
        npcs[1].setLocation(0.5, 100, 19.5, 128, 0);
        npcs[2].setLocation(3.5, 100, 19.5, 128, 0);
    }
}