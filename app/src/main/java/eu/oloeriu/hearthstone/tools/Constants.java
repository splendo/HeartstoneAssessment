package eu.oloeriu.hearthstone.tools;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Constants {
    public static final String SHARED_PREFERENCES_KEY = "eu.oloeriu.hearthstone";
    public static final String SHARED_SET_TYPE = "shared_set_type";
    public static final String SHARED_SET_RARITIES = "shared_set_rarities";
    public static final String SHARED_SET_CLASSES = "shared_set_classes";
    public static final String SHARED_SET_MECHANICS = "shared_set_mechanics";
    public static final String SHARED_SET_CARD_SETS = "shared_set_card_sets";
    public static final String LOG_TAG = "my_log_tag";

    //firebase constants
    public static final String DEVICE_ID = "$deviceId";
    public static final String CARD_ID = "$cardId";
    public static final String DEVICES = "devices";
    public static final String CARDS = "cards";
    public static final String LOCATION_CARDS = DEVICES+"/"+DEVICE_ID+"/"+CARDS;
    public static final String LOCATION_CARD = LOCATION_CARDS + "/"+CARD_ID;

}
