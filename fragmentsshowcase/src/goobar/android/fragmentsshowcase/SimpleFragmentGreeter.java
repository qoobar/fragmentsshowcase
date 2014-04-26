package goobar.android.fragmentsshowcase;

import java.util.Collection;
import java.util.HashSet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Simple implementation of {@link GreeterFragment}.
 * 
 * @author goobar
 *
 */
public class SimpleFragmentGreeter extends Fragment implements GreeterFragment
{
	/**
	 * This is a key used to retrieve/save counter value from/to bundle.
	 */
	private static final String COUNTER_KEY = "SimpleFragmentGreeter.counter";

	/**
	 * Just some simple useless field to show how to save/restore fragment
	 * instance
	 */
	int counter = 0;
	private TextView textView;

	private String msg;

	private final Collection<OnMessageDisplayedListener> listeners;

	/**
	 * Alternatively we could create this factory method to pass arguments
	 * to fragment.
	 * 
	 * @param msg
	 * @return
	 */
	public static SimpleFragmentGreeter newInstance(String msg)
	{
		SimpleFragmentGreeter fragment = new SimpleFragmentGreeter();
		// We need to pack our arguments inside bundle...
		Bundle args = new Bundle();
		args.putString("msg_key", msg);
		// and pass them using setArguments(Bundle bundle) method. After
		// that we can retrieve it inside onCreate... methods by calling
		// getArguments.
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Fragments need to have the default constructor. Moreover, it's
	 * impossible to pass arguments using other constructors (framework uses
	 * only default constructor). One should create static factory method
	 * which will save arguments inside bundle or pass data after creation
	 * of fragment. In this example we use the second approach.
	 */
	public SimpleFragmentGreeter()
	{
		msg = "Default msg";
		listeners = new HashSet<GreeterFragment.OnMessageDisplayedListener>();
	}

	@Override
	public void greet(String msg)
	{
		// Remember the message which will be used after view is
		// properly initialized
		this.msg = msg;
		// If view is already initialized (for example when user calls
		// this method after fragment is already initialized and ready
		// to use), let's just change our message right now
		if (getView() != null)
		{
			textView.setText(msg);
		}
	}

	@Override
	public String msg()
	{
		return textView.getText().toString();
	}

	/**
	 * We can initialize everything inside
	 * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} or
	 * {@link #onCreate(Bundle)} method. Generally it's better to initialize
	 * non-view data inside {@link #onCreate(Bundle)} method, but we are
	 * lazy ;).
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		// When fragment is about to recreate (when there is some data
		// saved inside the bundle)...
		if (savedInstanceState != null)
		{
			// retrieve the saved counter value
			counter = savedInstanceState.getInt(COUNTER_KEY);
		}
		// It's important to parse data from savedInstanceState first,
		// as arguments still can be accessible, even after recreation
		// of fragment
		else
		{
			// If we decided to pass some arguments using
			// newInstance static
			// factory method, now we can retrieve them by calling
			// getArguments()
			Bundle args = getArguments();
			// getArguments() can return null, when there are no
			// arguments passed (e.g. for the first time)
			if (args != null)
			{
				counter = args.getInt("msg_key");
			}
		}
		counter++;
		// Create the view
		return inflater.inflate(R.layout.fragment_simple_greeter,
			container, false);
	}

	/**
	 * This method is called when fragment is about to end its life. It's a
	 * good place to save state of the fragment.
	 */
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		// Save current value of counter
		outState.putInt(COUNTER_KEY, counter);
	}

	/**
	 * This method is called when view is properly initialized and we can
	 * start using/manipulating it.
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		textView = (TextView) view
			.findViewById(R.id.fragment_simple_greeter_text_view);
		textView.setText(msg + ", counter = " + counter);
		for (OnMessageDisplayedListener listener : listeners)
		{
			listener.onMessageDisplayed(msg);
		}
	}

	@Override
	public void registerOnMessageDisplayedListener(
		OnMessageDisplayedListener listener)
	{
		listeners.add(listener);
	}

}
