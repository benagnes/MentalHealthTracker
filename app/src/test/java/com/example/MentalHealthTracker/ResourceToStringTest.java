package com.example.MentalHealthTracker;

import com.example.MentalHealthTracker.FavouriteResources.ResourceToString;
import com.example.MentalHealthTracker.FavouriteResources.Resources;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceToStringTest {
    @Test
    public void checkResourcesToString() {
        int ResourceID = ResourceToString.getResourceNameFromID(Resources.Yoga.toString());
        assertEquals(R.string.yoga, ResourceID);
    }
}
