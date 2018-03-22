package eu.oloeriu.hearthstone.data;


import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

@SimpleSQLConfig(
        name = "HearthstoneProvider",
        authority = "eu.oloeriu.hearthstone",
        database = "hearthstone.db",
        version = 1)
public class CardProviderConfig implements ProviderConfig{
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
