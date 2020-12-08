package com.example.MentalHealthTracker.favourites;

import com.example.MentalHealthTracker.FavouriteResources.ResourceToString;
import com.example.MentalHealthTracker.FavouriteResources.Resources;
import com.example.MentalHealthTracker.R;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceToStringTest {
    @Test
    public void checkResourcesToString() {
        int ResourceID = ResourceToString.getResourceNameFromID(Resources.Yoga.toString());
        Assert.assertEquals(R.string.yoga, ResourceID);
    }
}
