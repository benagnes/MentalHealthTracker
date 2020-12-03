package com.example.mentalhealthtracker;

import com.example.mentalhealthtracker.FavouriteResources.ResourceToString;
import com.example.mentalhealthtracker.FavouriteResources.Resources;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceToStringTest {
    @Test
    public void checkResourcesToString() {
        int ResourceID = ResourceToString.getResourceNameFromID(Resources.Yoga.toString());
        assertEquals(R.string.yoga, ResourceID);
    }
}
