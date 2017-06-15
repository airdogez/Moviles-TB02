package com.upc.desarrollo.helpers;

import com.upc.desarrollo.Config;

/**
 * Created by profesores on 5/27/17.
 */

public class Utils
{

    public static float convertPixelsToMeters(float pixels){
        return pixels/ Config.PPM;

    }
}
