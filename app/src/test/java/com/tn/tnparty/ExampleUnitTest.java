package com.tn.tnparty;

import com.tn.tnparty.activities.report.ReportActivity;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String actual = AppUtils.getFormattedDateString("9-2-2018", Constants.DATE_READ_FORMAT, Constants.DOB_DATE_FORMAT);
        Assert.assertEquals(actual, "2018-02-09T12:00:00");
    }



}