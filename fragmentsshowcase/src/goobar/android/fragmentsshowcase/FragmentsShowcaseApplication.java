package goobar.android.fragmentsshowcase;

import android.app.Application;
import dagger.ObjectGraph;

/**
 * Global singleton object accessible from every context.
 * 
 * @author goobar
 *
 */
public class FragmentsShowcaseApplication extends Application
{
	/**
	 * Holds all the dependencies.
	 */
	private ObjectGraph objectGraph;

	/**
	 * Returns configured instance of {@link ObjectGraph}.
	 * 
	 * @return the objectGraph
	 */
	public ObjectGraph getObjectGraph()
	{
		return objectGraph;
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		// Initialize dependency graph.
		objectGraph = ObjectGraph.create(new ContainerModule());
	}

}
