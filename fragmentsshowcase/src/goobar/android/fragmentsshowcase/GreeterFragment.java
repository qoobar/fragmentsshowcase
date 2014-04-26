package goobar.android.fragmentsshowcase;

/**
 * Just an simple test interface.
 * 
 * @author goobar
 *
 */
public interface GreeterFragment
{
	/**
	 * Displays given message.
	 * 
	 * @param msg
	 */
	void greet(String msg);

	/**
	 * Returns displayed message. Used only for unit-testing purposes in
	 * this example project.
	 * 
	 * @return
	 */
	String msg();

	/**
	 * Registers {@link OnMessageDisplayedListener} listener.
	 * 
	 * @param listener
	 */
	void registerOnMessageDisplayedListener(
		OnMessageDisplayedListener listener);

	/**
	 * An callback interface called when message is displayed inside
	 * fragment.
	 * 
	 * @author goobar
	 *
	 */
	public static interface OnMessageDisplayedListener
	{
		void onMessageDisplayed(String msg);
	}
}
