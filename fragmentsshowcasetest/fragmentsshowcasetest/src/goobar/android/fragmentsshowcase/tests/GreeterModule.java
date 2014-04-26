package goobar.android.fragmentsshowcase.tests;

import goobar.android.fragmentsshowcase.GreeterFragment;
import goobar.android.fragmentsshowcase.SimpleFragmentGreeter;
import dagger.Module;
import dagger.Provides;

@Module(injects = GreeterFragmentTest.class)
public class GreeterModule
{
	@Provides
	public GreeterFragment provideGreeter()
	{
		return new SimpleFragmentGreeter();
	}

}
