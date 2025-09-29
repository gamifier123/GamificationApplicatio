package com.example.games;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DummyDatabase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "games.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TD = "table_TD";
    public static final String COLUMN_ID = "td_id"; // Standard practice for primary key
    public static final String COLUMN_TERM = "term";
    public static final String COLUMN_DEFINITION = "definition";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TD + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Use INTEGER PRIMARY KEY for auto-increment
                    COLUMN_TERM + " TEXT, " +
                    COLUMN_DEFINITION + " TEXT" +
                    ");";

    public DummyDatabase(Context context) {//database helper function
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        String addData = "INSERT INTO " + TABLE_TD + " (" + COLUMN_TERM + ", " + COLUMN_DEFINITION + ") VALUES " +
                "(" + "\"session\", " + "\"Any period devoted to an activity\")," +
                "(" + "\"junior\", " + "\"Younger in years\")," + 
                "(" + "\"arise\", " + "\"To get or stand up, as from a sitting, kneeling, or lying position\")," +
                "(" + "\"perfect\", " + "\"Having all essential elements\")," +
                "(" + "\"skeleton\", " + "\"The bones of a human or an animal considered as a whole, together forming the framework of the body\")," +
                "(" + "\"original\", " + "\"The first and genuine form of something, from which others are derived\")," +
                "(" + "\"concession\", " + "\"Any grant of rights, land, or property by a government, local authority, corporation, or individual\")," +
                "(" + "\"represent\", " + "\"To stand as an equivalent of\")," +
                "(" + "\"expansion\", " + "\"The act or process of expanding\")," +
                "(" + "\"strength\", " + "\"The quality or state of being strong\")," +
                "(" + "\"shame\", " + "\"A painful emotion resulting from an awareness of having done something dishonourable, unworthy, degrading, etc\")," +
                "(" + "\"conflict\", " + "\"To come into collision or disagreement; be contradictory, at variance, or in opposition; clash\")," +
                "(" + "\"belt\", " + "\"A band of flexible material, as leather or cord, for encircling the waist\")," +
                "(" + "\"notion\", " + "\"A general understanding\")," +
                "(" + "\"brick\", " + "\"Blocks of hardened clay collectively as used for building\")," +
                "(" + "\"store\", " + "\"An establishment where merchandise is sold, usually on a retail basis\")," +
                "(" + "\"remember\", " + "\"To recall to the mind by an act or effort of memory\")," +
                "(" + "\"adventure\", " + "\"An exciting or very unusual experience\")," +
                "(" + "\"weak\", " + "\"Liable to yield, break, or collapse under pressure or strain\")," +
                "(" + "\"ordinary\", " + "\"Of no special quality or interest\")," +
                "(" + "\"representative\", " + "\"A person or thing that represents another or others\")," +
                "(" + "\"dome\", " + "\"Any covering thought to resemble the hemispherical vault of a building or room\")," +
                "(" + "\"lemon\", " + "\"The yellowish, acid fruit of a subtropical citrus tree\")," +
                "(" + "\"member\", " + "\"A person, animal, plant, group, etc., that is part of a society, party, community, taxon, or other body\")," +
                "(" + "\"curriculum\", " + "\"The aggregate of courses of study given in a school, college, university, etc\")," +
                "(" + "\"inhabitant\", " + "\"A person or animal that inhabits a place, especially as a permanent resident\")," +
                "(" + "\"medieval\", " + "\"Of, pertaining to, characteristic of, or in the style of the Middle Ages\")," +
                "(" + "\"slide\", " + "\"To move along in continuous contact with a smooth or slippery surface\")," +
                "(" + "\"role\", " + "\"The function assumed by a person or thing in a given action or process\")," +
                "(" + "\"lazy\", " + "\"Tending to avoid work, activity, or exertion\")," +
                "(" + "\"hunter\", " + "\"A person who hunts game or other wild animals for food or in sport\")," +
                "(" + "\"executive\", " + "\"A person or group of persons having administrative or supervisory authority in an organization\")," +
                "(" + "\"credibility\", " + "\"The quality of being believable or worthy of trust\")," +
                "(" + "\"liberal\", " + "\"Favoring or permitting freedom of action, especially with respect to matters of personal belief or expression\")," +
                "(" + "\"decrease\", " + "\"To diminish or lessen in extent, quantity, strength, power, etc\")," +
                "(" + "\"loss\", " + "\"A thing or a number of related things that are lost or destroyed to some extent\")," +
                "(" + "\"story\", " + "\"A narrative, either true or fictitious, in prose or verse, designed to interest, amuse, or instruct the hearer or reader\")," +
                "(" + "\"continuation\", " + "\"The act or state of continuing\")," +
                "(" + "\"functional\", " + "\"Having or serving a utilitarian purpose; capable of serving the purpose for which it was designed\")," +
                "(" + "\"finger\", " + "\"Any of the terminal members of the hand, especially one other than the thumb\")," +
                "(" + "\"cinema\", " + "\"A place designed for the exhibition of films\")," +
                "(" + "\"us\", " + "\"The objective case of we, used as a direct or indirect object\")," +
                "(" + "\"fashionable\", " + "\"Observant of or conforming to the fashion; stylish\")," +
                "(" + "\"mechanical\", " + "\"Being a machine; operated by machinery\")," +
                "("+ "\"collect\", " + "\"To gather together\")";
        db.execSQL(addData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TD);
        onCreate(db);
    }
}
