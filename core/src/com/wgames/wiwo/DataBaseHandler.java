package com.wgames.wiwo;

import java.io.IOException;

/**
 * Created by root on 2016-05-03.
 */
public interface DataBaseHandler
{
    public void openDataBase();
    public void createDataBase();
    public boolean checkWord(String s);

}
