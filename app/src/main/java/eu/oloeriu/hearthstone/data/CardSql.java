package eu.oloeriu.hearthstone.data;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

@SimpleSQLTable(table="card",provider = "HearthstoneProvider")

public class CardSql {

    @SimpleSQLColumn(value="_id", primary = true)
    public long id;

    @SimpleSQLColumn("guestProfile")
    public String name;
}
