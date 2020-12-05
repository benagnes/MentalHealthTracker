package com.example.MentalHealthTracker.favouriteresources;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.FavouriteResources.Favourite;
import com.example.MentalHealthTracker.FavouriteResources.FavouriteDBHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// Tests Favourite Resources database handler functions
@RunWith(AndroidJUnit4.class)
public class FavouriteResourcesDatabaseTest {
    private FavouriteDBHandler db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new FavouriteDBHandler(context, null);
    }

    @After
    public void closeDb() {
        db.closeConnection();
    }

    @Test
    public void TestFavouriteResourceDatabase() {
        String allEntries;
        int favouriteNumber = 1;
        String ResourceName = "Yoga";

        // wipe database clean at start of test
        db.deleteAllHandler();

        // check Load Handler returns empty string
        allEntries = db.loadHandler();
        assert(allEntries.equals(""));

        // verify that entry does not exist
        Favourite testNull = db.findHandler(favouriteNumber);
        assert(testNull == null);

        // test adding and finding a favourite resource
        Favourite fav1 = new Favourite(favouriteNumber, ResourceName );
        db.addHandler(fav1);
        Favourite testFavourite1 = db.findHandler(favouriteNumber);
        assert(testFavourite1 != null);
        assert(testFavourite1.getFavouriteNumber() == fav1.getFavouriteNumber());
        assert(testFavourite1.getResource().equals(fav1.getResource()));

        // test updating a favourite resource
        String updateResourceName = "Mood Tracker";
        boolean updateResult = db.updateHandler(favouriteNumber, updateResourceName);
        assert(updateResult);

        Favourite testFavourite2 = db.findHandler(favouriteNumber);
        assert(testFavourite2 != null);
        assert(testFavourite2.getFavouriteNumber() == fav1.getFavouriteNumber());
        assert(testFavourite2.getResource().equals(updateResourceName));

        // test deleting a favourite resource
        db.deleteHandler(favouriteNumber);
        Favourite testFavourite3 = db.findHandler(favouriteNumber);
        assert(testFavourite3 == null);

        // test adding multiple entries, then loading all
        Favourite addFavourite1 = new Favourite(1, "Play Music");
        Favourite addFavourite2 = new Favourite(2, "View Statistics");
        Favourite addFavourite3 = new Favourite(3, "Meditation");
        db.addHandler(addFavourite1);
        db.addHandler(addFavourite2);
        db.addHandler(addFavourite3);

        allEntries = db.loadHandler();

        assert(allEntries.contains("1"));
        assert(allEntries.contains("Play Music"));

        assert(allEntries.contains("2"));
        assert(allEntries.contains("View Statistics"));

        assert(allEntries.contains("3"));
        assert(allEntries.contains("Meditation"));

        // Finally, delete all and verify database empty
        db.deleteAllHandler();
        allEntries = db.loadHandler();
        assert(allEntries.equals(""));
    }
}
