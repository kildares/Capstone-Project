package futmatcher.kildare.com.futmatcher;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by kilda on 8/18/2018.
 */

public class MatchUtils {

	public static int screenWidth(Configuration configuration)
	{
		int screenWidth = configuration.screenHeightDp;
		Log.d("Screen WIDTH",Integer.toString(screenWidth));
		return screenWidth;
	}

}
