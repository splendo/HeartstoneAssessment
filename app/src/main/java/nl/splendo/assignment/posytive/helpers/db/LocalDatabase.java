package nl.splendo.assignment.posytive.helpers.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * The class that defines the local SQlite DB (managed by DBFlow lib).
 */
@Database(name = LocalDatabase.NAME, version = LocalDatabase.VERSION)
public class LocalDatabase {

    public static final String NAME = "SplenDb";

    public static final int VERSION = 1;
}
