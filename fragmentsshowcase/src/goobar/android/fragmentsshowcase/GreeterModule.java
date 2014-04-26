package goobar.android.fragmentsshowcase;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module responsible of providing {@link GreeterFragment} instances.
 * 
 * @author goobar
 *
 */
@Module(injects = { MainActivity.class })
public class GreeterModule
{
	/**
	 * Creates instance of {@link GreeterFragment} which can be later
	 * injected by Dagger.
	 * 
	 * @return
	 */
	@Provides
	public GreeterFragment provideGreeter()
	{
		return new SimpleFragmentGreeter();
	}

}
