package com.krayem.hearthstone.utils

//todo
const val BASE_URL: String = "https://google.com/"

const val CARDSET_INTENT_EXTRA = "cardset.intent.extra"
const val FAVOURITES_INTENT_EXTRA = "favourites.intent.extra"



const val JSON_TEST = "[{\n" +
        "   \"cardId\": \"CFM_902\",\n" +
        "   \"name\": \"Aya Blackpaw\",\n" +
        "   \"cardSet\": \"Mean Streets of Gadgetzan\",\n" +
        "   \"type\": \"Minion\",\n" +
        "   \"rarity\": \"Legendary\",\n" +
        "   \"cost\": 6,\n" +
        "   \"attack\": 5,\n" +
        "   \"health\": 3,\n" +
        "   \"text\": \" <b>Battlecry and Deathrattle:</b> Summon a <b>Jade Golem</b>.\",\n" +
        "   \"flavor\": \"Though young, Aya took over as the leader of Jade Lotus through her charisma and strategic acumen when her predecessor was accidentally crushed by a jade golem.\",\n" +
        "   \"artist\": \"Glenn Rane\",\n" +
        "   \"collectible\": true,\n" +
        "   \"elite\": true,\n" +
        "   \"playerClass\": \"Neutral\",\n" +
        "   \"multiClassGroup\": \"Jade Lotus\",\n" +
        "   \"classes\": [\n" +
        "     \"Druid\",\n" +
        "     \"Rogue\",\n" +
        "     \"Shaman\"\n" +
        "   ],\n" +
        "   \"img\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/CFM_902.png\",\n" +
        "   \"imgGold\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/CFM_902_premium.gif\",\n" +
        "   \"locale\": \"enUS\",\n" +
        "   \"mechanics\": [\n" +
        "     {\n" +
        "       \"name\": \"Jade Golem\"\n" +
        "     },\n" +
        "     {\n" +
        "       \"name\": \"Battlecry\"\n" +
        "     },\n" +
        "     {\n" +
        "       \"name\": \"Deathrattle\"\n" +
        "     }\n" +
        "   ]\n" +
        "}, {\n" +
        "\t\t\"cardId\": \"KAR_A02_13H\",\n" +
        "\t\t\"name\": \"Be Our Guest\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"<b>Hero Power</b>\\\\nSummon two 1/1 Plates.\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KAR_A02_13H.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KAR_A02_13H_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\",\n" +
        "\t\t\"mechanics\": [{\n" +
        "\t\t\t\"name\": \"AIMustPlay\"\n" +
        "\t\t}]\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_00_11\",\n" +
        "\t\t\"name\": \"Evocation\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Spell\",\n" +
        "\t\t\"faction\": \"Neutral\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"Gain 5 Mana Crystals this turn only.\",\n" +
        "\t\t\"artist\": \"Mauricio Herrera\",\n" +
        "\t\t\"playerClass\": \"Mage\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_00_11.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_00_11_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_12_02H\",\n" +
        "\t\t\"name\": \"Ley Lines\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"[x]<b>Passive Hero Power</b>\\\\nBoth players have\\\\n<b>Spell Damage +5</b>.\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_12_02H.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_12_02H_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_12_02\",\n" +
        "\t\t\"name\": \"Ley Lines\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"[x]<b>Passive Hero Power</b>\\\\nBoth players have\\\\n<b>Spell Damage +3</b>.\",\n" +
        "\t\t\"artist\": \"Slawomir Maniak\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_12_02.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_12_02_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_05_01hpheroic\",\n" +
        "\t\t\"name\": \"Trembling\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"<b>Passive Hero Power</b> Minions cost (1). Enemy minions are 1/1.\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_05_01hpheroic.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_05_01hpheroic_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_05_01hp\",\n" +
        "\t\t\"name\": \"Trembling\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"<b>Passive Hero Power</b> Enemy minions are 1/1 and cost (1).\",\n" +
        "\t\t\"artist\": \"Kevin Chen\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_05_01hp.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_05_01hp_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_06_03hpheroic\",\n" +
        "\t\t\"name\": \"True Love\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"<b>Hero Power</b>\\\\nIf you don't have Romulo, summon him.\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_06_03hpheroic.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_06_03hpheroic_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_04_02hp\",\n" +
        "\t\t\"name\": \"Twister\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 0,\n" +
        "\t\t\"text\": \"<b>Hero Power</b>\\\\nDeal 100 damage. Can't be used if Dorothee is alive.\",\n" +
        "\t\t\"artist\": \"Rafael Zanchetin\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_04_02hp.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_04_02hp_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_036\",\n" +
        "\t\t\"name\": \"Arcane Anomaly\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"faction\": \"Neutral\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 2,\n" +
        "\t\t\"health\": 1,\n" +
        "\t\t\"text\": \"Whenever you cast a spell, give this minion\\\\n+1 Health.\",\n" +
        "\t\t\"flavor\": \"He used to get work as a Spatial Anomaly, but he got tired of having his polarity reversed.\",\n" +
        "\t\t\"artist\": \"Alex Aleksandrov\",\n" +
        "\t\t\"collectible\": true,\n" +
        "\t\t\"race\": \"Elemental\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"howToGet\": \"Unlocked in the Parlor, in One Night in Karazhan.\",\n" +
        "\t\t\"howToGetGold\": \"Crafting unlocked in the Parlor, in One Night in Karazhan.\",\n" +
        "\t\t\"img\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/KAR_036.png\",\n" +
        "\t\t\"imgGold\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/KAR_036_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_00_07\",\n" +
        "\t\t\"name\": \"Astral Portal\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Spell\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"text\": \"Summon a random <b>Legendary</b> minion.\",\n" +
        "\t\t\"playerClass\": \"Mage\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_00_07.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_00_07_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_009\",\n" +
        "\t\t\"name\": \"Babbling Book\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"rarity\": \"Rare\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 1,\n" +
        "\t\t\"health\": 1,\n" +
        "\t\t\"text\": \"<b>Battlecry:</b> Add a random Mage spell to your hand.\",\n" +
        "\t\t\"flavor\": \"His idol is the Green Hills of Stranglethorn, and he won't shut up about it.\",\n" +
        "\t\t\"artist\": \"A.J. Nazzaro\",\n" +
        "\t\t\"collectible\": true,\n" +
        "\t\t\"playerClass\": \"Mage\",\n" +
        "\t\t\"howToGet\": \"Unlocked in the Menagerie, in One Night in Karazhan.\",\n" +
        "\t\t\"howToGetGold\": \"Crafting unlocked in the Menagerie, in One Night in Karazhan.\",\n" +
        "\t\t\"img\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/KAR_009.png\",\n" +
        "\t\t\"imgGold\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/KAR_009_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\",\n" +
        "\t\t\"mechanics\": [{\n" +
        "\t\t\t\"name\": \"Battlecry\"\n" +
        "\t\t}]\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_A10_01\",\n" +
        "\t\t\"name\": \"Black Pawn\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 1,\n" +
        "\t\t\"health\": 6,\n" +
        "\t\t\"text\": \"<b>Auto-Attack:</b> Deal 1 damage to the enemies opposite this minion.\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KAR_A10_01.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KAR_A10_01_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KARA_08_06\",\n" +
        "\t\t\"name\": \"Blue Portal\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 0,\n" +
        "\t\t\"health\": 1,\n" +
        "\t\t\"text\": \"The character in the blue beam only takes 1 damage at a time.\",\n" +
        "\t\t\"artist\": \"Garrett Hanna\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KARA_08_06.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KARA_08_06_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_025a\",\n" +
        "\t\t\"name\": \"Candle\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 1,\n" +
        "\t\t\"health\": 1,\n" +
        "\t\t\"artist\": \"Matt O'Connor\",\n" +
        "\t\t\"playerClass\": \"Warlock\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KAR_025a.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KAR_025a_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_A10_22H\",\n" +
        "\t\t\"name\": \"Castle\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Hero Power\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"text\": \"<b>Hero Power</b>\\\\nMove a friendly minion left. Repeatable.\",\n" +
        "\t\t\"artist\": \"Anton Zemskov\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KAR_A10_22H.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KAR_A10_22H_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}," +
        " {\n" +
        "\t\t\"cardId\": \"KAR_A02_02\",\n" +
        "\t\t\"name\": \"Spoon\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Weapon\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 1,\n" +
        "\t\t\"durability\": 3,\n" +
        "\t\t\"artist\": \"G.Tsai & K. Turovec\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KAR_A02_02.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KAR_A02_02_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_044a\",\n" +
        "\t\t\"name\": \"Steward\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"faction\": \"Neutral\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 1,\n" +
        "\t\t\"health\": 1,\n" +
        "\t\t\"artist\": \"Max Grecke\",\n" +
        "\t\t\"playerClass\": \"Neutral\",\n" +
        "\t\t\"img\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/original/KAR_044a.png\",\n" +
        "\t\t\"imgGold\": \"http://wow.zamimg.com/images/hearthstone/cards/enus/animated/KAR_044a_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\"\n" +
        "\t}, {\n" +
        "\t\t\"cardId\": \"KAR_069\",\n" +
        "\t\t\"name\": \"Swashburglar\",\n" +
        "\t\t\"cardSet\": \"One Night in Karazhan\",\n" +
        "\t\t\"type\": \"Minion\",\n" +
        "\t\t\"faction\": \"Neutral\",\n" +
        "\t\t\"rarity\": \"Common\",\n" +
        "\t\t\"cost\": 1,\n" +
        "\t\t\"attack\": 1,\n" +
        "\t\t\"health\": 1,\n" +
        "\t\t\"text\": \"<b>Battlecry:</b> Add a random class card to your hand <i>(from your opponent's class).</i>\",\n" +
        "\t\t\"flavor\": \"Was almost named \\\"Swashb-AAAARRHHH-gler\\\"\",\n" +
        "\t\t\"artist\": \"Zoltan Boros\",\n" +
        "\t\t\"collectible\": true,\n" +
        "\t\t\"race\": \"Pirate\",\n" +
        "\t\t\"playerClass\": \"Rogue\",\n" +
        "\t\t\"howToGet\": \"Unlocked in the Opera, in One Night in Karazhan.\",\n" +
        "\t\t\"howToGetGold\": \"Crafting unlocked in the Opera, in One Night in Karazhan.\",\n" +
        "\t\t\"img\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/KAR_069.png\",\n" +
        "\t\t\"imgGold\": \"http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/KAR_069_premium.gif\",\n" +
        "\t\t\"locale\": \"enUS\",\n" +
        "\t\t\"mechanics\": [{\n" +
        "\t\t\t\"name\": \"Battlecry\"\n" +
        "\t\t}]\n" +
        "\t}]"