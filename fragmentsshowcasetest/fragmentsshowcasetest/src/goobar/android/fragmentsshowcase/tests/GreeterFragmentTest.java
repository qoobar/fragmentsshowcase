package goobar.android.fragmentsshowcase.tests;

import goobar.android.fragmentsshowcase.GreeterFragment;
import goobar.android.fragmentsshowcase.tests.GreeterFragmentTest.GreeterTestActivity;

import javax.inject.Inject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import dagger.ObjectGraph;

/**
 * Simple testing of {@link GreeterFragment}. It's necessary to deploy
 * {@link GreeterFragment} inside the {@link GreeterTestActivity} because of
 * Android framework requirements (every fragment must reside inside an
 * {@link Activity}.
 * 
 * @author goobar
 *
 */
public class GreeterFragmentTest extends
	ActivityUnitTestCase<GreeterTestActivity>
{
	private FragmentManager fragmentManager;
	@Inject
	GreeterFragment greeter;
	private ObjectGraph objectGraph;

	public GreeterFragmentTest()
	{
		super(GreeterTestActivity.class);
	}

	@SmallTest
	public void testGreeterShouldDisplayHelloMsg()
	{
		String msg = "Hello";

		GreeterFragment fragmentGreeter = findFragmentGreeter();
		fragmentGreeter.greet(msg);

		assertEquals(msg, fragmentGreeter.msg());
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		objectGraph = ObjectGraph.create(new GreeterModule());
		objectGraph.inject(this);
		startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		fragmentManager = getActivity().getFragmentManager();
		fragmentManager.beginTransaction()
			.replace(android.R.id.content, (Fragment) greeter)
			.commit();
		fragmentManager.executePendingTransactions();
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		fragmentManager.beginTransaction().remove((Fragment) greeter)
			.commit();
		fragmentManager.executePendingTransactions();
		greeter = null;
	}

	private GreeterFragment findFragmentGreeter()
	{
		GreeterFragment fragmentGreeter = (GreeterFragment) fragmentManager
			.findFragmentById(android.R.id.content);
		return fragmentGreeter;
	}

	/**
	 * Empty hosting activity. It's just a host wrapper for
	 * {@link GreeterFragment}.
	 * 
	 * @author goobar
	 *
	 */
	public static class GreeterTestActivity extends Activity
	{
	}

}
