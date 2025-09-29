package com.example.games;

import android.content.Context; // Required for DatabaseHelper
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // For logging errors

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TermsAndDefinitions {
    int index;//same as io in Database
    String term;
    String definition;

    public static List<TermsAndDefinitions> TsAndDs = new ArrayList<>();

    public TermsAndDefinitions(int index, String term, String definition){
        this.index = index;
        this.term = term;
        this.definition = definition;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    //==============================================================================================

    public static void loadDummyTsAndDs(){
        TsAndDs.clear();
        TsAndDs.add(new TermsAndDefinitions(1, "session", "Any period devoted to an activity")); //01
        TsAndDs.add(new TermsAndDefinitions(2, "junior", "Younger in years")); //02
        TsAndDs.add(new TermsAndDefinitions(3, "arise", "To get or stand up, as from a sitting, kneeling, or lying position")); //03
        TsAndDs.add(new TermsAndDefinitions(4, "perfect", "Having all essential elements")); //04
        TsAndDs.add(new TermsAndDefinitions(5, "skeleton", "The bones of a human or an animal considered as a whole, together forming the framework of the body")); //05
        TsAndDs.add(new TermsAndDefinitions(6, "original", "The first and genuine form of something, from which others are derived")); //06
        TsAndDs.add(new TermsAndDefinitions(7, "concession", "Any grant of rights, land, or property by a government, local authority, corporation, or individual")); //07
        TsAndDs.add(new TermsAndDefinitions(8, "represent", "To stand as an equivalent of")); //08
        TsAndDs.add(new TermsAndDefinitions(9, "expansion", "The act or process of expanding")); //09
        TsAndDs.add(new TermsAndDefinitions(10, "strength", "The quality or state of being strong")); //10
        TsAndDs.add(new TermsAndDefinitions(11, "shame", "A painful emotion resulting from an awareness of having done something dishonourable, unworthy, degrading, etc")); //11
        TsAndDs.add(new TermsAndDefinitions(12, "conflict", "To come into collision or disagreement; be contradictory, at variance, or in opposition; clash")); //12
        TsAndDs.add(new TermsAndDefinitions(13, "belt", "A band of flexible material, as leather or cord, for encircling the waist")); //13
        TsAndDs.add(new TermsAndDefinitions(14, "notion", "A general understanding")); //14
        TsAndDs.add(new TermsAndDefinitions(15, "brick", "Blocks of hardened clay collectively as used for building")); //15
        TsAndDs.add(new TermsAndDefinitions(16, "store", "An establishment where merchandise is sold, usually on a retail basis")); //16
        TsAndDs.add(new TermsAndDefinitions(16, "remember", "To recall to the mind by an act or effort of memory")); //17
        TsAndDs.add(new TermsAndDefinitions(18, "adventure", "An exciting or very unusual experience")); //18
        TsAndDs.add(new TermsAndDefinitions(19, "weak", "Liable to yield, break, or collapse under pressure or strain")); //19
        TsAndDs.add(new TermsAndDefinitions(20, "ordinary", "Of no special quality or interest")); //20
        TsAndDs.add(new TermsAndDefinitions(21, "representative", "A person or thing that represents another or others")); //21
        TsAndDs.add(new TermsAndDefinitions(22, "dome", "Any covering thought to resemble the hemispherical vault of a building or room")); //22
        TsAndDs.add(new TermsAndDefinitions(23, "lemon", "The yellowish, acid fruit of a subtropical citrus tree")); //23
        TsAndDs.add(new TermsAndDefinitions(24, "member", "A person, animal, plant, group, etc., that is part of a society, party, community, taxon, or other body")); //24
        TsAndDs.add(new TermsAndDefinitions(25, "curriculum", "The aggregate of courses of study given in a school, college, university, etc")); //25
        TsAndDs.add(new TermsAndDefinitions(26, "inhabitant", "A person or animal that inhabits a place, especially as a permanent resident")); //26
        TsAndDs.add(new TermsAndDefinitions(27, "medieval", "Of, pertaining to, characteristic of, or in the style of the Middle Ages")); //27
        TsAndDs.add(new TermsAndDefinitions(28, "slide", "To move along in continuous contact with a smooth or slippery surface")); //28
        TsAndDs.add(new TermsAndDefinitions(29, "role", "The function assumed by a person or thing in a given action or process")); //29
        TsAndDs.add(new TermsAndDefinitions(30, "lazy", "Tending to avoid work, activity, or exertion")); //30
        TsAndDs.add(new TermsAndDefinitions(31, "hunter", "A person who hunts game or other wild animals for food or in sport")); //31
        TsAndDs.add(new TermsAndDefinitions(32, "executive", "A person or group of persons having administrative or supervisory authority in an organization")); //32
        TsAndDs.add(new TermsAndDefinitions(33, "credibility", "The quality of being believable or worthy of trust")); //33
        TsAndDs.add(new TermsAndDefinitions(34, "liberal", "Favoring or permitting freedom of action, especially with respect to matters of personal belief or expression")); //34
        TsAndDs.add(new TermsAndDefinitions(35, "decrease", "To diminish or lessen in extent, quantity, strength, power, etc")); //35
        TsAndDs.add(new TermsAndDefinitions(36, "loss", "A thing or a number of related things that are lost or destroyed to some extent")); //36
        TsAndDs.add(new TermsAndDefinitions(37, "story", "A narrative, either true or fictitious, in prose or verse, designed to interest, amuse, or instruct the hearer or reader")); //37
        TsAndDs.add(new TermsAndDefinitions(38, "continuation", "The act or state of continuing")); //38
        TsAndDs.add(new TermsAndDefinitions(39, "functional", "Having or serving a utilitarian purpose; capable of serving the purpose for which it was designed")); //39
        TsAndDs.add(new TermsAndDefinitions(40, "finger", "Any of the terminal members of the hand, especially one other than the thumb")); //40
        TsAndDs.add(new TermsAndDefinitions(41, "cinema", "A place designed for the exhibition of films")); //41
        TsAndDs.add(new TermsAndDefinitions(42, "us", "The objective case of we, used as a direct or indirect object")); //42
        TsAndDs.add(new TermsAndDefinitions(43, "fashionable", "Observant of or conforming to the fashion; stylish")); //43
        TsAndDs.add(new TermsAndDefinitions(44, "mechanical", "Being a machine; operated by machinery"));//44
        TsAndDs.add(new TermsAndDefinitions(45, "collect", "To gather together"));//45
    }

    static Random random = new Random();
    public static int generateRandomIndex(){//generates a random index to get a random term and associated definition
        int randomIndex = random.nextInt(TsAndDs.size());

        return randomIndex;
    }

    public static TermsAndDefinitions getTermAndDefinition(int index){//gets terms and definitions
        return TsAndDs.get(index);
    }

    //=============================Load From Database===============================================

//    public static void loadTermsAndDefinitionsfromDB(Context context){
//        List<TermsAndDefinitions> loadedList = new ArrayList<>();
//
//        DummyDatabase dbHelper = new DummyDatabase(context);
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//
//        try{
//            db = dbHelper.getReadableDatabase();
//
//            // Define a projection that specifies which columns from the database that will be used after this query.
//            String[] columns = {DummyDatabase.COLUMN_ID, DummyDatabase.COLUMN_TERM, DummyDatabase.COLUMN_DEFINITION};
//
//            cursor = db.query(
//                    DummyDatabase.TABLE_TD,  // The table to query
//                    columns,                     // The columns to return
//                    null,                        // The columns for the WHERE clause
//                    null,                        // The values for the WHERE clause
//                    null,                        // Don't group the rows
//                    null,                        // Don't filter by row groups
//                    null                         // The sort order (or null for default)
//            );
//
//            // Get column indexes once
//            int idColumnIndex = cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ID);
//            int termColumnIndex = cursor.getColumnIndexOrThrow(dbHelper.COLUMN_TERM);
//            int definitionColumnIndex = cursor.getColumnIndexOrThrow(dbHelper.COLUMN_DEFINITION);
//
//            while (cursor.moveToNext()){
//                int currentID = cursor.getInt(idColumnIndex);
//                String currentTerm = cursor.getString(termColumnIndex);
//                String currentDefinition = cursor.getString(definitionColumnIndex);
//                loadedList.add(new TermsAndDefinitions(currentID, currentTerm, currentDefinition));
//            }
//        }catch (Exception e) {
//            Log.e("Terms and conditions", "Error loading data from database", e);
//        }finally{
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
//            TsAndDs.clear();
//            TsAndDs.addAll(loadedList);
//        }
//    }

}
