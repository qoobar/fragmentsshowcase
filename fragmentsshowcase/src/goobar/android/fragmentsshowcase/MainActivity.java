package goobar.android.fragmentsshowcase;

import goobar.android.fragmentsshowcase.GreeterFragment.OnMessageDisplayedListener;

import javax.inject.Inject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import dagger.ObjectGraph;

/**
 * Activity hosting a {@link GreeterFragment} instance injected by Dagger.
 * 
 * @author goobar
 *
 */
public class MainActivity extends Activity implements
	GreeterFragment.OnMessageDisplayedListener
{
	/**
	 * Injected instance of {@link GreeterFragment}. Unfortunately Dagger
	 * cannot inject private fields, even by using setter methods.
	 */
	@Inject
	GreeterFragment greeter;

	public MainActivity()
	{
		super();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Implementation of callback method defined inside
	 * {@link OnMessageDisplayedListener}.
	 */
	@Override
	public void onMessageDisplayed(String msg)
	{
		Toast.makeText(this, "MESSAGE DISPLAYED: " + msg,
			Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get instance of dependency graph
		FragmentsShowcaseApplication application = (FragmentsShowcaseApplication) getApplication();
		ObjectGraph objectGraph = application.getObjectGraph();
		// inject all dependencies using inject method
		objectGraph.inject(this);
		// or...
		// inject single dependency using get method
		// greeter = objectGraph.get(Greeter.class);

		// Get instance of fragment manager used to run fragment
		// transactions
		FragmentManager fragmentManager = getFragmentManager();

		// Always replace with new fragment instance
		// fragmentManager.beginTransaction()
		// .replace(R.id.container, (Fragment) greeter).commit();

		// or...

		// replace only if there is no fragment yet attached to hosting
		// activity
		GreeterFragment foundGreeter = (GreeterFragment) fragmentManager
			.findFragmentById(R.id.container);

		// There is no fragment attached yet...
		if (foundGreeter == null)
		{
			// so replace container identified by R.id.container id
			// with new instance of our fragment retrieved from
			// Dagger DI container
			fragmentManager.beginTransaction()
				.replace(R.id.container, (Fragment) greeter)
				.commit();
		}
		// There is already a fragment attached...
		else
		{
			// so let's just use our old instance
			greeter = foundGreeter;
		}

		// Now we have an working instance of GreeterFragment, so we can
		// start using it. Even if fragment is already attached, we need
		// to re-register listeners, as it's impossible to save
		// references inside fragment state bundle.
		greeter.registerOnMessageDisplayedListener(this);

		// Start real work with our fragment
		greeter.greet("Message passed through interface");
	}

}
