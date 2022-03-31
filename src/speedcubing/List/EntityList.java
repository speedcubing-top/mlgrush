package speedcubing.List;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import cubing.lib.bukkit.EntityPlayerUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;

import java.util.*;

public class EntityList {
    public static EntityArmorStand[] Lobbys = new EntityArmorStand[6];
    public static Set<PacketPlayOutSpawnEntityLiving> spawnpackets = new HashSet<>();

    ScoreboardTeam NoNameTag = new ScoreboardTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), "");
    public static List<Integer> NPCS = new ArrayList<>();
    public static List<Packet> LobbyNPCPackets = new ArrayList<>();
    public static List<Packet> LobbyNPCDelayedPackets = new ArrayList<>();

    public void Load() {
        WorldServer world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        for (int i = 0; i < Lobbys.length; i++) {
            Lobbys[i] = new EntityArmorStand(world);
            Lobbys[i].setCustomNameVisible(true);
            Lobbys[i].setGravity(true);
            Lobbys[i].setInvisible(true);
            Lobbys[i].n(true);
        }
        Lobbys[0].setLocation(-2.5, 101.85, 19.5, 0, 0);
        Lobbys[1].setLocation(0.5, 101.85, 19.5, 0, 0);
        Lobbys[2].setLocation(3.5, 101.85, 19.5, 0, 0);
        Lobbys[3].setLocation(-2.5, 102.15, 19.5, 0, 0);
        Lobbys[4].setLocation(0.5, 102.15, 19.5, 0, 0);
        Lobbys[5].setLocation(3.5, 102.15, 19.5, 0, 0);
        Lobbys[0].setCustomName("§bStickDuel");
        Lobbys[1].setCustomName("§bMLGRush 2x1");
        Lobbys[2].setCustomName("§bMLGRush 4x1");
        Lobbys[3].setCustomName("§a0/2");
        Lobbys[4].setCustomName("§a0/2");
        Lobbys[5].setCustomName("§4Maintenance");
        for (EntityArmorStand lobby : Lobbys) {
            spawnpackets.add(new PacketPlayOutSpawnEntityLiving(lobby));
        }

        NoNameTag.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        Lobby();
        MLGRushNPC();
    }

    String VEPEXtextures = "ewogICJ0aW1lc3RhbXAiIDogMTYxNzE4MDQwNzAyNSwKICAicHJvZmlsZUlkIiA6ICIwMGM2Yjk0YTY5YmU0MzY3OTkwOTQxNjFjMjAxOWI3ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJLQUVWRVJZIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI1YTUzMTU2Zjk1ODhlZjNkZTQyMGU0ODA5MTYzN2Q3NGYwN2ZmM2MyMzIyNjRkM2Q0MGY3Y2RlOGQ1N2JiODMiCiAgICB9CiAgfQp9";
    String VEPEXsignature = "CY9LJ6UsQiJ4h0x2MlHVyWq08zc8bA5neVud+3Deh0eK9UcNU4eC5rk+5Wghq6Ue8KUUV8Kc+h6Ze4XuujN/OfX6tulvt2YrkLgDWh9dyoPFVhmUiIV7cyYUqHqCbNSvRDjFjMmlnmcgzLMmTYnsaArfxwk/vqnHpigkGMFa7EnfkyoFHHTs5evAPCfr2CXWTdOXJAgsGeb/o54KI159sjOdRjv4lgmdQFq+7SyzBKcGMD0cR5nB4thczXw79U9rfKc9IYFfMKtjTFV1Qxhzhnyugw9yPO5TiZ9qve59H9wf6THFgA7m8QWwVqjrQEIBTE7MSUDUS9jSU/sVPnYKz6F3z+pIQSPSPykh0AHW5T1fP5IerzVgo4ddBuAAQegpv7VX/wybjel02ZYa79bYXMbb2FUfSwcfLj6b6pAGMNTPgrhArr2v2EUZzZwa5Z1BfNAUI7KvQk+6VlYNO/2PfKqaSxxmDlo2C63eXTpVzndcPtuBNmEK9z9Zl1+SYDiEqjKwapPiwT6SsDr6LN4uM+QWJhV7H78KiXDB1ADfg3wf7VTWeIFphnVh/a3LFfRAq/kY88g8PcEHFaoU65nVZEe2K/M9Eis3YzX1mTqDJ8bbsOU2SnwzBv888wlNAdPJtSAuOIkWdoROxEn48YXOzUORpkk58HV1BU9POzdk0iw=";
    String Tellytextures = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTE1NjEwMDUyOSwKICAicHJvZmlsZUlkIiA6ICJkODAwZDI4MDlmNTE0ZjkxODk4YTU4MWYzODE0Yzc5OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0aGVCTFJ4eCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yOWIxNWViYTJhZjkxOTAyYzExNDI1OTU3NTU1NGIwYWJiNDE2NWI1ODg5NTBiM2ViZjljYjJmZWM5Mzg2ZGMwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
    String Tellysignature = "cPq0/1JisEsQc6ZJ+P68fpUYQu6TTlsYGs3Q6knt3yhUWq26V/ncv/GFLf05VqDP6vi/JKWX3LaYTz9Z/0Kt5vap1UEFD9ierMxmPn/Pag2676mTpcMNPfqvkurzcn8JVw70dgKz49LebqPENX3ZJAEGfk1kalryqjUeZtP2uNXD7C43KWdISoYql3e6N85jHWl2TFVtRWMb67DPPjkQCTfcz3cXqBIueRc20o68lx+05pTpljjRc+SFChO8qW71E9fvQPMC0xEVFLdOKpElOPLhQH1UNPiaghwNPW5HZ0gCPU6hYbSUUaZk2RhjsN6nN6qcTp1oMbrNT/xgjYyuCcKzUUvu296UiXR6QCl+BmroAN6ljT8CJHOlvKsnP72en6kgCo5HMp1AbIclZZa7ZSbRfmSqWtIGK27Fw4As/yAl9TamqyLY9Nqsr1shK6/n2O67VaH2gDI6owZynYsLxNDre5zCjIIR7PDqu9OA1Nh4rIHzLd59LTK5+BsfQxXAZt8HpLA4LTXuVw+dvj1FXS3pSqUGutqU5q6Js7uaccI7c3iSrsxZwUsiOFs7asfhy26PbivK6PirjecqN9G5FDDawkAemMLghVRpyeGaTiQ3+Wv/H0nyC1yriZ8NWCwEH0E5yufDE/lPJg1D/asBZBMOripKSeXqbudqok695WM=";
    String RNStextures = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTE1NjE1ODQ0MSwKICAicHJvZmlsZUlkIiA6ICI0ODBkMmFiMmY1ZTk0OWQ0YWM2Mzk0NDdmNjIzYTYwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIb3RhbXBhIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzM4MzU3ZDkxM2RlZDIzNmZlZTJhNDg0ZWYzY2ZkN2ZhNzBjOThlNDRkNDk4MDVkYjA5Y2JiYzdhYjY5NDNhYzIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";
    String RNSsignature = "vXynPDMG6dgGyMzXgXKUbBR8TD280p2DntitiagD9FjO5Sey/zhtcx44eJxP0iWET0Ig7vT2YDEVOT5zbRviN7VQbhw7EQz5lJ+yOdLhmc+gudV5pYc43kUK05namKFmII2r1RUaco0sxOVf01ifX6hdrbR08QbNT8smbuC3uq2A5Y5nir+8pL3+76B0dnbwe/QMSXU5rdQ3TvCHncayMk4W6FdFIKf+iFkGYllGArd7EZYedXVvJjhZZVfi5tZELuyYJ8vCF5RuzA4eGFnvOY0YhtdIMRTjKe1TLBgSgAcsSO+te8kTRHBrBwPB1ppvs92GqNZ+TEbP7vaHdsypVnxAwGG1jHa6xr//u3xPzjw/5nfie2BYb0MLQhcE8hEB0Zvhga5oeSFkFsVq37DN3YI6GcgqNJQU96l7f0FeN0a50t8MdF7/+8HFysixshZ1OiHKSA+WbzoVXiaPfwL7F2vxJkV3Jlcr1ACGu33eigjrp2H3RJG163dSjaneREXTjeCjJFYPa1T2/JhuG1grpnvI7DVw3r/EOx67VtMcaUGBsHPNNpGpppkYzrXT4fpWIaaeNhR4hr3b5U7UETj1n+esIljvyPhmF0yKrm3CC3rqOXOWFzV5XiUupYYK2Ag9ZJiGHehKXSVqOqJpILe8uCLPNC+dSSDOjNQM47Vz3gM=";
    String Lebtextures = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTAwMzEwMzY0MCwKICAicHJvZmlsZUlkIiA6ICIzZmM3ZmRmOTM5NjM0YzQxOTExOTliYTNmN2NjM2ZlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJZZWxlaGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZjZGYyMGVlOGRlM2Q1MWYyNzkwYmVkNTA4MDE0NWQ0N2NmOTA1NWUwYzlhZDM2ZDQ3MTkxNmE5ZWViODM3ZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    String Lebsignature = "bJD75g2AQ+C2ViRtEoE4qa4Kcp0uujUdSxb/o2D+e+4inKbmAt5dUqAGKRfm2l87UrvtOmhz2mfr++XN1eztqFJMhrLa6jJ8x6PI+4ULCkVJy9adypNpyzCALqA+u6/dR13m9mIzu2h5iUTRSfOS2rbYV+gYsvZxqYMnDrNkR7qETP19TN2aRb5o0dDoTaKxXkV8z6hnHPImlSsycqepMVBZ5XGzqxi4mN+YDwfWY7D4e46Je9AAJ+wYC7E9WltsxY3JsnMx2b/cx+NdLrYt61Xl7/SABnoRZkcAu1m37O2pIPnq3hRlZ5orwUFe9xTTrFo6AZgmJAc4v2/1NEw8T4teSwGxuirimvOKqfpt0F0F6Q/cQkD3KcJOClpXFyRJNFSnjeCoOwDevQcxd7qqeD8OC/7u3hcD+hWyVoKzzCMdLRChzz4ZR/MAeIEbv388y1Sp9+i7yQ6lPfe2TIJzkbFp6Hq3dwtEKqwOGvRK0BKgqd4kiYghkgZlbhosFSNi4fGRuSCg1zf/yNBUo5qD4e6jWk3pks/WFqOkmRk98ZDH0tuL8wRGVBd4Cm2vm70UjKuOlvH6BBX3EvNxBLuR5vwV8XqIyBjNN+oUqaFBnGJgZabPYPxUjNo3VAjvmajKTDg11L6y/afdv8NXvPDCzge7Z4c6G8M9MZOoGo9/XEY=";
    String Difarktextures = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTMwMzM2NjU3MiwKICAicHJvZmlsZUlkIiA6ICI2MTZiODhkNDMwNzM0ZTM3OWM3NDc1ODdlZTJkNzlmZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJfX25vdGFodW1hbl9fIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FkMDljM2UwNDM1MGE5YTMzNzEzNDNkZGZlZGE2ZTNjMDM3ZmRjZTk3YmIwYzU3NmJkZmE5ODA5MDk4N2IyNzkiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";
    String Difarksignatures = "aWMvZ3g1PsY9d0mNqQ/stVkUXfJVhAfRprjsUBfF/1VB+D20z/Low3UIOtNa+62B0n6Tx+3n23whvCr+RmL0awgQDjQOOXGKlrNRUSrZHFL286GluCFRK2iKT4ewIAMDnpbMoOPRLtfbAxT7OFqVGeTcjlQ8p11DlLA1WronxSZQJDF9s1oTm0PCt7Pr8CIFV6LL7hBfa+Z1DOM7pLrbzwraSX4JufXpf6sxMx1Jwo2Y1vo61OU7z/Y7a2yb4bN2bkwrqekJbEr/iZ4hgogJJw0mb6CbmVpUP0gUP4VIrXlZrIx0B/28Rp1CjZ9S/KCUu/TpkLeOwN4MaXtQLxakZ9bN0K5LQJHDl5y3dJDenwLjZuZ98EoFMsbXvHDLsYoomnbDe/y7EO5LiCOL5Dk/SvigbJC/wsnjs9qQvAZ5xLrBRIXIQdocPiix5fFVSb98qH77GCIFVPlmQnwZ3sErtnScOfOSmgUayj8QERDuT44Q+pkcLcoXbCDlK77aqebGgq3J+v2n00AUstIh1Fsr16QouMlR+W8BIJVfXuDdtGsgbGYzBoEYveW5DDhRrgVxXi1mkjDcnUotrqDZgqnSidmtILEmdTzzTv2Zyl0wAzMnzoHDTlvzdh0di5g62JCzkdCaD0M9GqlvOtd6wBpcI7Vn2i5GCm0FqUQ3i6w8nQk=";
    String HollyBitetextures = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTMwMzU4MTUwMywKICAicHJvZmlsZUlkIiA6ICIyNDBiYThlMzNlN2M0YzE0ODhiNzJmYmU1Njg2ZjhlNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJQbGF4Q3JhZnRzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk4MGIxZjM4MGRmNjk1Y2JmMTJiNTdjODA4MmQyNmZmYTkyZTEzNWQ5NzFiNWJkZWU3NGQ0Y2EzNGYwZmEzZTkiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";
    String HollyBitesignatures = "rBhuT2mkNB660m1Om3LM3Ue02+SF/1g8Re38psPKNTlzNX6jMXUEnWxXvvbQ9NdBl79xxIQ4DweGGc44aZWJc1jnliZd/NYp87WZiaQ8pEAu1pvzoFAmzD7EpMXefJ0pPfHJYA0hqdm3hEZ4zQqpHtj2feSxfid2saOEL8pMl9pjrDaIs/pxjtHwLbhf3WK6Vo+Sb9XIiJB9wY4TpN1VCqTfhaQrdWzZ+c0BMubxPua9l8lBYPbe3nvuZBjNWL+yaQ9VT2i19mILf2/AVh/ViGx2L4DDfs/Xu35exvCJN9cdpZqJSAuWMdwLIcvVC/IPWol4q7ZMh8Qd9K7+2xAJIuA2j/SQdiBJEljDababQ5BP4qypXbT/j8EEHZn3JZuPi5bkgi1gsrUY1r6EsXSGvIf67qib1hOWX1QnE7DUOxypk6/TdLttNjEMhjdJLVmjrQctC3nmQnSr/JPs6tb1vH4nvhKaBklrkipkepjKVtj59ILBfvQ9iISySC/UgeQWwaSkNlN4dIVEFq+S/tqZv2XmzUnDRs1Si0oZD6ABnbrZaBbjMWDSHW5Lc01UCBt8naEY6BtMIEcs0qnnfFProVaYvPZ67ZO+NrUSvL7DNNEG0+Twu/WpB6rnmmenxtOG89ZltQAxs2USSLTqeSdAtCRLAUu56Q47z5lTKNx+vjo=";
    String TFGTEXTURES = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTMwMzkwNTUyMywKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NmNkYThlNGQ5MjhkNThlZmNiYTI4Nzc3YjBkNTg2NDgyMjE0OGMwNGJiYjY0OWExY2YwMjBmNzNhNzNiYmEwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
    String TFGSIGNATURES = "s6g7qp2TxGqGUh8pEO8+WrPOym0rhTmw7ewZ85eFQZuaDCaNJCENBbTZGGTcbalZn+KXGxZ3iBuWbPuGOGPd+ggyca8VdWhRugAESGyEiDN2/25SY++iTgZVB44hU1bCBQqM5108QbSeSbvHPYvkasO60pUkTa+delj5h7ligFo5iF7xh1j8WPsHnaTOXpzRqosKABaRE10cA1HB7hRrTiHVKusDxYA5PtBK/IdWCXy2uFOLR5tp17gke8vfjy3xWKB2dYu+IATVIDshq1niLRupQMUL29X2gmWXbJ65TdNaNP7JZjVvKPnvGcb6GhXu27bK1Eu6iY1ClLQThaKgm7VITzSA99dpkytqw+ttMLTY0ucJGLDBx2bZSR4+2oQq0KiSLW75Tmf0LKftxy4euC6zi1qidN/++KiFMGmeMI7bj8//vAEQKAmoBaIApAlv9/pSI/rR8DJsrAIBq4VhKd7/prSupQ4fav6PHQ+GAGRspWGIMP0h/bQLRtIMyDLkBDA5P86+1Tu50nfOwbFvk15fTdIj6+eh9D2MoqxA6Qgm7GzVBCNa0v4ScwaYDQNny26NjRlIS91xrglRYL5+g6O9oS6J6OlHsa0jkzzaM0AN9v7Pq1E9dCCMTJJvwBKKNvUSk97S3CUt6bzrmrfJ8uQCqJXLOvLua9vufcvuCz0=";
    String BLENDERTEXTURES = "ewogICJ0aW1lc3RhbXAiIDogMTYyNTc1MTc5MDg0NiwKICAicHJvZmlsZUlkIiA6ICIyNTUyNzc0YTEzNjQ0NDA3YjdlM2QyZjY2ZDkzNjA1YyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzdHVybWxpZWJlbmRlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81MmY4MzJlZDFiMGY5NWNkOWYzZTFmOWI2MGFlMmM4ZGY4Y2FiMDU0YjVkYTRjNjQ5NWFjNDI4ZWYwNDQ5YmQ1IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
    String BLENDERSIGNATURES = "axssK2gBfcvBt6M7wEDf6p5mJiUIqiXU0BeCnW1anmYzd4jicwmmO8E6930APPdECbYNmfl/n+Cy4H2scTBReRaCgH9T5ou3OOJz5XvUuWAA/MlM9vl1sA/PUOeifcKOccoOpY8m1k77tvztcYEKCiIVVLz8viiRavF61lOK5easaeofRL5fXjR1ukodJDo23cEiBQ5OXI5QxgsMjb/oYYqOhRC9muxhTcddRMuc/XV2Gk79cPW5njI6rdKa5I3clkXaayuzBwqUhnzlwefV+x6AZx14yUEmFdyKcyXfBqdbmmQadr5FW+yAKTlvccmOWd0i+hZYjJCV3aRm0Hb2pGqiynhoYcsJAbIUU8t7cXP2tpwfYQeeyEDQ6kNc9ReTyw06qxkK8pOiMzgYiz/Gbc/NF8cZ0VyBpLcmZZNwC/2nFGvYDIUb48W6OldpLaCHsQDOrde+T3C1347G7c3cT7yih0TxVq617w7OV5SsbcW+QSCA/6AoEhQGTEdx+j/eL8TdTUvdxbSESQJ8kldq8RCINxKEPZ1qmU2oVUWUwEFJn9+doN37MluVFdLeUA90ZiTP6uejJQ8Xbh0TounWLQqAff7a5qP5a3IhWb8w5Noz5rGRpsL7Dc/h6HNYT84vJGFbE4wdklyclIM2L63N4Izss4hI6CM4TIu5PzzHR9s=";
    String CAKETEXTURES = "ewogICJ0aW1lc3RhbXAiIDogMTYyNjQwNjg4NDc3MSwKICAicHJvZmlsZUlkIiA6ICIxNTM0ZDZkZjBmNGI0MjhmOGZhMjY1MmMwYTQ4MmI4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfQ2FrZV9wcm9ncmVzU18iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRlZjY0ZDZkOGRmNmI3OTZjNTNlZGZlZmEzYjhhMjU0ODMxMDE4ZjFlMTI0ZTQwY2E1YjIxN2EwOWJhYzQ3YiIKICAgIH0KICB9Cn0=";
    String CAKESIGNATURES = "yZxo8GrTQIewN8XkYlXpz1pj4I/reF0HmD6HOwNHmEmpvAFHLybVfrm8/mHdLO4Tr8aNmwtOiw3mmyEVrUW5vsgDA58fofJN76v59j69CZPx8rGzPNtmD4rOidNr80vRNBsFnjubh6waW6CUppMl3qrsvmTqOmHKiVPTJIQIRq9KJhmiF7dtIjuIobtWFEYHNrUp8KwzGFntyOvd//nadsyW8Zfuq3/ODLdWPAXINaL9YxKpXrU9SRQ9S1eECwzNFjzeNLy8zNYV0l/uPlKN3N2sYMaHQ9Okg8EJKA/gZZh6P2HPRsSW431X4pfIeJU/HK1sg8/l01JFUglq8EALcjfE8pxe2C1sp52BzXY6yVATn0pIh4r/qLWJiG3r3nOEz/hOT/xb0qgiV8H00MREt99c08MY/1k9zx1BRzg7bvBs0LNu4OzF9rVAWJi4/oRBXlsg48WHwxw33DTKgCYj/Z43Ehxrc4/EuOp9Hwo7lTLvJLGxtHDCo8Xe2Q880EbYie2bVQFBUBNR0rMi5L0ct/L4MZUNQGtvj3NfL1uu8qWpoXdUdpHndUbjWtt00T89TYsP3zI4YjLkJl22DADgUX2NFqnSbZFmzp0nRylKW4jcop+M6BhyucIquIvI0hudBB/hepJtctthPZade6kUb832FwSdzZT2OTRwgPeT+Ro=";


    private void MLGRushNPC() {
        WorldServer world = ((CraftWorld) Bukkit.getWorld("MLGRush2x1")).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "§bMLGRush");
        EntityPlayer entityPlayer = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), world, profile, new PlayerInteractManager(world));
        EntityPlayer entityPlayer1 = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), world, profile, new PlayerInteractManager(world));
        profile.getProperties().put("textures", new Property("textures", VEPEXtextures, VEPEXsignature));
        EntityPlayerUtils.enableOuterLayer(entityPlayer.getDataWatcher());
        EntityPlayerUtils.enableOuterLayer(entityPlayer1.getDataWatcher());
    }

    private void Lobby() {
        WorldServer world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        EntityPlayer[] entityPlayer = new EntityPlayer[3];
        for (int i = 0; i < entityPlayer.length; i++) {
            entityPlayer[i] = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), world, new GameProfile(UUID.randomUUID(), " "), new PlayerInteractManager(world));
            EntityPlayerUtils.enableOuterLayer(entityPlayer[i].getDataWatcher());
        }
        entityPlayer[0].getProfile().getProperties().put("textures", new Property("textures", VEPEXtextures, VEPEXsignature));
        entityPlayer[1].getProfile().getProperties().put("textures", new Property("textures", TFGTEXTURES, TFGSIGNATURES));
        entityPlayer[2].getProfile().getProperties().put("textures", new Property("textures", RNStextures, RNSsignature));
        entityPlayer[0].setLocation(-2.5, 100, 19.5, 180, 0);
        entityPlayer[1].setLocation(0.5, 100, 19.5, 180, 0);
        entityPlayer[2].setLocation(3.5, 100, 19.5, 179, 0);
        for (EntityPlayer value : entityPlayer) {
            LobbyNPCPackets.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, value));
            LobbyNPCPackets.add(new PacketPlayOutNamedEntitySpawn(value));
        }
        LobbyNPCPackets.add(new PacketPlayOutEntityHeadRotation(entityPlayer[0], (byte) 128));
        LobbyNPCPackets.add(new PacketPlayOutEntityHeadRotation(entityPlayer[1], (byte) 128));
        LobbyNPCPackets.add(new PacketPlayOutEntityHeadRotation(entityPlayer[2], (byte) 128));
        LobbyNPCPackets.add(new PacketPlayOutScoreboardTeam(NoNameTag, 1));
        LobbyNPCPackets.add(new PacketPlayOutScoreboardTeam(NoNameTag, 0));
        LobbyNPCPackets.add(new PacketPlayOutScoreboardTeam(NoNameTag, Collections.singletonList(" "), 3));
        for (EntityPlayer player : entityPlayer) {
            LobbyNPCDelayedPackets.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, player));
            NPCS.add(player.getId());
        }
    }
}